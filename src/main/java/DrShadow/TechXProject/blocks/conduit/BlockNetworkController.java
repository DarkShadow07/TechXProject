package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.conduit.network.controller.TileNetworkController;
import DrShadow.TechXProject.fx.EntityReddustFXT;
import DrShadow.TechXProject.items.ItemWrench;
import DrShadow.TechXProject.util.ChatUtil;
import DrShadow.TechXProject.util.Util;
import DrShadow.TechXProject.util.VectorUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockNetworkController extends BlockContainerBase
{
	public BlockNetworkController()
	{
		super(Material.iron);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		((INetworkContainer) worldIn.getTileEntity(pos)).searchRelays();
		((INetworkContainer) worldIn.getTileEntity(pos)).searchNetwork();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileNetworkController tile = (TileNetworkController) worldIn.getTileEntity(pos);

		if (playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() instanceof ItemWrench)
		{
			tile.searchNetwork();
			tile.searchRelays();
		}

		if (playerIn.isSneaking() && tile.getNetwork().getElements().size() > 0)
		{
			List<IChatComponent> messages = new ArrayList<>();
			messages.add(new ChatComponentText(String.format(ChatFormatting.GREEN + "Conduit Network at x: %s y: %s z: %s", tile.getController().getPos().getX(), tile.getController().getPos().getY(), tile.getController().getPos().getZ())));
			messages.add(new ChatComponentText(ChatFormatting.BLUE + " Can Import from: "));

			for (INetworkElement input : tile.getNetwork().getInputContainers())
			{
				if (input != null && input.hasInventory())
				{
					String name = input.getAttachedTile().getBlockType().getLocalizedName();
					String elementType = input instanceof IEnergyContainer ? "Energy" : "Item";

					messages.add(new ChatComponentText(
							"  " +
									name +
									" at x:" + ChatFormatting.GREEN + input.getTile().getPos().getX() + ChatFormatting.RESET +
									" y:" + ChatFormatting.GREEN + input.getTile().getPos().getY() + ChatFormatting.RESET +
									" z:" + ChatFormatting.GREEN + input.getTile().getPos().getZ() +
									ChatFormatting.RESET + " (" + elementType + ")"));
				}
			}

			messages.add(new ChatComponentText(ChatFormatting.GOLD + " Can Export to: "));

			for (INetworkElement output : tile.getNetwork().getOutputContainers())
			{
				if (output != null && output.hasInventory())
				{
					String name = output.getAttachedTile().getBlockType().getLocalizedName();
					String elementType = output instanceof IEnergyContainer ? "Energy" : "Item";

					messages.add(new ChatComponentText(
							"  " +
									name +
									" at x:" + ChatFormatting.GREEN + output.getTile().getPos().getX() + ChatFormatting.RESET +
									" y:" + ChatFormatting.GREEN + output.getTile().getPos().getY() + ChatFormatting.RESET +
									" z:" + ChatFormatting.GREEN + output.getTile().getPos().getZ() +
									ChatFormatting.RESET + " (" + elementType + ")"));
				}
			}
			ChatUtil.sendNoSpamClient(messages.toArray(new IChatComponent[messages.size()]));
		}

		for (INetworkElement element : tile.getNetwork().getElements())
		{
			for (Vec3 vec : VectorUtil.dotsOnRay(new Vec3(pos).addVector(0.5, 0.5, 0.5), new Vec3(element.getTile().getPos()).addVector(0.5, 0.5, 0.5), 0.1f))
			{
				Util.spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, 0.01f, 0, 1));
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileNetworkController();
	}
}

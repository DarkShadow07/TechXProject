package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.conduit.network.controller.TileNetworkController;
import DrShadow.TechXProject.util.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockNetworkController extends BlockContainerBase
{
	public BlockNetworkController()
	{
		super(Material.iron, 3.0f, 2, "pickaxe");
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		((INetworkContainer) worldIn.getTileEntity(pos)).searchNetwork();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileNetworkController tile = (TileNetworkController) worldIn.getTileEntity(pos);

		if (playerIn.isSneaking() && tile.getNetwork().getElements().size() > 0)
		{
			List<ITextComponent> messages = new ArrayList<>();
			messages.add(new TextComponentString(String.format(ChatFormatting.GREEN + "Conduit Network at x: %s y: %s z: %s", tile.getController().getPos().getX(), tile.getController().getPos().getY(), tile.getController().getPos().getZ())));
			messages.add(new TextComponentString(ChatFormatting.BLUE + " Can Import from: "));

			for (INetworkElement input : tile.getNetwork().getInputContainers())
			{
				if (input != null && input.hasInventory())
				{
					String name = input.getAttachedTile().getBlockType().getLocalizedName();
					String elementType = input instanceof IEnergyContainer ? "Energy" : "Item";

					messages.add(new TextComponentString(
							"  " +
									name +
									" at x:" + ChatFormatting.GREEN + input.getTile().getPos().getX() + ChatFormatting.RESET +
									" y:" + ChatFormatting.GREEN + input.getTile().getPos().getY() + ChatFormatting.RESET +
									" z:" + ChatFormatting.GREEN + input.getTile().getPos().getZ() +
									ChatFormatting.RESET + " (" + elementType + ")"));
				}
			}

			messages.add(new TextComponentString(ChatFormatting.GOLD + " Can Export to: "));

			for (INetworkElement output : tile.getNetwork().getOutputContainers())
			{
				if (output != null && output.hasInventory())
				{
					String name = output.getAttachedTile().getBlockType().getLocalizedName();
					String elementType = output instanceof IEnergyContainer ? "Energy" : "Item";

					messages.add(new TextComponentString(
							"  " +
									name +
									" at x:" + ChatFormatting.GREEN + output.getTile().getPos().getX() + ChatFormatting.RESET +
									" y:" + ChatFormatting.GREEN + output.getTile().getPos().getY() + ChatFormatting.RESET +
									" z:" + ChatFormatting.GREEN + output.getTile().getPos().getZ() +
									ChatFormatting.RESET + " (" + elementType + ")"));
				}
			}
			ChatUtil.sendNoSpamClient(messages.toArray(new ITextComponent[messages.size()]));
		}

		tile.drawLines();

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileNetworkController();
	}
}

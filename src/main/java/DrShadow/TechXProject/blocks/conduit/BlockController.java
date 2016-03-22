package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.conduit.network.INetworkElement;
import DrShadow.TechXProject.conduit.network.TileController;
import DrShadow.TechXProject.util.ChatUtil;
import DrShadow.TechXProject.util.VectorHelper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockController extends BlockContainerBase
{
	public BlockController()
	{
		super(Material.iron);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileController tile = (TileController) worldIn.getTileEntity(pos);

		if (playerIn.isSneaking() && tile.getNetwork().getElements().size() > 0)
		{
			List<IChatComponent> messages = new ArrayList<>();
			messages.add(new ChatComponentText(String.format(ChatFormatting.GREEN + "Conduit Network at x: %s y: %s z: %s", tile.getController().getPos().getX(), tile.getController().getPos().getY(), tile.getController().getPos().getZ())));
			messages.add(new ChatComponentText(ChatFormatting.BLUE + "- Can Import from: "));

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

			messages.add(new ChatComponentText(ChatFormatting.GOLD + "- Can Export to: "));

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
			for (Vec3 vec : VectorHelper.dotsOnRay(new Vec3(pos).addVector(0.5, 0.5, 0.5), new Vec3(element.getTile().getPos()).addVector(0.5, 0.5, 0.5), 0.1f))
			{
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, vec.xCoord, vec.yCoord, vec.zCoord, 0, 0, 0);
			}
		}

		return true;
	}

	@Override
	public net.minecraft.tileentity.TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileController();
	}
}

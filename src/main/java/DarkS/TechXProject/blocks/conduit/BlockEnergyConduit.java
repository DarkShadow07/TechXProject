package DarkS.TechXProject.blocks.conduit;

import DarkS.TechXProject.api.IWrench;
import DarkS.TechXProject.conduit.energy.TileConduitEnergy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergyConduit extends BlockConduitBase
{
	public BlockEnergyConduit()
	{

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!playerIn.isSneaking())
		{
			if (playerIn.getHeldItem(hand) != null && playerIn.getHeldItem(hand).getItem() instanceof IWrench)
			{
				TileConduitEnergy tile = (TileConduitEnergy) worldIn.getTileEntity(pos);

				if (tile.isInput())
				{
					tile.setInput(false);
					tile.setOutput(true);
				} else if (tile.isOutput())
				{
					tile.setOutput(false);
					tile.setInput(true);
				}
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileConduitEnergy tile = new TileConduitEnergy();
		tile.setInput(true);

		return tile;
	}
}

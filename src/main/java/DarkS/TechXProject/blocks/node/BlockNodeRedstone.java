package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.node.redstone.TileRedstoneNode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNodeRedstone extends BlockNodeBase
{
	public BlockNodeRedstone()
	{

	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		TileRedstoneNode node = (TileRedstoneNode) blockAccess.getTileEntity(pos);

		if (node != null && node.isOutput())
			return node.getPower();

		return 0;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		TileRedstoneNode node = (TileRedstoneNode) blockAccess.getTileEntity(pos);

		if (node != null && node.isOutput())
			return node.getPower();

		return 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return side != EnumFacing.getFront(getMetaFromState(state)) || side != EnumFacing.getFront(getMetaFromState(state)).getOpposite();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileRedstoneNode();
	}
}

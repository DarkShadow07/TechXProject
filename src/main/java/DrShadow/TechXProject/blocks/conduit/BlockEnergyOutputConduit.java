package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.conduit.energy.TileConduitEnergy;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyOutputConduit extends BlockContainerBase
{
	public static final PropertyDirection direction = PropertyDirection.create("rotation");

	public BlockEnergyOutputConduit()
	{
		super(Material.circuits);

		setDefaultState(blockState.getBaseState().withProperty(direction, EnumFacing.UP));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		float pixel = 1f / 16f;

		switch (EnumFacing.getFront(getMetaFromState(worldIn.getBlockState(pos))))
		{
			case UP:
				setBlockBounds(4 * pixel, 10 * pixel, 4 * pixel, 12 * pixel, 1, 12 * pixel);
				break;
			case DOWN:
				setBlockBounds(4 * pixel, 0, 4 * pixel, 12 * pixel, 6 * pixel, 12 * pixel);
				break;
			case NORTH:
				setBlockBounds(4 * pixel, 4 * pixel, 0, 12 * pixel, 12 * pixel, 6 * pixel);
				break;
			case SOUTH:
				setBlockBounds(4 * pixel, 4 * pixel, 10 * pixel, 12 * pixel, 12 * pixel, 1);
				break;
			case EAST:
				setBlockBounds(10 * pixel, 4 * pixel, 4 * pixel, 1, 12 * pixel, 12 * pixel);
				break;
			case WEST:
				setBlockBounds(0, 4 * pixel, 4 * pixel, 6 * pixel, 12 * pixel, 12 * pixel);
				break;
			default:
				setBlockBounds(4 * pixel, 0, 4 * pixel, 12 * pixel, 6 * pixel, 12 * pixel);
				break;
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(direction, facing.getOpposite());
	}

	@Override
	public BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{direction});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(direction, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(direction).ordinal();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileConduitEnergy tile = new TileConduitEnergy();
		tile.setOutput(true);

		return tile;
	}
}

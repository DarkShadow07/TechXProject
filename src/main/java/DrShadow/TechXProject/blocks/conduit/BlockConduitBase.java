package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.blocks.property.IVariantProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockConduitBase extends BlockContainerBase implements IVariantProvider
{
	public static final PropertyDirection direction = PropertyDirection.create("rotation");

	public BlockConduitBase()
	{
		super(Material.circuits);

		setDefaultState(blockState.getBaseState().withProperty(direction, EnumFacing.UP));
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		float pixel = 1f / 16f;

		switch (EnumFacing.getFront(getMetaFromState(worldIn.getBlockState(pos))))
		{
			case UP:
				return new AxisAlignedBB(4 * pixel, 10 * pixel, 4 * pixel, 12 * pixel, 1, 12 * pixel);
			case DOWN:
				return new AxisAlignedBB(4 * pixel, 0, 4 * pixel, 12 * pixel, 6 * pixel, 12 * pixel);
			case NORTH:
				return new AxisAlignedBB(4 * pixel, 4 * pixel, 0, 12 * pixel, 12 * pixel, 6 * pixel);
			case SOUTH:
				return new AxisAlignedBB(4 * pixel, 4 * pixel, 10 * pixel, 12 * pixel, 12 * pixel, 1);
			case EAST:
				return new AxisAlignedBB(10 * pixel, 4 * pixel, 4 * pixel, 1, 12 * pixel, 12 * pixel);
			case WEST:
				return new AxisAlignedBB(0, 4 * pixel, 4 * pixel, 6 * pixel, 12 * pixel, 12 * pixel);
			default:
				return new AxisAlignedBB(4 * pixel, 0, 4 * pixel, 12 * pixel, 6 * pixel, 12 * pixel);
		}
	}

	@Override
	public boolean isFullyOpaque(IBlockState state)
	{
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(direction, facing.getOpposite());
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]{direction});
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
	public List<Pair<Integer, String>> getVariants()
	{
		List<Pair<Integer, String>> ret = new ArrayList<>();

		EnumFacing[] values = EnumFacing.values();

		for (int i = 0; i < values.length; i++)
		{
			ret.add(new ImmutablePair<>(i, "rotation=" + values[i].getName()));
		}

		return ret;
	}
}

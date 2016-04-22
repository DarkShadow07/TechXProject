package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.blocks.base.IRenderer;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockConduitBase extends BlockContainerBase implements IRenderer
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

		switch (EnumFacing.getFront(getMetaFromState(state)))
		{
			case UP:
				return new AxisAlignedBB(4 * pixel, 14 * pixel, 4 * pixel, 12 * pixel, 1, 12 * pixel);
			case DOWN:
				return new AxisAlignedBB(4 * pixel, 0, 4 * pixel, 12 * pixel, 2 * pixel, 12 * pixel);
			case NORTH:
				return new AxisAlignedBB(4 * pixel, 4 * pixel, 0, 12 * pixel, 12 * pixel, 2 * pixel);
			case SOUTH:
				return new AxisAlignedBB(4 * pixel, 4 * pixel, 14 * pixel, 12 * pixel, 12 * pixel, 1);
			case EAST:
				return new AxisAlignedBB(14 * pixel, 4 * pixel, 4 * pixel, 1, 12 * pixel, 12 * pixel);
			case WEST:
				return new AxisAlignedBB(0, 4 * pixel, 4 * pixel, 2 * pixel, 12 * pixel, 12 * pixel);
			default:
				return null;
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(direction, facing.getOpposite());
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, direction);
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
	public void registerModel()
	{
		for (int i = 0; i < EnumFacing.VALUES.length; i++)
		{
			Logger.info("Registered Custom Model Block " + getUnlocalizedName() + " with Variant " + i + "!");

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(Reference.MOD_ID + ":" + getUnlocalizedName().substring(18), "inventory"));
		}
	}
}

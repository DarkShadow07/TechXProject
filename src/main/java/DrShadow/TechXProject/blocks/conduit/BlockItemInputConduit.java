package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.conduit.item.TileConduitItem;
import DrShadow.TechXProject.gui.GuiHandler;
import DrShadow.TechXProject.lib.Guis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockItemInputConduit extends BlockContainerBase
{
	public static final PropertyDirection direction = PropertyDirection.create("rotation");

	public BlockItemInputConduit()
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.CONDUIT, pos);

		return true;
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
		TileConduitItem tile = new TileConduitItem();
		tile.setInput(true);

		return tile;
	}
}

package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.blocks.base.BlockContainerBase;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Logger;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;
import java.util.List;

public class BlockNodeBase extends BlockContainerBase implements IRenderer
{
	public static final PropertyDirection direction = PropertyDirection.create("rotation");

	public BlockNodeBase()
	{
		super(Material.IRON, 1.9f, 2, "pickaxe");


		setDefaultState(blockState.getBaseState().withProperty(direction, EnumFacing.UP));
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
	{
		for (SelectionBox box : ((IHighlightProvider) worldIn.getTileEntity(pos)).getBoxes())
			if (box != null)
				for (AxisAlignedBB aabb : box.getBoxes())
					addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
	}

	@Nullable
	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
	{
		return ((IHighlightProvider) worldIn.getTileEntity(pos)).rayTrace(pos, start, end);
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

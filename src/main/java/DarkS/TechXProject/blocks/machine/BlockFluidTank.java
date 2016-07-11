package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.blocks.base.BlockBase;
import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
import DarkS.TechXProject.machines.fluidTank.TileFluidTank;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class BlockFluidTank extends BlockBase implements ITileEntityProvider
{
	public BlockFluidTank()
	{
		super(Material.GLASS, 8.4f, 0, "pickaxe");

		setSoundType(SoundType.GLASS);
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
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
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return FluidUtil.interactWithTank(heldItem, playerIn, (IFluidHandler) worldIn.getTileEntity(pos), side);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileFluidTank();
	}
}

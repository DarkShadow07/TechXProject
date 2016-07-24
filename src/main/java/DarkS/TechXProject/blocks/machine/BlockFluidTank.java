package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.blocks.base.BlockBase;
import DarkS.TechXProject.machines.fluidTank.TileFluidTank;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

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
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return FluidUtil.interactWithFluidHandler(heldItem, (IFluidHandler) worldIn.getTileEntity(pos), playerIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileFluidTank();
	}
}

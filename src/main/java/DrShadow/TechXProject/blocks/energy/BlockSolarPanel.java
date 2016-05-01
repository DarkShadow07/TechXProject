package DrShadow.TechXProject.blocks.energy;

import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.machines.energy.TileSolarPanel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockContainerBase
{
	public BlockSolarPanel()
	{
		super(Material.iron, 3.5f, 0, null);

	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		float pixel = 1f / 16f;

		return new AxisAlignedBB(0, 0, 0, 1, 2 * pixel, 1);
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
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileSolarPanel();
	}
}

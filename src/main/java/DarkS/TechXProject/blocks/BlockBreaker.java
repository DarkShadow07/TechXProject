package DarkS.TechXProject.blocks;

import DarkS.TechXProject.blocks.base.BlockContainerBase;
import DarkS.TechXProject.blocks.tile.TileBlockBreaker;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockBreaker extends BlockContainerBase
{
	public BlockBreaker()
	{
		super(Material.ROCK, 2.2f, 1, "pickaxe");
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
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileBlockBreaker();
	}
}

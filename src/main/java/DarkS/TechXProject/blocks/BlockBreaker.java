package DarkS.TechXProject.blocks;

import DarkS.TechXProject.blocks.base.BlockContainerBase;
import DarkS.TechXProject.blocks.tile.TileBlockBreaker;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBreaker extends BlockContainerBase
{
	public BlockBreaker()
	{
		super(Material.ROCK, 2.2f, 1, "pickaxe");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileBlockBreaker();
	}
}

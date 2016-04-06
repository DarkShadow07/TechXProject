package DrShadow.TechXProject.blocks;

import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.tileEntities.TileBlockBreaker;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBreaker extends BlockContainerBase
{
	public BlockBreaker()
	{
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileBlockBreaker();
	}
}

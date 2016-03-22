package DrShadow.TechXProject.blocks;

import DrShadow.TechXProject.TechXProject;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockContainerBase extends BlockContainer
{
	public BlockContainerBase(Material material)
	{
		super(material);
		setCreativeTab(TechXProject.techTab);
	}

	public BlockContainerBase(Material material, float hardness, int harvestLevel, String harvestTool)
	{
		super(material);
		setHardness(hardness);
		setHarvestLevel(harvestTool, harvestLevel);
		setCreativeTab(TechXProject.techTab);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return null;
	}
}

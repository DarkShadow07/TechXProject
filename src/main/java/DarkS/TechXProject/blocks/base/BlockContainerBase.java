package DarkS.TechXProject.blocks.base;

import DarkS.TechXProject.TechXProject;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockContainerBase extends BlockBase implements ITileEntityProvider
{
	public BlockContainerBase(Material material, float hardness, int harvestLevel, String harvestTool)
	{
		super(material, hardness, harvestLevel, harvestTool);
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

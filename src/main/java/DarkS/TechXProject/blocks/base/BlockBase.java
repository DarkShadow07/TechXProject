package DarkS.TechXProject.blocks.base;

import DarkS.TechXProject.TechXProject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block
{
	public BlockBase(Material material, float hardness, int harvestLevel, String harvestTool)
	{
		super(material);
		setHardness(hardness);
		setHarvestLevel(harvestTool, harvestLevel);
		setCreativeTab(TechXProject.techTab);
	}
}

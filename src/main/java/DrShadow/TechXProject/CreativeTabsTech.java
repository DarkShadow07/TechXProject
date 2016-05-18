package DrShadow.TechXProject;

import DrShadow.TechXProject.init.InitBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabsTech
{
	public static class CreativeTabTech extends CreativeTabs
	{
		public CreativeTabTech(String label)
		{
			super(label);
		}

		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(InitBlocks.networkController.block);
		}
	}

	public static class CreativeTabOres extends CreativeTabs
	{
		public CreativeTabOres(String label)
		{
			super(label);
		}

		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(InitBlocks.metalOre.block);
		}
	}
}

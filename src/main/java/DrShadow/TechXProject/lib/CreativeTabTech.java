package DrShadow.TechXProject.lib;

import DrShadow.TechXProject.init.InitItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabTech extends CreativeTabs
{
	public CreativeTabTech(String label)
	{
		super(label);
	}

	@Override
	public Item getTabIconItem()
	{
		return InitItems.wrench;
	}
}

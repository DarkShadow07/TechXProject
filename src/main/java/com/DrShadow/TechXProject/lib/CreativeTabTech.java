package com.DrShadow.TechXProject.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
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
		return Items.item_frame;
	}
}

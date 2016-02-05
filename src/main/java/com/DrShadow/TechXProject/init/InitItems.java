package com.DrShadow.TechXProject.init;

import com.DarkLapis.DarkLapisCore.core.CoreRegistry;
import com.DrShadow.TechXProject.items.ItemWrench;
import com.DrShadow.TechXProject.lib.TechItems;
import com.DrShadow.TechXProject.reference.Reference;

public class InitItems
{
	private static CoreRegistry registry;

	public static void init()
	{
		initItems();
	}

	public static void initItems()
	{
		registry = new CoreRegistry(Reference.MOD_ID);

		TechItems.wrench = registry.registerItem(new ItemWrench(), "wrench");
	}

	public static void initRenders()
	{
		registry.registerRender(TechItems.wrench);
	}
}

package com.DrShadow.TechXProject.init;

import com.DrShadow.TechXProject.items.ItemWrench;
import com.DrShadow.TechXProject.lib.TechItems;
import com.DrShadow.TechXProject.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitItems
{
	public static void init()
	{
		initItems();
	}

	public static void initItems()
	{
		TechItems.testItem = registerItem(new ItemWrench(), "wrench");
	}

	public static Item registerItem(Item item, String name)
	{
		item.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ".item." + name);
		GameRegistry.registerItem(item, name);

		return item;
	}

	public static void initRenders()
	{
		registerRender(TechItems.testItem);
	}

	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(23), "inventory"));
	}
}

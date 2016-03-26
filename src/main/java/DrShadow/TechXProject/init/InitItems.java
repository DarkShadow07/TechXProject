package DrShadow.TechXProject.init;

import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterBase;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterMod;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterName;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterOreDict;
import DrShadow.TechXProject.items.ItemWrench;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Util;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitItems
{
	public static Item wrench;
	public static Item filterBase;
	public static Item filterMod;
	public static Item filterName;
	public static Item filterOreDict;

	public static void init()
	{
		initItems();
		initRecipes();
	}

	public static void initItems()
	{
		wrench = registerItem(new ItemWrench(), "wrench");
		filterBase = registerItem(new ItemFilterBase(), "filterBase");
		filterMod = registerItem(new ItemFilterMod(), "filterMod");
		filterName = registerItem(new ItemFilterName(), "filterName");
		filterOreDict = registerItem(new ItemFilterOreDict(), "filterOreDict");
	}

	public static void initRenders()
	{
		registerRender(wrench);
		registerRender(filterBase);
		registerRender(filterMod);
		registerRender(filterName);
		registerRender(filterOreDict);
	}

	public static void initRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(filterBase), new ItemStack(filterBase));
		GameRegistry.addShapelessRecipe(new ItemStack(filterMod), new ItemStack(filterMod));
		GameRegistry.addShapelessRecipe(new ItemStack(filterName), new ItemStack(filterName));
		GameRegistry.addShapelessRecipe(new ItemStack(filterOreDict), new ItemStack(filterOreDict));
	}

	public static Item registerItem(Item item, String name)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.registerItem(item);
		return item;
	}

	public static void registerRender(Item item)
	{
		RenderItem renderItem = Util.minecraft().getRenderItem();
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getRegistryName().substring(Reference.MOD_ID.length() + 1), "inventory"));
	}
}

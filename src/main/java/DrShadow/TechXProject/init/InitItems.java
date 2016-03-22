package DrShadow.TechXProject.init;

import DarkLapis.DarkLapisCore.core.CoreRegistry;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterBase;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterMod;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterName;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterOreDict;
import DrShadow.TechXProject.items.ItemWrench;
import DrShadow.TechXProject.reference.Reference;
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
	private static CoreRegistry registry;

	public static void init()
	{
		initItems();
		initRecipes();
	}

	public static void initItems()
	{
		registry = new CoreRegistry(Reference.MOD_ID);

		wrench = registerItem(new ItemWrench(), "wrench");
		filterBase = registerItem(new ItemFilterBase(), "filterBase");
		filterMod = registerItem(new ItemFilterMod(), "filterMod");
		filterName = registerItem(new ItemFilterName(), "filterName");
		filterOreDict = registerItem(new ItemFilterOreDict(), "filterOreDict");
	}

	public static void initRenders()
	{
		registry.registerRender(wrench);
		registry.registerRender(filterBase);
		registry.registerRender(filterMod);
		registry.registerRender(filterName);
		registry.registerRender(filterOreDict);
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
}

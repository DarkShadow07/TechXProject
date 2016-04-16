package DrShadow.TechXProject.init;

import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterBase;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterMod;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterName;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterOreDict;
import DrShadow.TechXProject.items.*;
import DrShadow.TechXProject.items.energy.ItemMagnet;
import DrShadow.TechXProject.items.itemWire.ItemWire;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitItems
{
	public static Item wrench;
	public static Item filterBase;
	public static Item filterMod;
	public static Item filterName;
	public static Item filterOreDict;
	public static Item quarryCard;
	public static Item teleporterCard;
	public static Item machineRecipe;

	public static Item magnet;

	public static Item dustIron;
	public static Item dustGold;
	public static Item dustCoal;
	public static Item dustDiamond;
	public static Item dustEmerald;
	public static Item dustStone;

	public static Item wire;

	public static void init()
	{
		initItems();
		initRecipes();

		GameRegistry.registerFuelHandler(new FuelHandler());
	}

	public static void initItems()
	{
		wrench = registerItem(new ItemWrench(), "wrench");
		quarryCard = registerItem(new ItemQuarryCard(), "quarryCard");
		teleporterCard = registerItem(new ItemTeleporterCard(), "teleporterCard");
		machineRecipe = registerItem(new ItemMachineRecipe(), "machineRecipe");

		magnet = registerItem(new ItemMagnet(), "magnet");

		filterBase = registerItem(new ItemFilterBase(), "filterBase");
		filterMod = registerItem(new ItemFilterMod(), "filterMod");
		filterName = registerItem(new ItemFilterName(), "filterName");
		filterOreDict = registerItem(new ItemFilterOreDict(), "filterOreDict");

		dustIron = registerItem(new ItemDustBase(), "dustIron");
		dustGold = registerItem(new ItemDustBase(), "dustGold");
		dustCoal = registerItem(new ItemDustBase(), "dustCoal");
		dustDiamond = registerItem(new ItemDustBase(), "dustDiamond");
		dustEmerald = registerItem(new ItemDustBase(), "dustEmerald");
		dustStone = registerItem(new ItemDustBase(), "dustStone");

		wire = registerItem(new ItemWire(), "wire");
	}

	public static void initRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(filterBase), new ItemStack(filterBase));
		GameRegistry.addShapelessRecipe(new ItemStack(filterMod), new ItemStack(filterMod));
		GameRegistry.addShapelessRecipe(new ItemStack(filterName), new ItemStack(filterName));
		GameRegistry.addShapelessRecipe(new ItemStack(filterOreDict), new ItemStack(filterOreDict));

		GameRegistry.addShapedRecipe(new ItemStack(Blocks.stone), "dd", "dd", 'd', new ItemStack(dustStone));

		GameRegistry.addSmelting(dustIron, new ItemStack(Items.iron_ingot), 0.6f);
		GameRegistry.addSmelting(dustGold, new ItemStack(Items.gold_ingot), 0.6f);
		GameRegistry.addSmelting(dustCoal, new ItemStack(Items.coal), 0.6f);
		GameRegistry.addSmelting(dustDiamond, new ItemStack(Items.diamond), 0.6f);
		GameRegistry.addSmelting(dustEmerald, new ItemStack(Items.emerald), 0.6f);
	}

	public static Item registerItem(Item item, String name)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.registerItem(item);

		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(18), "inventory"));

		Logger.info("Registered Item " + item.getUnlocalizedName() + "!");

		return item;
	}
}

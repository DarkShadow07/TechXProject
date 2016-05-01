package DrShadow.TechXProject.init;

import DrShadow.TechXProject.blocks.base.IRecipeProvider;
import DrShadow.TechXProject.blocks.base.IRenderer;
import DrShadow.TechXProject.blocks.metal.EnumMetals;
import DrShadow.TechXProject.blocks.metal.ItemDustBase;
import DrShadow.TechXProject.blocks.metal.ItemIngotBase;
import DrShadow.TechXProject.blocks.metal.ItemNuggetBase;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum InitItems
{
	wrench(new ItemWrench(), "wrench"),

	quarryCard(new ItemQuarryCard(), "quarryCard"),
	teleporterCard(new ItemTeleporterCard(), "teleporterCard"),
	machineRecipe(new ItemMachineRecipe(), "machineRecipe"),

	magnet(new ItemMagnet(), "magnet"),

	guide(new ItemGuide(), "guide"),

	filterBase(new ItemFilterBase(), "filterBase"),
	filterMod(new ItemFilterMod(), "filterMod"),
	filterName(new ItemFilterName(), "filterName"),
	filterOreDict(new ItemFilterOreDict(), "filterOreDict"),

	ingot(new ItemIngotBase(), "metalIngot"),
	nugget(new ItemNuggetBase(), "metalNugget"),
	dust(new ItemDustBase(), "metalDust"),

	wire(new ItemWire(), "wire"),;

	public Item item;
	public String name;

	InitItems(Item item, String name)
	{
		this.item = item;
		this.name = name;
	}

	public static void init()
	{
		initItems();

		GameRegistry.registerFuelHandler(new FuelHandler());
	}

	public static void initItems()
	{
		for (InitItems item : InitItems.values())
		{
			item.item = registerItem(item.item, item.name);
		}
	}

	public static void initRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(filterBase.item), new ItemStack(filterBase.item));
		GameRegistry.addShapelessRecipe(new ItemStack(filterMod.item), new ItemStack(filterMod.item));
		GameRegistry.addShapelessRecipe(new ItemStack(filterName.item), new ItemStack(filterName.item));
		GameRegistry.addShapelessRecipe(new ItemStack(filterOreDict.item), new ItemStack(filterOreDict.item));

		GameRegistry.addShapedRecipe(new ItemStack(Blocks.stone), "dd", "dd", 'd', new ItemStack(dust.item, 1, EnumMetals.STONE.ordinal()));

		for (InitItems item : InitItems.values())
		{
			if (item.item instanceof IRecipeProvider)
			{
				((IRecipeProvider) item.item).registerRecipes();
			}
		}

		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.IRON.ordinal()), new ItemStack(Items.iron_ingot), 0.6f);
		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.GOLD.ordinal()), new ItemStack(Items.gold_ingot), 0.6f);
		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.DIAMOND.ordinal()), new ItemStack(Items.diamond), 0.6f);
		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.EMERALD.ordinal()), new ItemStack(Items.emerald), 0.6f);
	}

	public static Item registerItem(Item item, String name)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.register(item);

		if (item instanceof IRenderer)
		{
			((IRenderer) item).registerModel();
		} else
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(18), "inventory"));
		}

		Logger.info("Registered Item " + item.getUnlocalizedName() + "!");

		return item;
	}
}

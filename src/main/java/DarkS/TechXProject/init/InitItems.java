package DarkS.TechXProject.init;

import DarkS.TechXProject.blocks.base.IRecipeProvider;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.blocks.metal.EnumMetals;
import DarkS.TechXProject.blocks.metal.ItemDustBase;
import DarkS.TechXProject.blocks.metal.ItemIngotBase;
import DarkS.TechXProject.blocks.metal.ItemNuggetBase;
import DarkS.TechXProject.compat.jei.crusher.CrusherRecipeHandler;
import DarkS.TechXProject.compat.jei.smelter.SmelterRecipeHandler;
import DarkS.TechXProject.items.*;
import DarkS.TechXProject.items.energy.ItemEnergyCore;
import DarkS.TechXProject.items.energy.ItemMagnet;
import DarkS.TechXProject.items.itemWire.ItemWire;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterBase;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterMod;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterName;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterOreDict;
import DarkS.TechXProject.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.commons.lang3.tuple.ImmutablePair;

public enum InitItems
{
	wrench(new ItemWrench(), "wrench"),

	energyCore(new ItemEnergyCore(), "energyCore"),

	processingMatrix(new ItemProcessingMatrix(), "processingMatrix"),

	nodeBase(new ItemNodeBase(), "nodeBase"),

	areaCard(new ItemAreaCard(), "areaCard"),
	teleporterCard(new ItemTeleporterCard(), "teleporterCard"),
	machineRecipe(new ItemMachineRecipe(), "machineRecipe"),

	magnet(new ItemMagnet(), "magnet"),

	guide(new ItemGuide(), "guide"),

	filterBase(new ItemFilterBase(), "filterBase"),
	filterMod(new ItemFilterMod(), "filterMod"),
	filterName(new ItemFilterName(), "filterName"),
	filterOreDict(new ItemFilterOreDict(), "filterOreDict"),

	plasticBall(new ItemPlasticBall(), "plasticBall"),

	ingot(new ItemIngotBase(), "metalIngot"),
	meteorShard(new ItemMeteorShard(), "meteorShard"),
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
			item.item = registerItem(item.item, item.name);
	}

	public static void initRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nodeBase.item, 4), "sis", "mmm", 's', Blocks.STONE_SLAB, 'i', Items.IRON_INGOT, 'm', InitBlocks.meteorStone.block));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.nodeItem.block, 2), "ece", "ibi", 'e', Items.ENDER_PEARL, 'c', Blocks.CHEST, 'i', "ingotIron", 'b', nodeBase.item));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.nodeEnergy.block, 2), "wcw", "ibi", 'w', wire.item, 'c', energyCore.item, 'i', "ingotIron", 'b', nodeBase.item));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.nodeTransport.block, 2), "eme", "ibi", 'e', Items.ENDER_EYE, 'm', Items.MINECART, 'i', "ingotIron", 'b', nodeBase.item));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.nodeRedstone.block, 2), "ddd", "cbr", "ini", 'd', "dye", 'c', Items.COMPARATOR, 'b', Blocks.REDSTONE_BLOCK, 'r', Items.REPEATER, 'i', "ingotIron", 'n', nodeBase.item));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.wire.item, 8), "wgw", "ses", "wgw", 'w', "plankWood", 's', "stickWood", 'g', "blockGlassColorless", 'e', Items.ENDER_PEARL));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.processingMatrix.item), "szs", "ici", "sgs", 's', meteorShard.item, 'z', new ItemStack(Items.SKULL, 1, 2), 'i', "ingotIron", 'c', energyCore.item, 'g', new ItemStack(ingot.item, 1, EnumMetals.GRAPHENE.ordinal())));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.networkController.block), "mcm", "gpg", "sds", 'm', InitBlocks.meteorStone.block, 'c', energyCore.item, 'g', new ItemStack(ingot.item, 1, EnumMetals.GRAPHENE.ordinal()), 'p', processingMatrix.item, 's', meteorShard.item, 'd', new ItemStack(dust.item, 1, EnumMetals.TIN.ordinal())));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.networkRelay.block, 2), "sps", "mnm", 's', meteorShard.item, 'p', processingMatrix.item, 'm', InitBlocks.meteorStone.block, 'n', new ItemStack(nugget.item, 1, EnumMetals.TIN.ordinal())));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.recipeStamper.block), "lpl", "fri", "lbl", 'l', new ItemStack(ingot.item, 1, EnumMetals.TIN.ordinal()), 'p', plasticBall.item, 'f', "feather", 'r', "paper", 'i', new ItemStack(Items.DYE, 1, 0), 'b', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.machineAssembler.block), "pap", "ncn", "ibi", 'p', Blocks.PISTON, 'a', "stoneAndesitePolished", 'n', new ItemStack(nugget.item, 1, EnumMetals.TIN.ordinal()), 'c', "chest", 'i', "ingotIron", 'b', "blockIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(machineRecipe.item, 2), "paper", plasticBall.item));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitBlocks.recipeChest.block), "chest", machineRecipe.item));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.solarPanel.block, 2), "ngn", "isi", 'n', new ItemStack(nugget.item, 1, EnumMetals.TIN.ordinal()), 'g', "blockGlass", 'i', new ItemStack(ingot.item, 1, EnumMetals.TIN.ordinal()), 's', Blocks.DAYLIGHT_DETECTOR));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.fluidTank.block), "nin", "igi", "nin", 'n', new ItemStack(nugget.item, 1, EnumMetals.LEAD.ordinal()), 'i', new ItemStack(ingot.item, EnumMetals.LEAD.ordinal()), 'g', "paneGlassColorless"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.activator.block), "rdr", "sss", 'r', Items.REPEATER, 'd', "dustRedstone", 's', "stone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wrench.item), "i i", " g ", " i ", 'i', "ingotIron", 'g', new ItemStack(ingot.item, 1, EnumMetals.GRAPHENE.ordinal())));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magnet.item), "t t", "iei", "rir", 't', new ItemStack(ingot.item, 1, EnumMetals.TIN.ordinal()), 'i', "ingotIron", 'e', energyCore.item, 'r', "dyeRed"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(guide.item), new ItemStack(Items.BOOK), new ItemStack(wrench.item)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(energyCore.item), "gbg", "nsn", "gbg", 'g', new ItemStack(ingot.item, 1, EnumMetals.GRAPHENE.ordinal()), 'b', "paneGlassColorless", 'n', new ItemStack(nugget.item, 1, EnumMetals.LEAD.ordinal()), 's', meteorShard.item));
		GameRegistry.addShapelessRecipe(new ItemStack(filterBase.item), new ItemStack(filterBase.item));
		GameRegistry.addShapelessRecipe(new ItemStack(filterMod.item), new ItemStack(filterMod.item));
		GameRegistry.addShapelessRecipe(new ItemStack(filterName.item), new ItemStack(filterName.item));
		GameRegistry.addShapelessRecipe(new ItemStack(filterOreDict.item), new ItemStack(filterOreDict.item));
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.STONE), "dd", "dd", 'd', new ItemStack(dust.item, 1, EnumMetals.STONE.ordinal()));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(filterBase.item, 2), "npn", "php", "npn", 'n', "nuggetIron", 'p', "paper", 'h', Blocks.HOPPER));

		for (InitItems item : InitItems.values())
			if (item.item instanceof IRecipeProvider)
				((IRecipeProvider) item.item).registerRecipes();

		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.IRON.ordinal()), new ItemStack(Items.IRON_INGOT), 0);
		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.GOLD.ordinal()), new ItemStack(Items.GOLD_INGOT), 0);
		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.DIAMOND.ordinal()), new ItemStack(Items.DIAMOND), 0);
		GameRegistry.addSmelting(new ItemStack(dust.item, 1, EnumMetals.EMERALD.ordinal()), new ItemStack(Items.EMERALD), 0);

		GameRegistry.addSmelting(new ItemStack(Items.SLIME_BALL), new ItemStack(plasticBall.item, 2), 0.4f);

		SmelterRecipeHandler.instance.addRecipe(new ItemStack(ingot.item, 1, EnumMetals.GRAPHENE.ordinal()), 0.3f, 6000, new ItemStack(Items.IRON_INGOT), new ItemStack(meteorShard.item, 2), new ItemStack(Blocks.OBSIDIAN));
		SmelterRecipeHandler.instance.addRecipe(new ItemStack(meteorShard.item, 2), 1.4f, 700, new ItemStack(InitBlocks.metalOre.block, 1, EnumMetals.METEOR.ordinal()));

		CrusherRecipeHandler.instance.addRecipe(new ItemStack(Blocks.SAND), 80, new ImmutablePair<>(new ItemStack(plasticBall.item), 0.15f));
	}

	public static Item registerItem(Item item, String name)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.register(item);

		if (item instanceof IRenderer)
			((IRenderer) item).registerModel();
		else
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(18), "inventory"));

		return item;
	}
}

package DrShadow.TechXProject.init;

import DrShadow.TechXProject.blocks.BlockBreaker;
import DrShadow.TechXProject.blocks.base.IRecipeProvider;
import DrShadow.TechXProject.blocks.base.IRenderer;
import DrShadow.TechXProject.blocks.conduit.*;
import DrShadow.TechXProject.blocks.energy.BlockSolarPanel;
import DrShadow.TechXProject.blocks.machine.*;
import DrShadow.TechXProject.blocks.metal.BlockMetalBase;
import DrShadow.TechXProject.blocks.metal.BlockOreBase;
import DrShadow.TechXProject.blocks.metal.ItemBlockMetalBase;
import DrShadow.TechXProject.blocks.metal.ItemBlockOreBase;
import DrShadow.TechXProject.blocks.tile.TileBlockBreaker;
import DrShadow.TechXProject.conduit.energy.TileConduitEnergy;
import DrShadow.TechXProject.conduit.item.TileConduitItem;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.conduit.network.controller.TileNetworkController;
import DrShadow.TechXProject.conduit.network.relay.TileNetworkRelay;
import DrShadow.TechXProject.machines.capacitor.TileBasicCapacitor;
import DrShadow.TechXProject.machines.crusher.TileCrusher;
import DrShadow.TechXProject.machines.energy.TileSolarPanel;
import DrShadow.TechXProject.machines.energyMonitor.TileEnergyMonitor;
import DrShadow.TechXProject.machines.farmer.TileFarmer;
import DrShadow.TechXProject.machines.machineAssembler.TileMachineAssembler;
import DrShadow.TechXProject.machines.quarry.TileQuarry;
import DrShadow.TechXProject.machines.recipeChest.TileRecipeChest;
import DrShadow.TechXProject.machines.recipeStamper.TileRecipeStamper;
import DrShadow.TechXProject.machines.smelter.TileSmelter;
import DrShadow.TechXProject.machines.storageUnit.TileStorageUnit;
import DrShadow.TechXProject.machines.teleporter.TileTeleporter;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum InitBlocks
{
	conduitItem(new BlockItemConduit(), "itemConduit"),
	conduitEnergy(new BlockEnergyConduit(), "energyConduit"),
	networkRelay(new BlockNetworkRelay(), "relay"),
	networkController(new BlockNetworkController(), "controller"),

	conduitLogic(new BlockLogicConduit(), "conduitLogic"),

	energyMonitor(new BlockEnergyMonitor(), "energyMonitor"),

	machineAssembler(new BlockMachineAssembler(), "machineAssembler"),
	recipeChest(new BlockRecipeChest(), "recipeChest"),
	recipeStamper(new BlockRecipeStamper(), "recipeStamper"),

	smelter(new BlockSmelter(), "smelter"),
	crusher(new BlockCrusher(), "crusher"),
	quarry(new BlockQuarry(), "quarry"),
	farmer(new BlockFarmer(), "farmer"),

	basicCapacitor(new BlockCapacitorBasic(), "basicCapacitor"),

	solarPanel(new BlockSolarPanel(), "solarPanel"),

	teleporter(new BlockTeleporter(), "teleporter"),
	storageUnit(new BlockStorageUnit(), "storageUnit"),
	blockBreaker(new BlockBreaker(), "breaker"),

	metalOre(new ItemBlockOreBase(new BlockOreBase()), "metalOre"),
	metalBlock(new ItemBlockMetalBase(new BlockMetalBase()), "metalBlock"),;

	public Block block;
	public ItemBlock itemBlock;
	public String name;

	InitBlocks(Block block, String name)
	{
		this.block = block;
		this.itemBlock = new ItemBlock(block);
		this.name = name;
	}

	InitBlocks(ItemBlock itemBlock, String name)
	{
		this.block = itemBlock.getBlock();
		this.itemBlock = itemBlock;
		this.name = name;
	}

	public static void init()
	{
		initBlocks();
		registerTileEntities();
	}

	private static void initBlocks()
	{
		for (InitBlocks block : InitBlocks.values())
		{
			block.block = registerBlock(block);
		}
	}

	private static void registerTileEntities()
	{
		registerTile(TileNetworkController.class);
		registerTile(TileConduitItem.class);
		registerTile(TileConduitEnergy.class);
		registerTile(TileLogicConduit.class);
		registerTile(TileEnergyMonitor.class);
		registerTile(TileNetworkRelay.class);
		registerTile(TileSmelter.class);
		registerTile(TileCrusher.class);
		registerTile(TileBasicCapacitor.class);
		registerTile(TileSolarPanel.class);
		registerTile(TileQuarry.class);
		registerTile(TileBlockBreaker.class);
		registerTile(TileStorageUnit.class);
		registerTile(TileTeleporter.class);
		registerTile(TileRecipeStamper.class);
		registerTile(TileMachineAssembler.class);
		registerTile(TileRecipeChest.class);
		registerTile(TileFarmer.class);
	}

	public static void initRecipes()
	{
		for (InitBlocks block : InitBlocks.values())
		{
			if (block.block instanceof IRecipeProvider)
			{
				((IRecipeProvider) block.block).registerRecipes();
			}
		}
	}

	private static void registerTile(Class<? extends TileEntity> tileClass)
	{
		GameRegistry.registerTileEntity(tileClass, Reference.MOD_ID + tileClass.getSimpleName());
	}

	public static Block registerBlock(InitBlocks register)
	{
		Block block = register.block;
		block.setRegistryName(register.name);
		block.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + register.name);
		GameRegistry.register(block);
		GameRegistry.register(register.itemBlock.setRegistryName(register.name));

		if (block instanceof IRenderer)
		{
			((IRenderer) block).registerModel();
		} else
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(18), "inventory"));
		}

		Logger.info("Registered Block " + block.getUnlocalizedName() + "!");

		return block;
	}
}

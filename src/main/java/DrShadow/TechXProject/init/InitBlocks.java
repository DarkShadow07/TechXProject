package DrShadow.TechXProject.init;

import DrShadow.TechXProject.blocks.BlockBreaker;
import DrShadow.TechXProject.blocks.conduit.*;
import DrShadow.TechXProject.blocks.energy.BlockSolarPanel;
import DrShadow.TechXProject.blocks.machine.*;
import DrShadow.TechXProject.conduit.energy.TileConduitEnergy;
import DrShadow.TechXProject.conduit.item.TileConduitItem;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.conduit.network.controller.TileNetworkController;
import DrShadow.TechXProject.conduit.network.relay.TileNetworkRelay;
import DrShadow.TechXProject.machines.capacitor.TileBasicCapacitor;
import DrShadow.TechXProject.machines.crusher.TileCrusher;
import DrShadow.TechXProject.machines.energy.TileSolarPanel;
import DrShadow.TechXProject.machines.energyMonitor.TileEnergyMonitor;
import DrShadow.TechXProject.machines.machineAssembler.TileMachineAssembler;
import DrShadow.TechXProject.machines.quarry.TileQuarry;
import DrShadow.TechXProject.machines.recipeStamper.TileRecipeStamper;
import DrShadow.TechXProject.machines.smelter.TileSmelter;
import DrShadow.TechXProject.machines.storageUnit.TileStorageUnit;
import DrShadow.TechXProject.machines.teleporter.TileTeleporter;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.tileEntities.TileBlockBreaker;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitBlocks
{
	public static Block conduitItem;
	public static Block conduitEnergy;
	public static Block networkController;
	public static Block blockBreaker;
	public static Block conduitLogic;
	public static Block smelter;
	public static Block crusher;
	public static Block basicCapacitor;
	public static Block networkRelay;
	public static Block solarPanel;
	public static Block quarry;
	public static Block storageUnit;
	public static Block teleporter;
	public static Block energyMonitor;
	public static Block recipeStamper;
	public static Block machineAssembler;

	public static void init()
	{
		initBlocks();
		registerTileEntities();
		initRecipes();
	}

	private static void initBlocks()
	{
		conduitItem = registerBlock(new BlockItemConduit(), "itemConduit");
		conduitEnergy = registerBlock(new BlockEnergyConduit(), "energyConduit");
		networkRelay = registerBlock(new BlockNetworkRelay(), "relay");
		networkController = registerBlock(new BlockNetworkController(), "controller");
		energyMonitor = registerBlock(new BlockEnergyMonitor(), "energyMonitor");
		conduitLogic = registerBlock(new BlockLogicConduit(), "conduitLogic");
		recipeStamper = registerBlock(new BlockRecipeStamper(), "recipeStamper");
		machineAssembler = registerBlock(new BlockMachineAssembler(), "machineAssembler");
		smelter = registerBlock(new BlockSmelter(), "smelter");
		crusher = registerBlock(new BlockCrusher(), "crusher");
		quarry = registerBlock(new BlockQuarry(), "quarry");
		basicCapacitor = registerBlock(new BlockBatteryBasic(), "basicCapacitor");
		teleporter = registerBlock(new BlockTeleporter(), "teleporter");
		storageUnit = registerBlock(new BlockStorageUnit(), "storageUnit");
		solarPanel = registerBlock(new BlockSolarPanel(), "solarPanel");
		blockBreaker = registerBlock(new BlockBreaker(), "breaker");
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
	}

	public static void initRecipes()
	{

	}

	private static void registerTile(Class<? extends TileEntity> tileClass)
	{
		GameRegistry.registerTileEntity(tileClass, Reference.MOD_ID + tileClass.getSimpleName());
	}

	public static Block registerBlock(Block block, String name)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.registerBlock(block);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(18), "inventory"));

		Logger.info("Registered Block " + block.getLocalizedName() + "!");

		return block;
	}

	public Block registerBlock(Block block, String name, Class<? extends ItemBlock> itemBlock)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.registerBlock(block, itemBlock);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(18), "inventory"));

		Logger.info("Registered Block " + block.getUnlocalizedName() + "!");

		return block;
	}
}

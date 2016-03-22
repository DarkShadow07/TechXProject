package DrShadow.TechXProject.init;

import DrShadow.TechXProject.blocks.BlockBreaker;
import DrShadow.TechXProject.blocks.BlockTeslaTower;
import DrShadow.TechXProject.blocks.conduit.*;
import DrShadow.TechXProject.blocks.machine.BlockBatteryBasic;
import DrShadow.TechXProject.blocks.machine.BlockSmelter;
import DrShadow.TechXProject.conduit.energy.TileConduitEnergy;
import DrShadow.TechXProject.conduit.item.TileConduitItem;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.conduit.network.TileController;
import DrShadow.TechXProject.machines.battery.TileBasicBattery;
import DrShadow.TechXProject.machines.handler.smelter.SmelterRecipeHandler;
import DrShadow.TechXProject.machines.smelter.TileSmelter;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.tileEntities.TileBlockBreaker;
import DrShadow.TechXProject.tileEntities.TileTeslaTower;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitBlocks
{
	public static Block conduitItemOut;
	public static Block conduitItemIn;
	public static Block teslaTower;
	public static Block controller;
	public static Block blockBreaker;
	public static Block conduitLogic;
	public static Block smelter;
	public static Block basicBattery;
	public static Block conduitEnergyIn;
	public static Block conduitEnergyOut;

	public static void init()
	{
		initBlocks();
		registerTileEntities();
		initRecipes();
	}

	private static void initBlocks()
	{
		conduitItemOut = registerBlock(new BlockItemOutputConduit(), "blockConduitOutput");
		conduitItemIn = registerBlock(new BlockItemInputConduit(), "blockConduitInput");
		conduitLogic = registerBlock(new BlockLogicConduit(), "blockConduitLogic");
		teslaTower = registerBlock(new BlockTeslaTower(), "blockTeslaTower");
		controller = registerBlock(new BlockController(), "blockController");
		blockBreaker = registerBlock(new BlockBreaker(), "blockBreaker");
		smelter = registerBlock(new BlockSmelter(), "blockSmelter");
		basicBattery = registerBlock(new BlockBatteryBasic(), "basicBattery");
		conduitEnergyIn = registerBlock(new BlockEnergyInputConduit(), "blockEnergyConduitInput");
		conduitEnergyOut = registerBlock(new BlockEnergyOutputConduit(), "blockEnergyConduitOutput");
	}

	private static void registerTileEntities()
	{
		registerTile(TileBlockBreaker.class);
		registerTile(TileTeslaTower.class);
		registerTile(TileController.class);
		registerTile(TileConduitItem.class);
		registerTile(TileLogicConduit.class);
		registerTile(TileSmelter.class);
		registerTile(TileBasicBattery.class);
		registerTile(TileConduitEnergy.class);
	}

	public static void initRenders()
	{
		registerRender(conduitItemOut);
		registerRender(conduitItemIn);
		registerRender(conduitLogic);
		registerRender(teslaTower);
		registerRender(controller);
		registerRender(blockBreaker);
		registerRender(smelter);
		registerRender(basicBattery);
		registerRender(conduitEnergyIn);
		registerRender(conduitEnergyOut);
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
		return block;
	}

	public static void registerRender(Block block)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getRegistryName().substring(Reference.MOD_ID.length() + 1), "inventory"));
	}

	public Block registerBlock(Block block, String name, Class<? extends ItemBlock> itemBlock)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.registerBlock(block, itemBlock);
		return block;
	}
}

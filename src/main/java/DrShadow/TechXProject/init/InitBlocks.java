package DrShadow.TechXProject.init;

import DrShadow.TechXProject.blocks.BlockBreaker;
import DrShadow.TechXProject.blocks.conduit.*;
import DrShadow.TechXProject.blocks.energy.BlockSolarPanel;
import DrShadow.TechXProject.blocks.machine.*;
import DrShadow.TechXProject.blocks.property.IVariantProvider;
import DrShadow.TechXProject.conduit.energy.TileConduitEnergy;
import DrShadow.TechXProject.conduit.item.TileConduitItem;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.conduit.network.controller.TileNetworkController;
import DrShadow.TechXProject.conduit.network.relay.TileNetworkRelay;
import DrShadow.TechXProject.machines.capacitor.TileBasicCapacitor;
import DrShadow.TechXProject.machines.crusher.TileCrusher;
import DrShadow.TechXProject.machines.energy.TileSolarPanel;
import DrShadow.TechXProject.machines.quarry.TileQuarry;
import DrShadow.TechXProject.machines.smelter.TileSmelter;
import DrShadow.TechXProject.machines.storageUnit.TileStorageUnit;
import DrShadow.TechXProject.machines.teleporter.TileTeleporter;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.tileEntities.TileBlockBreaker;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

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

	public static void init()
	{
		initBlocks();
		registerTileEntities();
		initRecipes();
	}

	private static void initBlocks()
	{
		conduitItem = registerBlock(new BlockItemConduit(), "blockItemConduit");
		conduitEnergy = registerBlock(new BlockEnergyConduit(), "blockEnergyConduit");
		networkRelay = registerBlock(new BlockNetworkRelay(), "blockRelay");
		networkController = registerBlock(new BlockNetworkController(), "blockController");
		conduitLogic = registerBlock(new BlockLogicConduit(), "blockConduitLogic");
		smelter = registerBlock(new BlockSmelter(), "blockSmelter");
		crusher = registerBlock(new BlockCrusher(), "blockCrusher");
		quarry = registerBlock(new BlockQuarry(), "blockQuarry");
		basicCapacitor = registerBlock(new BlockBatteryBasic(), "basicCapacitor");
		teleporter = registerBlock(new BlockTeleporter(), "blockTeleporter");
		storageUnit = registerBlock(new BlockStorageUnit(), "blockStorageUnit");
		solarPanel = registerBlock(new BlockSolarPanel(), "blockSolarPanel");
		blockBreaker = registerBlock(new BlockBreaker(), "blockBreaker");
	}

	private static void registerTileEntities()
	{
		registerTile(TileNetworkController.class);
		registerTile(TileConduitItem.class);
		registerTile(TileConduitEnergy.class);
		registerTile(TileLogicConduit.class);
		registerTile(TileNetworkRelay.class);
		registerTile(TileSmelter.class);
		registerTile(TileCrusher.class);
		registerTile(TileBasicCapacitor.class);
		registerTile(TileSolarPanel.class);
		registerTile(TileQuarry.class);
		registerTile(TileBlockBreaker.class);
		registerTile(TileStorageUnit.class);
		registerTile(TileTeleporter.class);
	}

	public static void initRenders()
	{
		registerRender(conduitLogic);
		registerRender(networkController);
		registerRender(blockBreaker);
		registerRender(basicCapacitor);
		registerRender(networkRelay);
		registerRender(solarPanel);
		registerRender(quarry);
		registerRender(storageUnit);
		registerRender(teleporter);

		registerRenderVariables(conduitEnergy);
		registerRenderVariables(conduitItem);

		registerRenderVariables(smelter);
		registerRenderVariables(crusher);
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
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, block.getUnlocalizedName().substring(Reference.MOD_ID.length() + 1)), "inventory"));
	}

	public static void registerRenderVariables(Block block)
	{
		if (block instanceof IVariantProvider)
		{
			IVariantProvider variantProvider = (IVariantProvider) block;
			for (Pair<Integer, String> variant : variantProvider.getVariants())
			{
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), variant.getKey(), new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, block.getUnlocalizedName().substring(Reference.MOD_ID.length() + 1)), variant.getValue()));
			}
		}
	}

	public Block registerBlock(Block block, String name, Class<? extends ItemBlock> itemBlock)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + "." + name);
		GameRegistry.registerBlock(block, itemBlock);
		return block;
	}
}

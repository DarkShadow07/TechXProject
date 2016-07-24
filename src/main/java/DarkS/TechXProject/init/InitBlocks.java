package DarkS.TechXProject.init;

import DarkS.TechXProject.blocks.BlockBreaker;
import DarkS.TechXProject.blocks.BlockMeteorStone;
import DarkS.TechXProject.blocks.base.IRecipeProvider;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.blocks.machine.*;
import DarkS.TechXProject.blocks.machine.generator.BlockSolarPanel;
import DarkS.TechXProject.blocks.metal.BlockMetalBase;
import DarkS.TechXProject.blocks.metal.BlockOreBase;
import DarkS.TechXProject.blocks.metal.ItemBlockMetalBase;
import DarkS.TechXProject.blocks.metal.ItemBlockOreBase;
import DarkS.TechXProject.blocks.node.*;
import DarkS.TechXProject.blocks.tile.TileBlockBreaker;
import DarkS.TechXProject.machines.activator.TileActivator;
import DarkS.TechXProject.machines.capacitor.TileBasicCapacitor;
import DarkS.TechXProject.machines.crusher.TileCrusher;
import DarkS.TechXProject.machines.energyMonitor.TileEnergyMonitor;
import DarkS.TechXProject.machines.farmer.TileFarmer;
import DarkS.TechXProject.machines.fluidTank.TileFluidTank;
import DarkS.TechXProject.machines.itemInterface.TileItemInterface;
import DarkS.TechXProject.machines.machineAssembler.TileMachineAssembler;
import DarkS.TechXProject.machines.node.energy.TileEnergyNode;
import DarkS.TechXProject.machines.node.item.TileItemNode;
import DarkS.TechXProject.machines.node.network.controller.TileNetworkController;
import DarkS.TechXProject.machines.node.network.relay.TileNetworkRelay;
import DarkS.TechXProject.machines.node.redstone.TileRedstoneNode;
import DarkS.TechXProject.machines.node.transport.TileTransportNode;
import DarkS.TechXProject.machines.quarry.TileQuarry;
import DarkS.TechXProject.machines.recipeChest.TileRecipeChest;
import DarkS.TechXProject.machines.recipeStamper.TileRecipeStamper;
import DarkS.TechXProject.machines.smelter.TileSmelter;
import DarkS.TechXProject.machines.solar.TileSolarPanel;
import DarkS.TechXProject.machines.storageUnit.TileStorageUnit;
import DarkS.TechXProject.machines.teleporter.TileTeleporter;
import DarkS.TechXProject.machines.wirelessCharger.TileWirelessCharger;
import DarkS.TechXProject.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum InitBlocks
{
	nodeItem(new BlockItemNode(), "nodeItem"),
	nodeEnergy(new BlockEnergyNode(), "nodeEnergy"),
	nodeTransport(new BlockTransportNode(), "nodeTransport"),
	nodeRedstone(new BlockNodeRedstone(), "nodeRedstone"),
	networkRelay(new BlockNetworkRelay(), "relay"),
	networkController(new BlockNetworkController(), "controller"),

	itemInterface(new BlockItemInterface(), "itemInterface"),

	energyMonitor(new BlockEnergyMonitor(), "energyMonitor"),
	wirelessCharger(new BlockWirelessCharger(), "wirelessCharger"),

	recipeStamper(new BlockRecipeStamper(), "recipeStamper"),
	recipeChest(new BlockRecipeChest(), "recipeChest"),
	machineAssembler(new BlockMachineAssembler(), "machineAssembler"),

	smelter(new BlockSmelter(), "smelter"),
	crusher(new BlockCrusher(), "crusher"),
	quarry(new BlockQuarry(), "quarry"),
	farmer(new BlockFarmer(), "farmer"),

	basicCapacitor(new BlockCapacitorBasic(), "basicCapacitor"),

	solarPanel(new BlockSolarPanel(), "solarPanel"),

	fluidTank(new BlockFluidTank(), "fluidTank"),

	teleporter(new BlockTeleporter(), "teleporter"),
	storageUnit(new BlockStorageUnit(), "storageUnit"),
	blockBreaker(new BlockBreaker(), "breaker"),

	activator(new BlockActivator(), "activator"),

	metalOre(new ItemBlockOreBase(new BlockOreBase()), "metalOre"),
	metalBlock(new ItemBlockMetalBase(new BlockMetalBase()), "metalBlock"),

	meteorStone(new BlockMeteorStone(), "meteorStone"),;

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
		registerTile(TileItemNode.class);
		registerTile(TileEnergyNode.class);
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
		registerTile(TileWirelessCharger.class);
		registerTile(TileActivator.class);
		registerTile(TileTransportNode.class);
		registerTile(TileFluidTank.class);
		registerTile(TileRedstoneNode.class);
		registerTile(TileItemInterface.class);
	}

	public static void initRecipes()
	{
		for (InitBlocks block : InitBlocks.values())
			if (block.block instanceof IRecipeProvider)
				((IRecipeProvider) block.block).registerRecipes();
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
			((IRenderer) block).registerModel();
		else
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(18), "inventory"));

		return block;
	}
}

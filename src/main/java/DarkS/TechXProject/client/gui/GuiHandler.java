package DarkS.TechXProject.client.gui;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.configuration.GuiConfiguration;
import DarkS.TechXProject.blocks.base.ContainerDummy;
import DarkS.TechXProject.guide.GuiGuide;
import DarkS.TechXProject.machines.capacitor.ContainerCapacitor;
import DarkS.TechXProject.machines.capacitor.GuiCapacitor;
import DarkS.TechXProject.machines.capacitor.TileCapacitor;
import DarkS.TechXProject.machines.crusher.ContainerCrusher;
import DarkS.TechXProject.machines.crusher.GuiCrusher;
import DarkS.TechXProject.machines.crusher.TileCrusher;
import DarkS.TechXProject.machines.energyMonitor.ContainerEnergyMonitor;
import DarkS.TechXProject.machines.energyMonitor.GuiEnergyMonitor;
import DarkS.TechXProject.machines.energyMonitor.TileEnergyMonitor;
import DarkS.TechXProject.machines.farmer.ContainerFarmer;
import DarkS.TechXProject.machines.farmer.GuiFarmer;
import DarkS.TechXProject.machines.farmer.TileFarmer;
import DarkS.TechXProject.machines.machineAssembler.ContainerMachineAssembler;
import DarkS.TechXProject.machines.machineAssembler.GuiMachineAssembler;
import DarkS.TechXProject.machines.machineAssembler.TileMachineAssembler;
import DarkS.TechXProject.machines.recipeChest.ContainerRecipeChest;
import DarkS.TechXProject.machines.recipeChest.GuiRecipeChest;
import DarkS.TechXProject.machines.recipeChest.TileRecipeChest;
import DarkS.TechXProject.machines.recipeStamper.ContainerRecipeStamper;
import DarkS.TechXProject.machines.recipeStamper.GuiRecipeStamper;
import DarkS.TechXProject.machines.recipeStamper.TileRecipeStamper;
import DarkS.TechXProject.machines.smelter.ContainerSmelter;
import DarkS.TechXProject.machines.smelter.GuiSmelter;
import DarkS.TechXProject.machines.smelter.TileSmelter;
import DarkS.TechXProject.machines.storageUnit.ContainerStorageUnit;
import DarkS.TechXProject.machines.storageUnit.GuiStorageUnit;
import DarkS.TechXProject.machines.storageUnit.TileStorageUnit;
import DarkS.TechXProject.machines.teleporter.ContainerTeleporter;
import DarkS.TechXProject.machines.teleporter.GuiTeleporter;
import DarkS.TechXProject.machines.teleporter.TileTeleporter;
import DarkS.TechXProject.node.item.ContainerItemNode;
import DarkS.TechXProject.node.item.gui.GuiItemNode;
import DarkS.TechXProject.node.logic.ContainerLogicConduit;
import DarkS.TechXProject.node.logic.TileLogicConduit;
import DarkS.TechXProject.node.logic.gui.GuiLogicConduit;
import DarkS.TechXProject.reference.Guis;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class GuiHandler implements IGuiHandler
{
	public static void openGui(EntityPlayer player, Object mainModClassInstance, int modGuiId, int x, int y, int z)
	{
		if (player.getEntityWorld().isRemote) return;
		FMLNetworkHandler.openGui(player, mainModClassInstance, modGuiId, player.getEntityWorld(), x, y, z);
	}

	public static void openGui(EntityPlayer player, Object mainModClassInstance, int modGuiId, BlockPos pos)
	{
		openGui(player, mainModClassInstance, modGuiId, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void openGui(EntityPlayer player, int modGuiId, BlockPos pos)
	{
		openGui(player, TechXProject.instance, modGuiId, pos);
	}

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);

		switch (ID)
		{
			case Guis.CONFIGURATION:
				return new ContainerDummy();
			case Guis.CONDUIT:
				return new ContainerItemNode(player.inventory, (IInventory) tile);
			case Guis.CONDUIT_LOGIC:
				return new ContainerLogicConduit(player.inventory, (TileLogicConduit) tile);
			case Guis.SMELTER:
				return new ContainerSmelter(player.inventory, (TileSmelter) tile);
			case Guis.CAPACITOR:
				return new ContainerCapacitor(player.inventory);
			case Guis.CRUSHER:
				return new ContainerCrusher(player.inventory, (TileCrusher) tile);
			case Guis.STORAGE_UNIT:
				return new ContainerStorageUnit(player.inventory, (TileStorageUnit) tile);
			case Guis.TELEPORTER:
				return new ContainerTeleporter(player.inventory, (IInventory) tile);
			case Guis.ENERGY_MONITOR:
				return new ContainerEnergyMonitor(player.inventory);
			case Guis.RECIPE_STAMPER:
				return new ContainerRecipeStamper(player.inventory, (TileRecipeStamper) tile);
			case Guis.MACHINE_ASSEMBLER:
				return new ContainerMachineAssembler(player.inventory, (TileMachineAssembler) tile);
			case Guis.RECIPE_CHEST:
				return new ContainerRecipeChest(player.inventory, (TileRecipeChest) tile);
			case Guis.FARMER:
				return new ContainerFarmer(player.inventory, (TileFarmer) tile);
			case Guis.GUIDE:
				return new ContainerDummy();
		}

		return null;
	}

	@Override
	public GuiScreen getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);

		switch (ID)
		{
			case Guis.CONFIGURATION:
				return new GuiConfiguration();
			case Guis.CONDUIT:
				return new GuiItemNode(player.inventory, (IInventory) tile);
			case Guis.CONDUIT_LOGIC:
				return new GuiLogicConduit(player.inventory, (TileLogicConduit) tile);
			case Guis.SMELTER:
				return new GuiSmelter(player.inventory, (TileSmelter) tile);
			case Guis.CAPACITOR:
				return new GuiCapacitor(player.inventory, (TileCapacitor) tile);
			case Guis.CRUSHER:
				return new GuiCrusher(player.inventory, (TileCrusher) tile);
			case Guis.STORAGE_UNIT:
				return new GuiStorageUnit(player.inventory, (TileStorageUnit) tile);
			case Guis.TELEPORTER:
				return new GuiTeleporter(player.inventory, (TileTeleporter) tile);
			case Guis.ENERGY_MONITOR:
				return new GuiEnergyMonitor(player.inventory, (TileEnergyMonitor) tile);
			case Guis.RECIPE_STAMPER:
				return new GuiRecipeStamper(player.inventory, (TileRecipeStamper) tile);
			case Guis.MACHINE_ASSEMBLER:
				return new GuiMachineAssembler(player.inventory, (TileMachineAssembler) tile);
			case Guis.RECIPE_CHEST:
				return new GuiRecipeChest(player.inventory, (TileRecipeChest) tile);
			case Guis.FARMER:
				return new GuiFarmer(player.inventory, (TileFarmer) tile);
			case Guis.GUIDE:
				return new GuiGuide();
		}

		return null;
	}
}

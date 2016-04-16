package DrShadow.TechXProject.gui;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.conduit.item.ContainerConduitBase;
import DrShadow.TechXProject.conduit.item.gui.GuiConduitBase;
import DrShadow.TechXProject.conduit.logic.ContainerLogicConduit;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.conduit.logic.gui.GuiLogicConduit;
import DrShadow.TechXProject.container.ContainerDummy;
import DrShadow.TechXProject.lib.Guis;
import DrShadow.TechXProject.machines.capacitor.ContainerCapacitor;
import DrShadow.TechXProject.machines.capacitor.GuiCapacitor;
import DrShadow.TechXProject.machines.capacitor.TileCapacitor;
import DrShadow.TechXProject.machines.crusher.ContainerCrusher;
import DrShadow.TechXProject.machines.crusher.GuiCrusher;
import DrShadow.TechXProject.machines.crusher.TileCrusher;
import DrShadow.TechXProject.machines.energyMonitor.ContainerEnergyMonitor;
import DrShadow.TechXProject.machines.energyMonitor.GuiEnergyMonitor;
import DrShadow.TechXProject.machines.energyMonitor.TileEnergyMonitor;
import DrShadow.TechXProject.machines.machineAssembler.ContainerMachineAssembler;
import DrShadow.TechXProject.machines.machineAssembler.GuiMachineAssembler;
import DrShadow.TechXProject.machines.machineAssembler.TileMachineAssembler;
import DrShadow.TechXProject.machines.recipeStamper.ContainerRecipeStamper;
import DrShadow.TechXProject.machines.recipeStamper.GuiRecipeStamper;
import DrShadow.TechXProject.machines.recipeStamper.TileRecipeStamper;
import DrShadow.TechXProject.machines.smelter.ContainerSmelter;
import DrShadow.TechXProject.machines.smelter.GuiSmelter;
import DrShadow.TechXProject.machines.smelter.TileSmelter;
import DrShadow.TechXProject.machines.storageUnit.ContainerStorageUnit;
import DrShadow.TechXProject.machines.storageUnit.GuiStorageUnit;
import DrShadow.TechXProject.machines.storageUnit.TileStorageUnit;
import DrShadow.TechXProject.machines.teleporter.ContainerTeleporter;
import DrShadow.TechXProject.machines.teleporter.GuiTeleporter;
import DrShadow.TechXProject.machines.teleporter.TileTeleporter;
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
				return new ContainerConduitBase(player.inventory, (IInventory) tile);
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
				return new GuiConduitBase(player.inventory, (IInventory) tile);
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
		}

		return null;
	}
}

package DrShadow.TechXProject.gui;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.conduit.item.ContainerConduitBase;
import DrShadow.TechXProject.conduit.item.TileConduitItem;
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

		switch (ID)
		{
			case Guis.CONFIGURATION:
				return new ContainerDummy();
			case Guis.CONDUIT:
				TileConduitItem conduit = (TileConduitItem) world.getTileEntity(pos);
				return new ContainerConduitBase(player.inventory, conduit);
			case Guis.CONDUIT_LOGIC:
				TileLogicConduit logicConduit = (TileLogicConduit) world.getTileEntity(pos);
				return new ContainerLogicConduit(player.inventory, logicConduit);
			case Guis.SMELTER:
				TileSmelter smelter = (TileSmelter) world.getTileEntity(pos);
				return new ContainerSmelter(player.inventory, smelter);
			case Guis.BATTERY:
				return new ContainerCapacitor(player.inventory);
			case Guis.CRUSHER:
				TileCrusher crusher = (TileCrusher) world.getTileEntity(pos);
				return new ContainerCrusher(player.inventory, crusher);
			case Guis.STORAGE_UNIT:
				TileStorageUnit storageUnit = (TileStorageUnit) world.getTileEntity(pos);
				return new ContainerStorageUnit(player.inventory, storageUnit);
			case Guis.TELEPORTER:
				TileTeleporter teleporter = (TileTeleporter) world.getTileEntity(pos);
				return new ContainerTeleporter(player.inventory, teleporter);
		}

		return null;
	}

	@Override
	public GuiScreen getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		switch (ID)
		{
			case Guis.CONFIGURATION:
				return new GuiConfiguration();
			case Guis.CONDUIT:
				TileConduitItem conduit = (TileConduitItem) world.getTileEntity(pos);
				return new GuiConduitBase(player.inventory, conduit);
			case Guis.CONDUIT_LOGIC:
				TileLogicConduit logicConduit = (TileLogicConduit) world.getTileEntity(pos);
				return new GuiLogicConduit(player.inventory, logicConduit);
			case Guis.SMELTER:
				TileSmelter smelter = (TileSmelter) world.getTileEntity(pos);
				return new GuiSmelter(player.inventory, smelter);
			case Guis.BATTERY:
				TileCapacitor battery = (TileCapacitor) world.getTileEntity(pos);
				return new GuiCapacitor(player.inventory, battery);
			case Guis.CRUSHER:
				TileCrusher crusher = (TileCrusher) world.getTileEntity(pos);
				return new GuiCrusher(player.inventory, crusher);
			case Guis.STORAGE_UNIT:
				TileStorageUnit storageUnit = (TileStorageUnit) world.getTileEntity(pos);
				return new GuiStorageUnit(player.inventory, storageUnit);
			case Guis.TELEPORTER:
				TileTeleporter teleporter = (TileTeleporter) world.getTileEntity(pos);
				return new GuiTeleporter(player.inventory, teleporter);
		}

		return null;
	}
}

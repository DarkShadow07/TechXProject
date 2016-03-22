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
import DrShadow.TechXProject.machines.battery.ContainerBattery;
import DrShadow.TechXProject.machines.battery.GuiBattery;
import DrShadow.TechXProject.machines.battery.TileBatteryBase;
import DrShadow.TechXProject.machines.smelter.ContainerSmelter;
import DrShadow.TechXProject.machines.smelter.GuiSmelter;
import DrShadow.TechXProject.machines.smelter.TileSmelter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
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
				return new ContainerBattery(player.inventory);
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
				TileBatteryBase battery = (TileBatteryBase) world.getTileEntity(pos);
				return new GuiBattery(player.inventory, battery);
		}

		return null;
	}
}

package DrShadow.TechXProject.proxy;

import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.init.InitItems;
import DrShadow.TechXProject.packets.PacketHandler;
import DrShadow.TechXProject.util.InventoryRenderHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

public abstract class CommonProxy implements IProxy
{
	public MinecraftServer getMinecraftServer()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}

	public World getClientWorld()
	{
		return null;
	}

	public InventoryRenderHelper getInventoryRenderHelper()
	{
		return null;
	}

	@Override
	public void registerBlocks()
	{
		InitBlocks.init();

		InitBlocks.initRecipes();
	}

	@Override
	public void registerItems()
	{
		InitItems.init();

		InitItems.initRecipes();
	}

	@Override
	public void registerKeyBindings()
	{

	}

	@Override
	public void registerPacketHandler()
	{
		PacketHandler.init();
	}

	@Override
	public void registerEvents()
	{

	}

	@Override
	public void registerConfiguration(File file)
	{

	}

	@Override
	public void registerThreads()
	{

	}
}

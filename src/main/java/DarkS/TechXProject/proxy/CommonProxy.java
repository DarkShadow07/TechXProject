package DarkS.TechXProject.proxy;

import DarkS.TechXProject.enchantment.EnchantmentEnergyRepair;
import DarkS.TechXProject.init.InitBlocks;
import DarkS.TechXProject.init.InitItems;
import DarkS.TechXProject.packets.PacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

public class CommonProxy implements IProxy
{
	public MinecraftServer getMinecraftServer()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}

	public World getClientWorld()
	{
		return null;
	}

	@Override
	public void preInit()
	{

	}

	@Override
	public void init()
	{

	}

	@Override
	public void postInit()
	{

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

		GameRegistry.register(new EnchantmentEnergyRepair());
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

	@Override
	public void registerRenders()
	{

	}
}

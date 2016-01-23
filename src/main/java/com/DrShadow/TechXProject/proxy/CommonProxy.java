package com.DrShadow.TechXProject.proxy;

import com.DrShadow.TechXProject.init.InitBlocks;
import com.DrShadow.TechXProject.init.InitItems;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

public abstract class CommonProxy implements IProxy
{
	public static MinecraftServer getMinecraftServer()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}

	@Override
	public void registerBlocks()
	{
		InitBlocks.init();
	}

	@Override
	public void registerItems()
	{
		InitItems.init();
	}

	@Override
	public void registerGuis()
	{

	}

	@Override
	public void registerKeyBindings()
	{

	}

	@Override
	public void registerPacketHandler()
	{

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
	public void registerRenderers()
	{

	}

	@Override
	public void registerThreads()
	{

	}
}

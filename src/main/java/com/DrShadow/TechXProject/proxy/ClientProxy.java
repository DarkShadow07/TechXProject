package com.DrShadow.TechXProject.proxy;

import com.DrShadow.TechXProject.events.RenderEvents;
import com.DrShadow.TechXProject.events.UpdateEvents;
import com.DrShadow.TechXProject.events.WorldEvents;
import com.DrShadow.TechXProject.init.InitBlocks;
import com.DrShadow.TechXProject.init.InitItems;
import com.DrShadow.TechXProject.render.TeslaTowerRenderer;
import com.DrShadow.TechXProject.tileEntities.TileTeslaTower;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		InitBlocks.initRenders();
		InitItems.initRenders();

		ClientRegistry.bindTileEntitySpecialRenderer(TileTeslaTower.class, new TeslaTowerRenderer());
	}

	@Override
	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new RenderEvents());
		MinecraftForge.EVENT_BUS.register(new WorldEvents());
		FMLCommonHandler.instance().bus().register(new UpdateEvents());
	}
}

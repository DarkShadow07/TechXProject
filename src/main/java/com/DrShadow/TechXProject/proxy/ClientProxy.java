package com.DrShadow.TechXProject.proxy;

import com.DrShadow.TechXProject.events.RenderEvent;
import com.DrShadow.TechXProject.init.InitBlocks;
import com.DrShadow.TechXProject.init.InitItems;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		InitBlocks.initRenders();
		InitItems.initRenders();
	}

	@Override
	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new RenderEvent());
	}
}

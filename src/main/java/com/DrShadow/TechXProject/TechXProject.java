package com.DrShadow.TechXProject;

import com.DrShadow.TechXProject.lib.CreativeTabTech;
import com.DrShadow.TechXProject.proxy.CommonProxy;
import com.DrShadow.TechXProject.reference.Reference;
import com.DrShadow.TechXProject.util.UpdateChecker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:DarkLapisCore@[0.00.0 Pre-Alpha,)")
public class TechXProject
{
	@Mod.Instance(Reference.MOD_ID)
	public static TechXProject instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		UpdateChecker.init();

		proxy.registerBlocks();
		proxy.registerItems();
		proxy.registerKeyBindings();
		proxy.registerGuis();
		proxy.registerConfiguration(event.getSuggestedConfigurationFile());
		proxy.registerPacketHandler();
	}

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerEvents();
	    proxy.registerRenderers();
    }

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.registerThreads();
	}

	public static CreativeTabs techTab = new CreativeTabTech("techTab");
}

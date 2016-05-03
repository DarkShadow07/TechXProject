package DrShadow.TechXProject;

import DrShadow.TechXProject.client.gui.GuiHandler;
import DrShadow.TechXProject.configuration.ConfigurationHandler;
import DrShadow.TechXProject.init.InitChestLoot;
import DrShadow.TechXProject.proxy.CommonProxy;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import DrShadow.TechXProject.util.UpdateChecker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, updateJSON = Reference.UPDATE_JSON)
public class TechXProject
{
	@Mod.Instance(Reference.MOD_ID)
	public static TechXProject instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static ConfigurationHandler configurationHandler;

	public static CreativeTabs techTab = new CreativeTabTech("techTab");
	public static CreativeTabs oresTab = new CreativeTabTech.CreativeTabOres("techTabOres");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		UpdateChecker.init();
		proxy.registerBlocks();
		proxy.registerItems();
		proxy.registerKeyBindings();
		proxy.registerConfiguration(event.getSuggestedConfigurationFile());
		proxy.registerPacketHandler();

		NetworkRegistry.INSTANCE.registerGuiHandler(TechXProject.instance, new GuiHandler());

		Logger.info("Pre-Initialization Completed!");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();

		if (Loader.isModLoaded("Waila"))
		{
			Logger.info("Waila Integration Loaded Successfully!");
			FMLInterModComms.sendMessage("Waila", "register", Reference.PATH_INTEGRATIONS + "waila.WailaTileHandler.register");
		}

		proxy.registerEvents();

		InitChestLoot.init();

		Logger.info("Initialization Completed!");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();

		proxy.registerThreads();

		configurationHandler = new ConfigurationHandler();

		Logger.info("Post-Initialization Completed!");
	}
}

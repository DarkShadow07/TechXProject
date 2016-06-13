package DarkS.TechXProject;

import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.configuration.ConfigurationHandler;
import DarkS.TechXProject.init.InitChestLoot;
import DarkS.TechXProject.proxy.CommonProxy;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Logger;
import com.google.common.base.Stopwatch;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.concurrent.TimeUnit;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, updateJSON = Reference.UPDATE_JSON)
public class TechXProject
{
	@Mod.Instance(Reference.MOD_ID)
	public static TechXProject instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static ConfigurationHandler configurationHandler;

	public static CreativeTabs techTab = new CreativeTabsTech.CreativeTabTech("techTab");
	public static CreativeTabs oresTab = new CreativeTabsTech.CreativeTabOres("techTabOres");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Stopwatch stopwatch = Stopwatch.createStarted();

		proxy.registerBlocks();
		proxy.registerItems();
		proxy.registerKeyBindings();
		proxy.registerConfiguration(event.getSuggestedConfigurationFile());
		proxy.registerPacketHandler();

		configurationHandler = new ConfigurationHandler();

		NetworkRegistry.INSTANCE.registerGuiHandler(TechXProject.instance, new GuiHandler());

		Logger.info("Pre-Initialization Completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms!");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Stopwatch stopwatch = Stopwatch.createStarted();

		proxy.init();

		if (Loader.isModLoaded("Waila"))
		{
			Logger.info("Waila Integration Loaded Successfully!");
			FMLInterModComms.sendMessage("Waila", "register", Reference.PATH_COMPAT + "waila.WailaTileHandler.register");
		}

		proxy.registerEvents();

		proxy.registerRenders();

		InitChestLoot.init();

		Logger.info("Initialization Completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms!");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Stopwatch stopwatch = Stopwatch.createStarted();
		proxy.postInit();

		proxy.registerThreads();

		configurationHandler.readConfig();

		Logger.info("Post-Initialization Completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms!");
	}

	@EventHandler
	public void onIMC(FMLInterModComms.IMCEvent event)
	{

	}
}

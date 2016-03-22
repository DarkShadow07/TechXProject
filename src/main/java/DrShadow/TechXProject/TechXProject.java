package DrShadow.TechXProject;

import DrShadow.TechXProject.configuration.ConfigurationHandler;
import DrShadow.TechXProject.gui.GuiHandler;
import DrShadow.TechXProject.lib.CreativeTabTech;
import DrShadow.TechXProject.proxy.CommonProxy;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.UpdateChecker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "after:JEI@[2.23.0,);required-after:DarkLapisCore@[0.00.0 Pre-Alpha,)")
public class TechXProject
{
	@Mod.Instance(Reference.MOD_ID)
	public static TechXProject instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static ConfigurationHandler configurationHandler;

	public static CreativeTabs techTab = new CreativeTabTech("techTab");

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

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerEvents();

		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.registerThreads();

		configurationHandler = new ConfigurationHandler();

		proxy.postInit();
	}
}

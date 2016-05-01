package DrShadow.TechXProject;

import DrShadow.TechXProject.client.gui.GuiHandler;
import DrShadow.TechXProject.configuration.ConfigurationHandler;
import DrShadow.TechXProject.init.InitChestLoot;
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

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class TechXProject
{
	@Mod.Instance(Reference.MOD_ID)
	public static TechXProject instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static ConfigurationHandler configurationHandler;

	public static CreativeTabs techTab = new CreativeTabTech("techTab");
	public static CreativeTabs oresTab;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		UpdateChecker.init();
		proxy.registerBlocks();
		proxy.registerItems();
		proxy.registerKeyBindings();
		proxy.registerConfiguration(event.getSuggestedConfigurationFile());
		proxy.registerPacketHandler();

		oresTab = new CreativeTabTech.CreativeTabOres("techTabOres");

		NetworkRegistry.INSTANCE.registerGuiHandler(TechXProject.instance, new GuiHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();

		proxy.registerEvents();

		InitChestLoot.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();

		proxy.registerThreads();

		configurationHandler = new ConfigurationHandler();
	}
}

package DrShadow.TechXProject.proxy;

import DrShadow.TechXProject.events.*;
import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.init.InitItems;
import DrShadow.TechXProject.keyBindings.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new RenderEvents());
		MinecraftForge.EVENT_BUS.register(new WorldEvents());
		MinecraftForge.EVENT_BUS.register(new KeyInputEvent());
		MinecraftForge.EVENT_BUS.register(new TickEvents());
		MinecraftForge.EVENT_BUS.register(new AnvilEvent());
	}

	@Override
	public void preInit()
	{

	}

	@Override
	public void init()
	{
		InitBlocks.initRenders();
		InitItems.initRenders();
	}

	@Override
	public void postInit()
	{

	}

	@Override
	public void registerKeyBindings()
	{
		ClientRegistry.registerKeyBinding(KeyBindings.configuration);
	}
}

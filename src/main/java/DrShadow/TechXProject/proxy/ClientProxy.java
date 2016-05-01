package DrShadow.TechXProject.proxy;

import DrShadow.TechXProject.client.keyBindings.KeyBindings;
import DrShadow.TechXProject.events.*;
import DrShadow.TechXProject.world.WorldGen;
import DrShadow.TechXProject.world.WorldTickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new RenderEvents());
		MinecraftForge.EVENT_BUS.register(new WorldEvents());
		MinecraftForge.EVENT_BUS.register(new KeyInputEvent());
		MinecraftForge.EVENT_BUS.register(new TickEvents());
		MinecraftForge.EVENT_BUS.register(new AnvilEvent());

		GameRegistry.registerWorldGenerator(new WorldGen(), 10);
		MinecraftForge.EVENT_BUS.register(new WorldGen());

		MinecraftForge.EVENT_BUS.register(new WorldTickHandler());
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
	public void registerKeyBindings()
	{
		ClientRegistry.registerKeyBinding(KeyBindings.configuration);
	}

	@Override
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}

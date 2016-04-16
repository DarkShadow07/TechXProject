package DrShadow.TechXProject.proxy;

import DrShadow.TechXProject.events.*;
import DrShadow.TechXProject.keyBindings.KeyBindings;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.InventoryRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	private InventoryRenderHelper inventoryRenderHelper;

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
		inventoryRenderHelper = new InventoryRenderHelper(Reference.MOD_ID);
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
	public InventoryRenderHelper getInventoryRenderHelper()
	{
		return inventoryRenderHelper;
	}

	@Override
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}

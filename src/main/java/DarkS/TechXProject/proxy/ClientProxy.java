package DarkS.TechXProject.proxy;

import DarkS.TechXProject.client.keyBindings.KeyBindings;
import DarkS.TechXProject.events.*;
import DarkS.TechXProject.machines.canvas.TileCanvas;
import DarkS.TechXProject.machines.canvas.TileCanvasRenderer;
import DarkS.TechXProject.machines.fluidTank.TileFluidTank;
import DarkS.TechXProject.machines.fluidTank.TileFluidTankRender;
import DarkS.TechXProject.node.redstone.TileRedstoneNode;
import DarkS.TechXProject.node.redstone.TileRedstoneNodeRenderer;
import DarkS.TechXProject.node.transport.TileTransportNode;
import DarkS.TechXProject.node.transport.TileTransportNodeRender;
import DarkS.TechXProject.world.WorldGen;
import DarkS.TechXProject.world.WorldTickHandler;
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
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());

		GameRegistry.registerWorldGenerator(new WorldGen(), 16);
		MinecraftForge.ORE_GEN_BUS.register(new WorldGen());

		MinecraftForge.EVENT_BUS.register(new WorldTickHandler());
	}

	@Override
	public void registerRenders()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileTransportNode.class, new TileTransportNodeRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileFluidTank.class, new TileFluidTankRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileRedstoneNode.class, new TileRedstoneNodeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCanvas.class, new TileCanvasRenderer());
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

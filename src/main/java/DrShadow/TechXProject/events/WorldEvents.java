package DrShadow.TechXProject.events;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.client.render.IRenderObject;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.ChatUtil;
import DrShadow.TechXProject.util.PartialTicksUtil;
import DrShadow.TechXProject.util.UpdateChecker;
import DrShadow.TechXProject.util.VectorUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldEvents
{
	public static List<IRenderObject> renderObjects = new ArrayList<>();

	public static void addRenderObject(IRenderObject obj)
	{
		renderObjects.add(obj);
	}

	@SubscribeEvent
	public void worldRenderLast(RenderWorldLastEvent event)
	{
		Vec3d pos = VectorUtil.multiply(PartialTicksUtil.calculatePos(Minecraft.getMinecraft().getRenderViewEntity()), -1);

		GlStateManager.pushMatrix();

		GlStateManager.translate(pos.xCoord, pos.yCoord, pos.zCoord);

		Iterator toRender = renderObjects.iterator();
		while (toRender.hasNext())
		{
			IRenderObject obj = (IRenderObject) toRender.next();

			obj.render();
			if (obj.isDead()) toRender.remove();
		}

		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public void worldSaveEvent(WorldEvent.Save event)
	{
		TechXProject.configurationHandler.writeConfig();
	}

	@SubscribeEvent
	public void worldLoadEvent(WorldEvent.Load event)
	{
		TechXProject.configurationHandler.readConfig();

		if (UpdateChecker.show)
		{
			ChatUtil.sendNoSpamClient(Reference.MOD_NAME + " is Outdated! " + ChatFormatting.RED + UpdateChecker.currentVersion + ChatFormatting.RESET + " || " + ChatFormatting.AQUA + UpdateChecker.newestVersion);
		}
	}
}

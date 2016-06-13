package DarkS.TechXProject.events;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.client.EffectManager;
import DarkS.TechXProject.client.render.IRenderObject;
import DarkS.TechXProject.util.PartialTicksUtil;
import DarkS.TechXProject.util.VectorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;

public class WorldEvents
{
	@SubscribeEvent
	public void worldRenderLast(RenderWorldLastEvent event)
	{
		Vec3d pos = VectorUtil.multiply(PartialTicksUtil.calculatePos(Minecraft.getMinecraft().getRenderViewEntity()), -1);

		GlStateManager.pushMatrix();

		GlStateManager.translate(pos.xCoord, pos.yCoord, pos.zCoord);

		RenderHelper.enableStandardItemLighting();

		Iterator toRender = EffectManager.getRenderObjects().iterator();

		while (toRender.hasNext())
		{
			IRenderObject obj = (IRenderObject) toRender.next();

			obj.render();

			if (obj.isDead()) toRender.remove();

			obj.setDead();
		}

		RenderHelper.disableStandardItemLighting();

		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public void worldSaveEvent(WorldEvent.Save event)
	{
		TechXProject.configurationHandler.writeConfig();
	}
}

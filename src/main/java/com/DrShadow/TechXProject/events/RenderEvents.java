package com.DrShadow.TechXProject.events;

import com.DrShadow.TechXProject.util.GL11Util;
import com.DrShadow.TechXProject.util.Helper;
import com.DrShadow.TechXProject.util.OverlayHelper;
import com.DrShadow.TechXProject.util.UpdateChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderEvents
{
	private FontRenderer fontRenderer;

	public static float partialTicks = 0;

	@SubscribeEvent
	public void render(RenderGameOverlayEvent event)
	{
		if (!Minecraft.isGuiEnabled()) return;
		if (event.type != RenderGameOverlayEvent.ElementType.CHAT) return;

		renderChat(event.resolution, event.partialTicks);
	}

	public void renderChat(ScaledResolution res, float ticks)
	{
		OverlayHelper overlayHelper = new OverlayHelper();

		int time = ChatMessageEvent.ticksExisted;

		if (ChatMessageEvent.getMessage() != "")
		{
			String msg = ChatMessageEvent.getMessage();

			FontRenderer fontRenderer = Helper.minecraft().fontRendererObj;

			int x = res.getScaledWidth() - fontRenderer.getStringWidth(msg) - 2;
			int y = res.getScaledHeight() - 36;


			GL11Util.startOpaqueRendering();

			GL11.glColor4f(0, 0, 0, 0.3f * ChatMessageEvent.alpha);

			overlayHelper.drawPlane(x - 1, y - 1, fontRenderer.getStringWidth(msg) + 1, 9, new Color(0, 0, 0, 0.3F * ChatMessageEvent.alpha).hashCode());
			fontRenderer.drawStringWithShadow(msg, x, y, new Color(1, 1, 1, ChatMessageEvent.alpha).hashCode());

			GL11Util.endOpaqueRendering();
		}
	}

	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		partialTicks = event.partialTicks;
	}
}

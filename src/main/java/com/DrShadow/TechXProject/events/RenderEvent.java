package com.DrShadow.TechXProject.events;

import com.DrShadow.TechXProject.util.Helper;
import com.DrShadow.TechXProject.util.OverlayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class RenderEvent
{
	private FontRenderer fontRenderer;

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

		if (ChatMessageEvent.getMessage() != "" && time >= 1)
		{
			String msg = ChatMessageEvent.getMessage();

			FontRenderer fontRenderer = Helper.minecraft().fontRendererObj;

			int x = res.getScaledWidth() - fontRenderer.getStringWidth(msg) - 2;
			int y = res.getScaledHeight() - 36;

			ChatMessageEvent.ticksExisted -= 1;

			overlayHelper.drawPlane(x - 1, y - 1, fontRenderer.getStringWidth(msg) + 1, 9, new Color(0, 0, 0, 0.3F).hashCode());
			fontRenderer.drawStringWithShadow(msg, x, y, new Color(1, 1, 1, 1).hashCode());
		}else ChatMessageEvent.removeMessage();
	}
}

package com.DrShadow.TechXProject.events;

import com.DrShadow.TechXProject.power.PowerTile;
import com.DrShadow.TechXProject.util.Helper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.text.NumberFormat;

public class RenderEvent
{
	private FontRenderer fontRenderer;

	@SubscribeEvent
	public void render(RenderGameOverlayEvent event)
	{
		if (!Minecraft.isGuiEnabled()) return;
		if (event.type != RenderGameOverlayEvent.ElementType.CHAT) return;

		fontRenderer = Helper.minecraft().fontRendererObj;

		BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();

		World world = Helper.getTheWorld();

		if (pos != null)
		{
			Block block = world.getBlockState(pos).getBlock();

			PowerTile tile = (PowerTile) world.getTileEntity(pos);

			if (tile != null)
			{
				fontRenderer.drawString(NumberFormat.getInstance().format(tile.getPower()) + " / " + NumberFormat.getInstance().format(tile.getMaxPower()), 14, 14, Color.white.hashCode(), true);
			}

		}

	}
}

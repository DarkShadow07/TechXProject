package DrShadow.TechXProject.events;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.api.energy.IEnergyGenerator;
import DrShadow.TechXProject.util.RenderUtil;
import DrShadow.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.text.NumberFormat;

public class RenderEvents
{
	public static ScaledResolution resolution;

	@SubscribeEvent
	public void renderHUD(RenderGameOverlayEvent.Post event)
	{
		resolution = event.getResolution();

		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			if (Util.minecraft().objectMouseOver != null && Util.minecraft().objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK))
			{
				World world = Util.world();
				BlockPos pos = Util.minecraft().objectMouseOver.getBlockPos();

				RenderUtil.drawFakeBlock(new ResourceLocation("textures/blocks/stone.png"), pos.getX(), pos.getY() + 0.5f, pos.getZ());

				TileEntity tile = world.getTileEntity(pos);

				if (tile != null && tile instanceof IEnergyContainer)
				{
					IEnergyContainer container = (IEnergyContainer) tile;

					String energy = NumberFormat.getInstance().format(container.getEnergy()) + "/" + NumberFormat.getInstance().format(container.getMaxEnergy());

					FontRenderer fontRenderer = Util.minecraft().fontRendererObj;

					fontRenderer.drawStringWithShadow(tile.getBlockType().getLocalizedName(), resolution.getScaledWidth() / 2 + 8, resolution.getScaledHeight() / 2, Color.white.getRGB());
					fontRenderer.drawStringWithShadow(ChatFormatting.GRAY + energy, (resolution.getScaledWidth() / 2) + 8, resolution.getScaledHeight() / 2 + 10, Color.white.hashCode());

					if (tile instanceof IEnergyGenerator)
					{
						IEnergyGenerator generator = (IEnergyGenerator) tile;

						String generating = String.format("Generating %sTF/t", ChatFormatting.GREEN + NumberFormat.getInstance().format(generator.getGenerating()) + ChatFormatting.GRAY);

						fontRenderer.drawStringWithShadow(ChatFormatting.GRAY + generating, (resolution.getScaledWidth() / 2) + 8, resolution.getScaledHeight() / 2 + 20, Color.white.getRGB());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void renderDebug(RenderGameOverlayEvent.Text event)
	{
		if (!Util.minecraft().gameSettings.showDebugInfo) return;

		event.getLeft().add(5, "Fx: " + WorldEvents.renderObjects.size());
	}
}

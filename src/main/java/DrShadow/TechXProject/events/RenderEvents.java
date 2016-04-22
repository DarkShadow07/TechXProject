package DrShadow.TechXProject.events;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.api.energy.IEnergyGenerator;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.items.itemWire.ItemWire;
import DrShadow.TechXProject.util.Logger;
import DrShadow.TechXProject.util.RenderUtil;
import DrShadow.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.NumberFormat;

public class RenderEvents
{
	public static final String PREFIX = ChatFormatting.AQUA + "[Tech'X'Project]" + ChatFormatting.RESET;
	public static ScaledResolution resolution;

	@SubscribeEvent
	public void renderHUD(RenderGameOverlayEvent.Post event)
	{
		resolution = event.getResolution();

		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			if (Util.minecraft().objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK))
			{
				World world = Util.world();
				BlockPos pos = Util.minecraft().objectMouseOver.getBlockPos();

				TileEntity tile = world.getTileEntity(pos);

				if (tile != null && tile instanceof IEnergyContainer)
				{
					IEnergyContainer container = (IEnergyContainer) tile;

					String energy = NumberFormat.getInstance().format(container.getEnergy()) + "/" + NumberFormat.getInstance().format(container.getMaxEnergy());

					FontRenderer fontRenderer = Util.minecraft().fontRendererObj;

					fontRenderer.drawStringWithShadow(tile.getBlockType().getLocalizedName(), resolution.getScaledWidth() / 2 + 8, resolution.getScaledHeight() / 2, Color.WHITE.getRGB());
					fontRenderer.drawStringWithShadow(energy, (resolution.getScaledWidth() / 2) + 8, resolution.getScaledHeight() / 2 + 10, Color.ORANGE.hashCode());

					if (tile instanceof IEnergyGenerator)
					{
						IEnergyGenerator generator = (IEnergyGenerator) tile;

						String generating = "Generating " + NumberFormat.getInstance().format(generator.getGenerating()) + " TF/t";

						fontRenderer.drawStringWithShadow(generating, (resolution.getScaledWidth() / 2) + 8, resolution.getScaledHeight() / 2 + 20, Color.ORANGE.getRGB());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void renderDebug(RenderGameOverlayEvent.Text event)
	{
		if (!Util.minecraft().gameSettings.showDebugInfo) return;

		if (Util.minecraft().objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK))
		{
			World world = Util.world();
			BlockPos pos = Util.minecraft().objectMouseOver.getBlockPos();

			TileEntity tile = world.getTileEntity(pos);

			if (tile != null && tile instanceof INetworkElement)
			{
				INetworkElement element = (INetworkElement) tile;

				event.getRight().add("input: " + element.isInput());
				event.getRight().add("output: " + element.isOutput());
			}
		}
	}
}

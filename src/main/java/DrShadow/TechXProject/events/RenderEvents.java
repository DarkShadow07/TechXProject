package DrShadow.TechXProject.events;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.text.NumberFormat;

public class RenderEvents
{
	public static final String PREFIX = ChatFormatting.AQUA + "[Tech'X'Project]" + ChatFormatting.RESET;
	public static ScaledResolution resolution;

	@SubscribeEvent
	public void renderHUD(RenderGameOverlayEvent.Post event)
	{
		resolution = event.resolution;

		if (event.type == RenderGameOverlayEvent.ElementType.ALL)
		{
			if (Util.minecraft().objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK))
			{
				World world = Util.world();
				BlockPos pos = Util.minecraft().objectMouseOver.getBlockPos();

				TileEntity tile = world.getTileEntity(pos);

				if (tile != null && tile instanceof IEnergyContainer)
				{
					IEnergyContainer container = (IEnergyContainer) tile;

					String energy = NumberFormat.getInstance().format(container.getEnergy()) + "/" + NumberFormat.getInstance().format(container.getMaxEnergy());

					FontRenderer fontRenderer = Util.minecraft().fontRendererObj;

					fontRenderer.drawStringWithShadow(tile.getBlockType().getLocalizedName(), (resolution.getScaledWidth() / 2) + 8, resolution.getScaledHeight() / 2, Color.WHITE.getRGB());
					fontRenderer.drawStringWithShadow(energy, (resolution.getScaledWidth() / 2) + 8, resolution.getScaledHeight() / 2 + 10, Color.ORANGE.hashCode());
				}
			}
		}
	}

	@SubscribeEvent
	public void renderDebug(RenderGameOverlayEvent.Text event)
	{
		if (!Util.minecraft().gameSettings.showDebugInfo) return;
	}
}

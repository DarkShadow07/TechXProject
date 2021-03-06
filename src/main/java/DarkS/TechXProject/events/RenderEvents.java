package DarkS.TechXProject.events;

import DarkS.TechXProject.api.energy.IEnergyContainer;
import DarkS.TechXProject.api.energy.IEnergyGenerator;
import DarkS.TechXProject.blocks.tile.highlight.IHighlightProvider;
import DarkS.TechXProject.blocks.tile.highlight.SelectionBox;
import DarkS.TechXProject.capability.CustomCapabilities;
import DarkS.TechXProject.client.EffectManager;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.PartialTicksUtil;
import DarkS.TechXProject.util.Renderer;
import DarkS.TechXProject.util.Util;
import DarkS.TechXProject.util.VectorUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.text.NumberFormat;

public class RenderEvents
{
	@SubscribeEvent
	public void drawHighlightEvent(DrawBlockHighlightEvent event)
	{
		World world = event.getPlayer().worldObj;

		if (event.getTarget() != null && event.getTarget().typeOfHit.equals(RayTraceResult.Type.BLOCK))
		{
			BlockPos pos = event.getTarget().getBlockPos();

			TileEntity tile = world.getTileEntity(pos);

			if (tile != null && tile instanceof IHighlightProvider)
			{
				GlStateManager.pushMatrix();

				Vec3d off = VectorUtil.multiply(PartialTicksUtil.calculatePos(event.getPlayer()), -1).add(new Vec3d(pos));
				GlStateManager.translate(off.xCoord, off.yCoord, off.zCoord);

				GlStateManager.disableLighting();
				GlStateManager.depthMask(false);
				GlStateManager.disableTexture2D();

				Util.GL.startOpaqueRendering();

				event.setCanceled(true);

				IHighlightProvider provider = (IHighlightProvider) tile;

				double blockReachDistance = event.getPlayer() instanceof EntityPlayerMP ? ((EntityPlayerMP) event.getPlayer()).interactionManager.getBlockReachDistance() : 4.5d;

				Vec3d start = event.getPlayer().getPositionEyes(event.getPartialTicks());
				Vec3d look = event.getPlayer().getLook(event.getPartialTicks());
				Vec3d end = start.addVector(look.xCoord * blockReachDistance, look.yCoord * blockReachDistance, look.zCoord * blockReachDistance);

				for (SelectionBox box : provider.getSelectedBoxes(pos, start, end))
				{
					if (box != null)
					{
						for (AxisAlignedBB selectedBox : box.getBoxes())
						{
							AxisAlignedBB expBox = selectedBox.expand(0.002, 0.002, 0.002);
							drawBox(expBox.minX, expBox.minY, expBox.minZ, expBox.maxX, expBox.maxY, expBox.maxZ);
						}
					}
				}

				Util.GL.endOpaqueRendering();

				GlStateManager.enableLighting();
				GlStateManager.depthMask(true);
				GlStateManager.enableTexture2D();

				GlStateManager.popMatrix();
			}
		}
	}

	private void drawBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		GlStateManager.disableTexture2D();

		GlStateManager.color(0, 0, 0, 0.5f);

		GlStateManager.glLineWidth(2);

		Renderer.LINES.begin();

		Renderer.LINES.addVertex(minX, minY, minZ);
		Renderer.LINES.addVertex(minX, maxY, minZ);

		Renderer.LINES.addVertex(minX, minY, minZ);
		Renderer.LINES.addVertex(maxX, minY, minZ);

		Renderer.LINES.addVertex(minX, minY, minZ);
		Renderer.LINES.addVertex(minX, minY, maxZ);

		Renderer.LINES.addVertex(maxX, minY, minZ);
		Renderer.LINES.addVertex(maxX, maxY, minZ);

		Renderer.LINES.addVertex(minX, minY, maxZ);
		Renderer.LINES.addVertex(minX, maxY, maxZ);

		Renderer.LINES.addVertex(maxX, minY, maxZ);
		Renderer.LINES.addVertex(maxX, maxY, maxZ);

		Renderer.LINES.addVertex(maxX, minY, minZ);
		Renderer.LINES.addVertex(maxX, minY, maxZ);

		Renderer.LINES.addVertex(minX, minY, maxZ);
		Renderer.LINES.addVertex(maxX, minY, maxZ);

		Renderer.LINES.addVertex(minX, maxY, minZ);
		Renderer.LINES.addVertex(maxX, maxY, minZ);

		Renderer.LINES.addVertex(minX, maxY, minZ);
		Renderer.LINES.addVertex(minX, maxY, maxZ);

		Renderer.LINES.addVertex(maxX, maxY, minZ);
		Renderer.LINES.addVertex(maxX, maxY, maxZ);

		Renderer.LINES.addVertex(minX, maxY, maxZ);
		Renderer.LINES.addVertex(maxX, maxY, maxZ);

		Renderer.LINES.draw();

		GlStateManager.enableTexture2D();
	}

	@SubscribeEvent
	public void renderOverlayEvent(RenderGameOverlayEvent.Post event)
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			FontRenderer fontRenderer = Util.minecraft().fontRendererObj;

			if (Util.player().hasCapability(CustomCapabilities.ENERGY_CAPABILITY, null) && !Util.player().capabilities.isCreativeMode)
			{
				CustomCapabilities.IEnergyData data = Util.player().getCapability(CustomCapabilities.ENERGY_CAPABILITY, null);

				if (data.getEnergy() > 0)
				{
					GlStateManager.pushMatrix();

					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

					Util.minecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icon/energyBarEmpty.png"));
					Gui.drawModalRectWithCustomSizedTexture(res.getScaledWidth() / 2 - 182 / 2, res.getScaledHeight() - 28, 0, 0, 182, 5, 182, 5);

					Util.minecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icon/energyBar.png"));
					Gui.drawModalRectWithCustomSizedTexture(res.getScaledWidth() / 2 - 182 / 2, res.getScaledHeight() - 28, 0, 0, (int) ((float) data.getEnergy() / data.getMaxEnergy() * 182), 5, 182, 5);

					String energy = String.format("%s/%s", NumberFormat.getInstance().format(data.getEnergy()), NumberFormat.getInstance().format(data.getMaxEnergy()));

					GlStateManager.scale(0.75f, 0.75f, 0.75f);

					if (GuiScreen.isAltKeyDown())
						fontRenderer.drawString(energy, (res.getScaledWidth() / 2 + 182 / 2 + 2) * 1.33f, (res.getScaledHeight() - 28) * 1.33f, Color.white.getRGB(), true);

					GlStateManager.popMatrix();
				}
			}

			if (Util.minecraft().objectMouseOver != null && Util.minecraft().objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK))
			{
				World world = Util.world();
				BlockPos pos = Util.minecraft().objectMouseOver.getBlockPos();

				TileEntity tile = world.getTileEntity(pos);

				if (tile != null && tile instanceof IEnergyContainer)
				{
					IEnergyContainer container = (IEnergyContainer) tile;

					String energy = NumberFormat.getInstance().format(container.getEnergy()) + "/" + NumberFormat.getInstance().format(container.getMaxEnergy());

					fontRenderer.drawStringWithShadow(tile.getBlockType().getLocalizedName(), res.getScaledWidth() / 2 + 8, res.getScaledHeight() / 2, Color.white.getRGB());
					fontRenderer.drawStringWithShadow(ChatFormatting.GRAY + energy, (res.getScaledWidth() / 2) + 8, res.getScaledHeight() / 2 + 10, Color.white.hashCode());

					if (tile instanceof IEnergyGenerator)
					{
						IEnergyGenerator generator = (IEnergyGenerator) tile;

						String generating = String.format("Generating %sTF/t", ChatFormatting.GREEN + NumberFormat.getInstance().format(generator.getGenerating()) + ChatFormatting.GRAY);

						fontRenderer.drawStringWithShadow(ChatFormatting.GRAY + generating, (res.getScaledWidth() / 2) + 8, res.getScaledHeight() / 2 + 20, Color.white.getRGB());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void renderDebugEvent(RenderGameOverlayEvent.Text event)
	{
		if (!Util.minecraft().gameSettings.showDebugInfo) return;

		event.getLeft().add(5, "Effect Count: " + EffectManager.getRenderObjects().size());
	}
}

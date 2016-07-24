package DarkS.TechXProject.machines.fluidTank;

import DarkS.TechXProject.util.Renderer;
import DarkS.TechXProject.util.Util;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import java.awt.*;

public class TileFluidTankRender extends TileEntitySpecialRenderer<TileFluidTank>
{
	@Override
	public void renderTileEntityAt(TileFluidTank te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (te.tank.getFluid() == null) return;

		float level = 0.1875f + (float) te.tank.getFluidAmount() / te.tank.getCapacity() * 0.624f;

		TextureAtlasSprite sprite = Util.minecraft().getTextureMapBlocks().registerSprite(te.tank.getFluid().getFluid().getStill());
		Util.minecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		float uMin = sprite.getMinU();
		float uMax = sprite.getMaxU();
		float vMin = sprite.getMinV();
		float vMax = sprite.getMaxV();

		float offset = 0.18755f;

		GlStateManager.pushMatrix();

		GlStateManager.disableLighting();

		GlStateManager.translate(x, y, z);

		Color color = new Color(te.tank.getFluid().getFluid().getColor());

		GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);

		Util.GL.startOpaqueRendering();

		Renderer.POS_UV.beginQuads();

		Renderer.POS_UV.addVertex(offset, offset, offset, uMin, vMin);
		Renderer.POS_UV.addVertex(1 - offset, offset, offset, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offset, offset, 1 - offset, uMax, vMax);
		Renderer.POS_UV.addVertex(offset, offset, 1 - offset, uMin, vMax);

		Renderer.POS_UV.addVertex(offset, level, 1 - offset, uMin, vMin);
		Renderer.POS_UV.addVertex(1 - offset, level, 1 - offset, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offset, level, offset, uMax, vMax);
		Renderer.POS_UV.addVertex(offset, level, offset, uMin, vMax);

		Renderer.POS_UV.addVertex(offset, offset, 1 - offset, uMin, vMin);
		Renderer.POS_UV.addVertex(1 - offset, offset, 1 - offset, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offset, level, 1 - offset, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(offset, level, 1 - offset, uMin, vMin + (vMax - vMin) * (16 * level) / 16);

		Renderer.POS_UV.addVertex(offset, level, offset, uMin, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offset, level, offset, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offset, offset, offset, uMax, vMin);
		Renderer.POS_UV.addVertex(offset, offset, offset, uMin, vMin);

		Renderer.POS_UV.addVertex(1 - offset, level, offset, uMin, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offset, level, 1 - offset, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offset, offset, 1 - offset, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offset, offset, offset, uMin, vMin);

		Renderer.POS_UV.addVertex(offset, offset, offset, uMin, vMin);
		Renderer.POS_UV.addVertex(offset, offset, 1 - offset, uMax, vMin);
		Renderer.POS_UV.addVertex(offset, level, 1 - offset, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(offset, level, offset, uMin, vMin + (vMax - vMin) * (16 * level) / 16);

		Renderer.POS_UV.draw();

		Util.GL.endOpaqueRendering();

		GlStateManager.enableLighting();

		GlStateManager.popMatrix();
	}
}

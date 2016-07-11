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

		float level = 0.125f + (float) te.tank.getFluidAmount() / te.tank.getCapacity() * 0.75f;

		TextureAtlasSprite sprite = Util.minecraft().getTextureMapBlocks().registerSprite(te.tank.getFluid().getFluid().getStill());
		Util.minecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		float uMin = sprite.getMinU();
		float uMax = sprite.getMaxU();
		float vMin = sprite.getMinV();
		float vMax = sprite.getMaxV();

		float offsetSide = 0.18754f, offsetTop = 0.125f;

		GlStateManager.pushMatrix();

		GlStateManager.disableLighting();

		GlStateManager.translate(x, y, z);

		Color color = new Color(te.tank.getFluid().getFluid().getColor());

		GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);

		Util.GL.startOpaqueRendering();

		Renderer.POS_UV.beginQuads();

		Renderer.POS_UV.addVertex(offsetSide, offsetTop, offsetSide, uMin, vMin);
		Renderer.POS_UV.addVertex(1 - offsetSide, offsetTop, offsetSide, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offsetSide, offsetTop, 1 - offsetSide, uMax, vMax);
		Renderer.POS_UV.addVertex(offsetSide, offsetTop, 1 - offsetSide, uMin, vMax);

		Renderer.POS_UV.addVertex(offsetSide, level, 1 - offsetSide, uMin, vMin);
		Renderer.POS_UV.addVertex(1 - offsetSide, level, 1 - offsetSide, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offsetSide, level, offsetSide, uMax, vMax);
		Renderer.POS_UV.addVertex(offsetSide, level, offsetSide, uMin, vMax);

		Renderer.POS_UV.addVertex(offsetSide, offsetTop, 1 - offsetSide, uMin, vMin);
		Renderer.POS_UV.addVertex(1 - offsetSide, offsetTop, 1 - offsetSide, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offsetSide, level, 1 - offsetSide, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(offsetSide, level, 1 - offsetSide, uMin, vMin + (vMax - vMin) * (16 * level) / 16);

		Renderer.POS_UV.addVertex(offsetSide, level, offsetSide, uMin, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offsetSide, level, offsetSide, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offsetSide, offsetTop, offsetSide, uMax, vMin);
		Renderer.POS_UV.addVertex(offsetSide, offsetTop, offsetSide, uMin, vMin);

		Renderer.POS_UV.addVertex(1 - offsetSide, level, offsetSide, uMin, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offsetSide, level, 1 - offsetSide, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(1 - offsetSide, offsetTop, 1 - offsetSide, uMax, vMin);
		Renderer.POS_UV.addVertex(1 - offsetSide, offsetTop, offsetSide, uMin, vMin);

		Renderer.POS_UV.addVertex(offsetSide, offsetTop, offsetSide, uMin, vMin);
		Renderer.POS_UV.addVertex(offsetSide, offsetTop, 1 - offsetSide, uMax, vMin);
		Renderer.POS_UV.addVertex(offsetSide, level, 1 - offsetSide, uMax, vMin + (vMax - vMin) * (16 * level) / 16);
		Renderer.POS_UV.addVertex(offsetSide, level, offsetSide, uMin, vMin + (vMax - vMin) * (16 * level) / 16);

		Renderer.POS_UV.draw();

		GlStateManager.enableLighting();

		Util.GL.endOpaqueRendering();

		GlStateManager.popMatrix();
	}
}

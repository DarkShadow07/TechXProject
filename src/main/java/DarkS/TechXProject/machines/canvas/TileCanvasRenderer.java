package DarkS.TechXProject.machines.canvas;

import DarkS.TechXProject.util.Renderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import java.awt.*;

public class TileCanvasRenderer extends TileEntitySpecialRenderer<TileCanvas>
{
	float pixel = 1f / 16f;

	@Override
	public void renderTileEntityAt(TileCanvas te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);

		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();

		drawCanvas(te);

		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();

		GlStateManager.popMatrix();
	}

	private void drawCanvas(TileCanvas te)
	{
		Renderer.POS_COLOR.beginQuads();

		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
			{
				Color color = te.getColor(i, 16, j);

				if (color != null)
				{
					Renderer.POS_COLOR.addVertex(i * pixel, 1.0, (j + 1) * pixel, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, 1.0, (j + 1) * pixel, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, 1.0, j * pixel, color);
					Renderer.POS_COLOR.addVertex(i * pixel, 1.0, j * pixel, color);
				}

				color = te.getColor(i, 0, j);

				if (color != null)
				{
					Renderer.POS_COLOR.addVertex(i * pixel, 0, j * pixel, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, 0, j * pixel, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, 0, (j + 1) * pixel, color);
					Renderer.POS_COLOR.addVertex(i * pixel, 0, (j + 1) * pixel, color);
				}

				color = te.getColor(i, j, 0);

				if (color != null)
				{
					Renderer.POS_COLOR.addVertex(i * pixel, (j + 1) * pixel, 0, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, (j + 1) * pixel, 0, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, j * pixel, 0, color);
					Renderer.POS_COLOR.addVertex(i * pixel, j * pixel, 0, color);
				}

				color = te.getColor(i, j, 16);

				if (color != null)
				{
					Renderer.POS_COLOR.addVertex(i * pixel, j * pixel, 1, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, j * pixel, 1, color);
					Renderer.POS_COLOR.addVertex((i + 1) * pixel, (j + 1) * pixel, 1, color);
					Renderer.POS_COLOR.addVertex(i * pixel, (j + 1) * pixel, 1, color);
				}

				color = te.getColor(0, j, i);

				if (color != null)
				{
					Renderer.POS_COLOR.addVertex(0, j * pixel, i * pixel, color);
					Renderer.POS_COLOR.addVertex(0, j * pixel, (i + 1) * pixel, color);
					Renderer.POS_COLOR.addVertex(0, (j + 1) * pixel, (i + 1) * pixel, color);
					Renderer.POS_COLOR.addVertex(0, (j + 1) * pixel, i * pixel, color);
				}

				color = te.getColor(16, j, i);

				if (color != null)
				{
					Renderer.POS_COLOR.addVertex(1, (j + 1) * pixel, i * pixel, color);
					Renderer.POS_COLOR.addVertex(1, (j + 1) * pixel, (i + 1) * pixel, color);
					Renderer.POS_COLOR.addVertex(1, j * pixel, (i + 1) * pixel, color);
					Renderer.POS_COLOR.addVertex(1, j * pixel, i * pixel, color);
				}
			}

		Renderer.POS_COLOR.draw();
	}
}

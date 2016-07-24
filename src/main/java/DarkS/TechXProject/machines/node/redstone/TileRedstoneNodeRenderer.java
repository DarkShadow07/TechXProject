package DarkS.TechXProject.machines.node.redstone;

import DarkS.TechXProject.util.Renderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import java.awt.*;

public class TileRedstoneNodeRenderer extends TileEntitySpecialRenderer<TileRedstoneNode>
{
	float pixel = 1f / 16f;

	@Override
	public void renderTileEntityAt(TileRedstoneNode te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		//TODO: Rotation!

		Color[] colors = new Color[3];

		colors[0] = new Color(te.getChannel()[0]);
		colors[1] = new Color(te.getChannel()[1]);
		colors[2] = new Color(te.getChannel()[2]);

		GlStateManager.pushMatrix();

		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();

		GlStateManager.translate(x, y, z);

		Renderer.POS_COLOR.beginQuads();

		for (int i = 0; i < 3; i++)
		{
			Renderer.POS_COLOR.addVertex(3 * pixel, 2.5 * pixel, (4 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2.5 * pixel, (4 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2.5 * pixel, (6 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(3 * pixel, 2.5 * pixel, (6 + 3 * i) * pixel, colors[i]);

			Renderer.POS_COLOR.addVertex(2 * pixel, 2.5 * pixel, (4 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2 * pixel, (4 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2 * pixel, (6 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2.5 * pixel, (6 + 3 * i) * pixel, colors[i]);

			Renderer.POS_COLOR.addVertex(3 * pixel, 2.5 * pixel, (4 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(3 * pixel, 2 * pixel, (4 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2 * pixel, (4 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2.5 * pixel, (4 + 3 * i) * pixel, colors[i]);

			Renderer.POS_COLOR.addVertex(2 * pixel, 2.5 * pixel, (6 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(2 * pixel, 2 * pixel, (6 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(3 * pixel, 2 * pixel, (6 + 3 * i) * pixel, colors[i]);
			Renderer.POS_COLOR.addVertex(3 * pixel, 2.5 * pixel, (6 + 3 * i) * pixel, colors[i]);
		}

		Renderer.POS_COLOR.draw();

		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();

		GlStateManager.popMatrix();
	}
}

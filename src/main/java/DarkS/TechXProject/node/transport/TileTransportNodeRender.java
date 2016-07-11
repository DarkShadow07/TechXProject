package DarkS.TechXProject.node.transport;

import DarkS.TechXProject.util.Util;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;

import java.awt.*;

public class TileTransportNodeRender extends TileEntitySpecialRenderer<TileTransportNode>
{
	@Override
	public void renderTileEntityAt(TileTransportNode te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (distanteToPlayer(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), Util.player()) > 16) return;

		String name = te.getName();
		Color color = te.getColor();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5, (float) y + 0.65F + Util.fluctuateSmooth(100, 0) / 12, (float) z + 0.5);
		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-Util.minecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) (Util.minecraft().getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * Util.minecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-0.025F, -0.025F, 0.025F);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		FontRenderer fontrenderer = Util.minecraft().fontRendererObj;

		int j = fontrenderer.getStringWidth(name) / 2;
		GlStateManager.disableTexture2D();
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vertexbuffer.pos((double) (-j - 1), (double) (-1), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		vertexbuffer.pos((double) (-j - 1), (double) (8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		vertexbuffer.pos((double) (j + 1), (double) (8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		vertexbuffer.pos((double) (j + 1), (double) (-1), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();

		GlStateManager.depthMask(true);
		fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, color.getRGB());
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	private double distanteToPlayer(double x, double y, double z, Entity entity)
	{
		double dX = x - entity.posX;
		double dY = y - entity.posY;
		double dZ = z - entity.posZ;

		dX = dX * dX;
		dY = dY * dY;
		dZ = dZ * dZ;

		return Math.sqrt(dX + dY + dZ);
	}
}

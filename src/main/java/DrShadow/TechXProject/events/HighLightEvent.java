package DrShadow.TechXProject.events;

import DrShadow.TechXProject.blocks.multiHighlight.IMultiHighlightProvider;
import DrShadow.TechXProject.util.VectorUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class HighlightEvent
{
	Tessellator tess = Tessellator.getInstance();

	@SubscribeEvent
	public void drawHighLight(DrawBlockHighlightEvent event)
	{
		EntityPlayer player = event.player;
		World world = player.worldObj;

		if (event.target != null && event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			TileEntity tile = world.getTileEntity(event.target.getBlockPos());

			if (tile != null && tile instanceof IMultiHighlightProvider)
			{
				event.setCanceled(true);

				Vec3 pos = VectorUtil.multiply(player.getPositionVector(), -1f).add(new Vec3(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ()));

				GlStateManager.pushMatrix();

				GlStateManager.translate(pos.xCoord, pos.yCoord, pos.zCoord);

				IMultiHighlightProvider multiProvider = (IMultiHighlightProvider) tile;

				AxisAlignedBB[] boxes = multiProvider.getBoxes().toArray(new AxisAlignedBB[0]);

				for (AxisAlignedBB box : boxes)
				{
					drawBox(box.minX, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, 0, 0, 0, 0.25f);
				}

				GlStateManager.popMatrix();
			}
		}
	}

	public void drawBox(double minX, double maxX, double minY, double maxY, double minZ, double maxZ, float rColor, float gColor, float bColor, float alpha)
	{
		float[] xPoints = new float[8], yPoints = new float[8], zPoints = new float[8];

		xPoints[0] = (float) minX;
		xPoints[1] = (float) minX;
		yPoints[0] = (float) minY;
		yPoints[1] = (float) maxY;
		zPoints[0] = (float) minZ;
		zPoints[1] = (float) minZ;

		xPoints[2] = (float) maxX;
		xPoints[3] = (float) maxX;
		yPoints[2] = (float) minY;
		yPoints[3] = (float) maxY;
		zPoints[2] = (float) minZ;
		zPoints[3] = (float) minZ;

		xPoints[4] = (float) maxX;
		xPoints[5] = (float) maxX;
		yPoints[4] = (float) minY;
		yPoints[5] = (float) maxY;
		zPoints[4] = (float) maxZ;
		zPoints[5] = (float) maxZ;

		xPoints[6] = (float) minX;
		xPoints[7] = (float) minX;
		yPoints[6] = (float) minY;
		yPoints[7] = (float) maxY;
		zPoints[6] = (float) maxZ;
		zPoints[7] = (float) maxZ;

		drawRawBox(xPoints, yPoints, zPoints, rColor, gColor, bColor, alpha);
	}

	public void drawRawBox(float[] xPoints, float[] yPoints, float[] zPoints, float rColor, float gColor, float bColor, float alpha)
	{
		GlStateManager.disableTexture2D();
		GlStateManager.color(rColor, gColor, bColor, alpha);

		float pixel = 1f / 16f;

		GL11.glLineWidth(2 * pixel);
		WorldRenderer renderer = tess.getWorldRenderer();

		renderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);

		renderer.pos(xPoints[0], yPoints[0], zPoints[0]).endVertex();
		renderer.pos(xPoints[1], yPoints[1], zPoints[1]).endVertex();

		renderer.pos(xPoints[2], yPoints[2], zPoints[2]).endVertex();
		renderer.pos(xPoints[3], yPoints[3], zPoints[3]).endVertex();

		renderer.pos(xPoints[4], yPoints[4], zPoints[4]).endVertex();
		renderer.pos(xPoints[5], yPoints[5], zPoints[5]).endVertex();

		renderer.pos(xPoints[6], yPoints[6], zPoints[6]).endVertex();
		renderer.pos(xPoints[7], yPoints[7], zPoints[7]).endVertex();

		renderer.pos(xPoints[0], yPoints[0], zPoints[0]).endVertex();
		renderer.pos(xPoints[6], yPoints[6], zPoints[6]).endVertex();

		renderer.pos(xPoints[1], yPoints[1], zPoints[1]).endVertex();
		renderer.pos(xPoints[7], yPoints[7], zPoints[7]).endVertex();

		renderer.pos(xPoints[0], yPoints[0], zPoints[0]).endVertex();
		renderer.pos(xPoints[2], yPoints[2], zPoints[2]).endVertex();

		renderer.pos(xPoints[1], yPoints[1], zPoints[1]).endVertex();
		renderer.pos(xPoints[3], yPoints[3], zPoints[3]).endVertex();

		renderer.pos(xPoints[2], yPoints[2], zPoints[2]).endVertex();
		renderer.pos(xPoints[4], yPoints[4], zPoints[4]).endVertex();

		renderer.pos(xPoints[3], yPoints[3], zPoints[3]).endVertex();
		renderer.pos(xPoints[5], yPoints[5], zPoints[5]).endVertex();

		renderer.pos(xPoints[4], yPoints[4], zPoints[4]).endVertex();
		renderer.pos(xPoints[6], yPoints[6], zPoints[6]).endVertex();

		renderer.pos(xPoints[5], yPoints[5], zPoints[5]).endVertex();
		renderer.pos(xPoints[7], yPoints[7], zPoints[7]).endVertex();

		tess.draw();

		GlStateManager.enableTexture2D();
	}
}

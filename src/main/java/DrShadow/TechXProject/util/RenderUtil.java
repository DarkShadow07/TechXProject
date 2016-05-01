package DrShadow.TechXProject.util;

import DrShadow.TechXProject.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtil
{
	public static void drawFakeBlock(ResourceLocation texture, double minX, double minY, double minZ)
	{
		if (texture == null)
			return;

		double maxX = minX + 1;
		double maxY = minY + 1;
		double maxZ = minZ + 1;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer wr = tessellator.getBuffer();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		float texMinU = 0;
		float texMinV = 0;
		float texMaxU = 16;
		float texMaxV = 16;

		wr.pos(minX, minY, minZ).tex(texMinU, texMinV).endVertex();
		wr.pos(maxX, minY, minZ).tex(texMaxU, texMinV).endVertex();
		wr.pos(maxX, minY, maxZ).tex(texMaxU, texMaxV).endVertex();
		wr.pos(minX, minY, maxZ).tex(texMinU, texMaxV).endVertex();

		wr.pos(minX, maxY, maxZ).tex(texMinU, texMaxV).endVertex();
		wr.pos(maxX, maxY, maxZ).tex(texMaxU, texMaxV).endVertex();
		wr.pos(maxX, maxY, minZ).tex(texMaxU, texMinV).endVertex();
		wr.pos(minX, maxY, minZ).tex(texMinU, texMinV).endVertex();

		wr.pos(maxX, minY, minZ).tex(texMinU, texMaxV).endVertex();
		wr.pos(minX, minY, minZ).tex(texMaxU, texMaxV).endVertex();
		wr.pos(minX, maxY, minZ).tex(texMaxU, texMinV).endVertex();
		wr.pos(maxX, maxY, minZ).tex(texMinU, texMinV).endVertex();

		wr.pos(minX, minY, maxZ).tex(texMinU, texMaxV).endVertex();
		wr.pos(maxX, minY, maxZ).tex(texMaxU, texMaxV).endVertex();
		wr.pos(maxX, maxY, maxZ).tex(texMaxU, texMinV).endVertex();
		wr.pos(minX, maxY, maxZ).tex(texMinU, texMinV).endVertex();

		wr.pos(minX, minY, minZ).tex(texMinU, texMaxV).endVertex();
		wr.pos(minX, minY, maxZ).tex(texMaxU, texMaxV).endVertex();
		wr.pos(minX, maxY, maxZ).tex(texMaxU, texMinV).endVertex();
		wr.pos(minX, maxY, minZ).tex(texMinU, texMinV).endVertex();

		wr.pos(maxX, minY, maxZ).tex(texMinU, texMaxV).endVertex();
		wr.pos(maxX, minY, minZ).tex(texMaxU, texMaxV).endVertex();
		wr.pos(maxX, maxY, minZ).tex(texMaxU, texMinV).endVertex();
		wr.pos(maxX, maxY, maxZ).tex(texMinU, texMinV).endVertex();

		tessellator.draw();
	}

	public static TextureAtlasSprite fromName(TextureMap textureMap, String name, String dir)
	{
		return textureMap.registerSprite(new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + dir + "/" + name));
	}
}

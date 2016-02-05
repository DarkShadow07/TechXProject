package com.DrShadow.TechXProject.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

public class Renderer
{
	private static final Renderer instance = new Renderer();

	public static final PosRenderer POS = new PosRenderer();
	public static final PosUVNormalRenderer POS_UV_NORMAL = new PosUVNormalRenderer();
	public static final PosUVRenderer POS_UV = new PosUVRenderer();
	public static final PosUVColorRenderer POS_UV_COLOR = new PosUVColorRenderer();
	public static final PosColorRenderer POS_COLOR = new PosColorRenderer();
	public static final LineRenderer LINES = new LineRenderer();
	public static final PosUVColorNormalRenderer POS_UV_COLOR_NORMAL = new PosUVColorNormalRenderer();


	private static WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
	private static Tessellator tessellator = Tessellator.getInstance();

	private Renderer() {}


	private void addVertexData(double xPos, double yPos, double zPos, double u, double v, float xNormal, float yNormal, float zNormal)
	{
		addPos(xPos, yPos, zPos).addUV(u, v).addNormal(xNormal, yNormal, zNormal).endVertex();
	}

	private Renderer addVertexData(double xPos, double yPos, double zPos, double u, double v)
	{
		addPos(xPos, yPos, zPos).addUV(u, v);
		return instance;
	}


	private Renderer addNormal(float x, float y, float z)
	{
		renderer.normal(x, y, z);
		return instance;
	}

	private Renderer addUV(double u, double v)
	{
		renderer.tex(u, v);
		return instance;
	}

	private Renderer addPos(double xPos, double yPos, double zPos)
	{
		renderer.pos(xPos, yPos, zPos);
		return instance;
	}

	private Renderer addColor(float r, float g, float b, float a)
	{
		renderer.color(Helper.snap(r, 0, 1), Helper.snap(g, 0, 1), Helper.snap(b, 0, 1), Helper.snap(a, 0, 1));
		return instance;
	}

	private Renderer addColor(float r, float g, float b)
	{
		addColor(r, g, b, 1F);
		return instance;
	}

	private void endVertex()
	{
		renderer.endVertex();
	}

	private Renderer lightmap(int j, int k)
	{
		renderer.lightmap(j, k);
		return instance;
	}

	public static abstract class RendererBase
	{
		private RendererBase() {}

		public static Renderer setTranslation(double x, double y, double z)
		{
			renderer.setTranslation(x, y, z);
			return instance;
		}


		public void begin(int type)
		{
			renderer.begin(type, getVertexFormat());
		}

		public abstract VertexFormat getVertexFormat();

		public void beginQuads()
		{
			renderer.begin(7, getVertexFormat());
		}

		public final void draw()
		{
			tessellator.draw();
		}

	}

	public static class PosRenderer extends RendererBase
	{

		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION;
		}

		public void addVertex(Vec3M pos)
		{
			instance.addPos(pos.x, pos.y, pos.z);
		}

		public void addVertex(double xPos, double yPos, double zPos)
		{
			instance.addPos(xPos, yPos, zPos).endVertex();
		}
	}

	public static class LineRenderer extends RendererBase
	{

		@Deprecated
		public void beginQuads()
		{
			begin();
		}

		public void begin()
		{
			begin(GL11.GL_LINES);
		}

		@Override
		@Deprecated
		public void begin(int type)
		{
			super.begin(type);
		}

		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION;
		}

		public void addVertex(Vec3M pos)
		{
			instance.addPos(pos.x, pos.y, pos.z);
		}

		public void addVertex(double xPos, double yPos, double zPos)
		{
			instance.addPos(xPos, yPos, zPos).endVertex();
		}
	}


	public static class PosUVNormalRenderer extends RendererBase
	{

		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_NORMAL;
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, Vec3M normal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v, (float) normal.x, (float) normal.y, (float) normal.z);
		}

		public void addVertex(Vec3M pos, float u, float v, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v, xNormal, yNormal, zNormal);
		}

		public void addVertex(Vec3M pos, float u, float v, Vec3M normal)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v, (float) normal.x, (float) normal.y, (float) normal.z);
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v, xNormal, yNormal, zNormal);
		}
	}

	public static class PosUVColorNormalRenderer extends RendererBase
	{

		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL;
		}

		public void addVertex(Vec3M pos, float u, float v, float r, float g, float b, float a, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v).addColor(r, g, b, a).addNormal(xNormal, yNormal, zNormal).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, float r, float g, float b, float a, Vec3M normal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(r, g, b, a).addNormal((float) normal.x, (float) normal.y, (float) normal.z).endVertex();
		}

		public void addVertex(Vec3M pos, float u, float v, float r, float g, float b, float a, Vec3M normal)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v).addColor(r, g, b, a).addNormal((float) normal.x, (float) normal.y, (float) normal.z).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, ColorUtil color, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(color.r, color.g, color.b, color.a).addNormal(xNormal, yNormal, zNormal).endVertex();
		}

		public void addVertex(Vec3M pos, float u, float v, ColorUtil color, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v).addColor(color.r, color.g, color.b, color.a).addNormal(xNormal, yNormal, zNormal).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, ColorUtil color, Vec3M normal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(color.r, color.g, color.b, color.a).addNormal((float) normal.x, (float) normal.y, (float) normal.z).endVertex();
		}

		public void addVertex(Vec3M pos, float u, float v, ColorUtil color, Vec3M normal)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v).addColor(color.r, color.g, color.b, color.a).addNormal((float) normal.x, (float) normal.y, (float) normal.z).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, float r, float g, float b, float a, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(r, g, b, a).addNormal(xNormal, yNormal, zNormal).endVertex();
		}
	}

	public static class PosUVRenderer extends RendererBase
	{

		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_NORMAL;
		}

		public void addVertex(Vec3M pos, float u, float v)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).endVertex();
		}
	}

	public static class PosUVColorRenderer extends RendererBase
	{

		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_COLOR;
		}

		public void addVertex(Vec3M pos, float u, float v, ColorUtil color)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v).addColor(color.r, color.g, color.b, color.a).endVertex();
		}

		public void addVertex(Vec3M pos, float u, float v, float r, float g, float b, float a)
		{
			instance.addVertexData(pos.x, pos.y, pos.z, u, v).addColor(r, g, b, a).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, ColorUtil color)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(color.r, color.g, color.b, color.a).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, float r, float g, float b, float a)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(r, g, b, a).endVertex();
		}
	}

	public static class PosColorRenderer extends RendererBase
	{

		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_COLOR;
		}

		public void addVertex(Vec3M pos, ColorUtil color)
		{
			instance.addPos(pos.x, pos.y, pos.z).addColor(color.r, color.g, color.b, color.a).endVertex();
		}

		public void addVertex(Vec3M pos, float r, float g, float b, float a)
		{
			instance.addPos(pos.x, pos.y, pos.z).addColor(r, g, b, a).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, ColorUtil color)
		{
			instance.addPos(xPos, yPos, zPos).addColor(color.r, color.g, color.b, color.a).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float r, float g, float b, float a)
		{
			instance.addPos(xPos, yPos, zPos).addColor(r, g, b, a).endVertex();
		}
	}
}

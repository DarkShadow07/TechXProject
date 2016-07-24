package DarkS.TechXProject.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Renderer
{
	public static final LineRenderer LINES = new LineRenderer();
	public static final PosRenderer POS = new PosRenderer();
	public static final PosColorRenderer POS_COLOR = new PosColorRenderer();
	public static final PosUVRenderer POS_UV = new PosUVRenderer();
	public static final PosUVColorRenderer POS_UV_COLOR = new PosUVColorRenderer();
	public static final PosUVColorNormalRenderer POS_UV_COLOR_NORMAL = new PosUVColorNormalRenderer();
	public static final PosUVNormalRenderer POS_UV_NORMAL = new PosUVNormalRenderer();
	private static final Renderer instance = new Renderer();
	private static VertexBuffer renderer = Tessellator.getInstance().getBuffer();
	private static Tessellator tessellator = Tessellator.getInstance();

	private Renderer()
	{

	}

	private Renderer addColor(float r, float g, float b, float a)
	{
		renderer.color(MathHelper.clamp_float(r, 0, 1), MathHelper.clamp_float(g, 0, 1), MathHelper.clamp_float(b, 0, 1), MathHelper.clamp_float(a, 0, 1));
		return instance;
	}

	private Renderer addNormal(float x, float y, float z)
	{
		renderer.normal(x, y, z);
		return instance;
	}

	private Renderer addPos(double xPos, double yPos, double zPos)
	{
		renderer.pos(xPos, yPos, zPos);
		return instance;
	}

	private Renderer addUV(double u, double v)
	{
		renderer.tex(u, v);
		return instance;
	}

	private Renderer addVertexData(double xPos, double yPos, double zPos, double u, double v)
	{
		addPos(xPos, yPos, zPos).addUV(u, v);
		return instance;
	}

	private void addVertexData(double xPos, double yPos, double zPos, double u, double v, float xNormal, float yNormal, float zNormal)
	{
		addPos(xPos, yPos, zPos).addUV(u, v).addNormal(xNormal, yNormal, zNormal).endVertex();
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

	public static class LineRenderer extends RendererBase
	{
		public void addVertex(double xPos, double yPos, double zPos)
		{
			instance.addPos(xPos, yPos, zPos).endVertex();
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

		@Override
		@Deprecated
		public void beginQuads()
		{
			begin();
		}

		@Override
		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION;
		}
	}

	public static class PosColorRenderer extends RendererBase
	{
		public void addVertex(double xPos, double yPos, double zPos, Color color)
		{
			instance.addPos(xPos, yPos, zPos).addColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float r, float g, float b, float a)
		{
			instance.addPos(xPos, yPos, zPos).addColor(r, g, b, a).endVertex();
		}

		@Override
		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_COLOR;
		}
	}

	public static class PosRenderer extends RendererBase
	{
		public void addVertex(double xPos, double yPos, double zPos)
		{
			instance.addPos(xPos, yPos, zPos).endVertex();
		}

		@Override
		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION;
		}
	}

	public static class PosUVColorNormalRenderer extends RendererBase
	{
		public void addVertex(double xPos, double yPos, double zPos, float u, float v, Color color, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).addNormal(xNormal, yNormal, zNormal).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, float r, float g, float b, float a, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(r, g, b, a).addNormal(xNormal, yNormal, zNormal).endVertex();
		}

		@Override
		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL;
		}
	}

	public static class PosUVColorRenderer extends RendererBase
	{
		public void addVertex(double xPos, double yPos, double zPos, float u, float v, Color color)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
		}

		public void addVertex(double xPos, double yPos, double zPos, float u, float v, float r, float g, float b, float a)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).addColor(r, g, b, a).endVertex();
		}

		@Override
		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_COLOR;
		}
	}

	public static class PosUVNormalRenderer extends RendererBase
	{
		public void addVertex(double xPos, double yPos, double zPos, float u, float v, float xNormal, float yNormal, float zNormal)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v, xNormal, yNormal, zNormal);
		}

		@Override
		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX_NORMAL;
		}
	}

	public static class PosUVRenderer extends RendererBase
	{
		public void addVertex(double xPos, double yPos, double zPos, float u, float v)
		{
			instance.addVertexData(xPos, yPos, zPos, u, v).endVertex();
		}

		@Override
		public VertexFormat getVertexFormat()
		{
			return DefaultVertexFormats.POSITION_TEX;
		}
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

		public void beginQuads()
		{
			renderer.begin(7, getVertexFormat());
		}

		public final void draw()
		{
			tessellator.draw();
		}

		public abstract VertexFormat getVertexFormat();

	}
}

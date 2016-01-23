package com.DrShadow.TechXProject.render;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Renderer
{
	private static WorldRenderer renderer;

	private static Tessellator tessellator = Tessellator.getInstance();
	private static Renderer instance = new Renderer();

	static
	{
		renderer = tessellator.getWorldRenderer();
	}

	private Renderer()
	{

	}

	public static void draw()
	{
		tessellator.draw();
	}

	public static void begin(int type)
	{
		renderer.begin(type, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
	}

	public static void beginLines()
	{
		renderer.begin(GL11.GL_LINES, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
	}

	public static void beginQuads()
	{
		renderer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
	}

	public static void addVertexWithData(PositionTextureVertex vertex, float xNormal, float yNormal, float zNormal)
	{
		addVertexData(vertex.vector3D, vertex.texturePositionX, vertex.texturePositionY, xNormal, yNormal, zNormal);
	}

	public static void addVertexData(Vec3 pos, double u, double v, float xNormal, float yNormal, float zNormal)
	{
		addVertexData(pos.xCoord, pos.yCoord, pos.zCoord, u, v, xNormal, yNormal, zNormal);
	}

	public static void addVertexData(double xPos, double yPos, double zPos, double u, double v, float xNormal, float yNormal, float zNormal)
	{
		addPos(xPos, yPos, zPos).addUV(u, v).addNormal(xNormal, yNormal, zNormal).endVertex();
	}

	public static Renderer addVertexData(double x, double y, double z, double u, double v)
	{
		addPos(x, y, z).addUV(u, v);
		return instance;
	}


	public static Renderer addNormal(float x, float y, float z)
	{
		renderer.normal(x, y, z);
		return instance;
	}


	public static Renderer addUV(double u, double v)
	{
		renderer.tex(u, v);
		return instance;
	}

	public static Renderer addPos(double x, double y, double z)
	{
		renderer.pos(x, y, z);
		return instance;
	}


	public static void setTranslation(double x, double y, double z)
	{
		renderer.setTranslation(x, y, z);
	}

	public static void setColor(float r, float g, float b, float a)
	{
		renderer.color(r, g, b, a);
	}

	public static void setColor(float r, float g, float b)
	{
		setColor(r, g, b, 1);
	}

	public static void endVertex()
	{
		renderer.endVertex();
	}

}
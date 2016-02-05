package com.DrShadow.TechXProject.util;

import java.awt.*;

public class ColorUtil
{
	public static final ColorUtil
			BLACK = ColorUtil.convert(Color.BLACK),
			BLUE = ColorUtil.convert(Color.BLUE),
			CYAN = ColorUtil.convert(Color.CYAN),
			DARK_GRAY = ColorUtil.convert(Color.DARK_GRAY),
			GRAY = ColorUtil.convert(Color.GRAY),
			GREEN = ColorUtil.convert(Color.GREEN),
			LIGHT_GRAY = ColorUtil.convert(Color.LIGHT_GRAY),
			MAGENTA = ColorUtil.convert(Color.MAGENTA),
			ORANGE = ColorUtil.convert(Color.ORANGE),
			PINK = ColorUtil.convert(Color.PINK),
			RED = ColorUtil.convert(Color.RED),
			WHITE = ColorUtil.convert(Color.WHITE),
			YELLOW = ColorUtil.convert(Color.YELLOW);
	public float r, g, b, a;

	public ColorUtil(double r, double g, double b, double a)
	{
		this.r = (float) Helper.snap(r, 0, 1);
		this.g = (float) Helper.snap(g, 0, 1);
		this.b = (float) Helper.snap(b, 0, 1);
		this.a = (float) Helper.snap(a, 0, 1);
	}

	public ColorUtil()
	{
		this(1, 1, 1, 1);
	}

	public ColorUtil blackNWhite()
	{
		float sum = (r + g + b) / 3F;
		return new ColorUtil(sum, sum, sum, a);
	}

	public ColorUtil negative()
	{
		return new ColorUtil(1 - r, 1 - g, 1 - b, 1 - a);
	}

	public ColorUtil disablBlend()
	{
		return new ColorUtil(r, g, b, 1);
	}

	public void bind()
	{
		OpenGLM.color(r, g, b, a);
	}

	public int toCode()
	{
		return new Color(r, g, b, a).hashCode();
	}

	public ColorUtil mix(Color color)
	{
		return mix(convert(color));
	}

	public ColorUtil mix(ColorUtil color)
	{
		return new ColorUtil((r + color.r) / 2F, (g + color.g) / 2F, (b + color.b) / 2F, (a + color.a) / 2F);
	}

	public ColorUtil mix(Color color, float scale1, float scale2)
	{
		return mix(convert(color), scale1, scale2);
	}

	public ColorUtil mix(ColorUtil color, float scale1, float scale2)
	{
		return new ColorUtil(
				(r * scale1 + color.r * scale2) / (scale1 + scale2),
				(g * scale1 + color.g * scale2) / (scale1 + scale2),
				(b * scale1 + color.b * scale2) / (scale1 + scale2),
				(a * scale1 + color.a * scale2) / (scale1 + scale2)
		);
	}

	public ColorUtil set(float modifier, char c)
	{
		modifier = Helper.snap(modifier, 0, 1);
		return new ColorUtil(c == 'r' ? modifier : r, c == 'g' ? modifier : g, c == 'b' ? modifier : b, c == 'a' ? modifier : a);
	}

	public ColorUtil copy()
	{
		return new ColorUtil(r, g, b, a);
	}

	@Override
	public String toString()
	{
		return "(" + (r + "").substring(0, Math.min((r + "").length(), 4)) + ", " + (g + "").substring(0, Math.min((g + "").length(), 4)) + ", " + (b + "").substring(0, Math.min((b + "").length(), 4)) + ", " + (a + "").substring(0, Math.min((a + "").length(), 4)) + ")";
	}

	public static ColorUtil convert(Color color)
	{
		return new ColorUtil((float) color.getRed() / 256F, (float) color.getGreen() / 256F, (float) color.getBlue() / 256F, (float) color.getAlpha() / 256F);
	}

	public ColorUtil mul(double r, double g, double b, double a)
	{
		return new ColorUtil(this.r * r, this.g * g, this.b * b, this.a * a);
	}

	public ColorUtil mul(double var)
	{
		return new ColorUtil(r * var, g * var, b * var, a * var);
	}

	public ColorUtil add(ColorUtil color)
	{
		return new ColorUtil(color.r + r, color.g + g, color.b + b, color.a + a);
	}
}

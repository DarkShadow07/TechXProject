package DrShadow.TechXProject.util;

import net.minecraft.client.gui.Gui;

public class OverlayHelper extends Gui
{
	public OverlayHelper()
	{

	}

	public static void drawWindow(int x, int y, int w, int h, int bgColor)
	{
		drawRect(x - 3, y - 4, x + w + 3, y - 3, bgColor);
		drawRect(x - 3, y + h + 3, x + w + 3, y + h + 4, bgColor);
		drawRect(x - 3, y - 3, x + w + 3, y + h + 3, bgColor);
		drawRect(x - 4, y - 3, x - 3, y + h + 3, bgColor);
		drawRect(x + w + 3, y - 3, x + w + 4, y + h + 3, bgColor);
	}

	public void drawPlaneWithFullBorder(int x, int y, int w, int h, int bgColor, int borderColor)
	{
		drawRect(x - 1, y - 2, x + w + 2, y - 1, borderColor);
		drawRect(x - 1, y + h, x + w + 2, y + h + 1, borderColor);
		drawRect(x - 1, y - 1, x + w + 1, y + h, bgColor);
		drawRect(x - 1, y - 2, x - 2, y + h + 1, borderColor);
		drawRect(x + w + 1, y - 1, x + w + 2, y + h + 1, borderColor);
	}

	public void drawPlaneWithBorder(int x, int y, int w, int h, int bgColor, int frameColor, boolean fade)
	{
		drawWindow(x, y, w, h, bgColor);
		int frameFade;
		if (fade)
		{
			frameFade = (frameColor & 0xFEFEFE) >> 1 | frameColor & 0xFF000000;
		} else
		{
			frameFade = frameColor;
		}
		drawGradientRect(x - 3, y - 3 + 1, x - 3 + 1, y + h + 3 - 1, frameColor, frameFade);
		drawGradientRect(x + w + 2, y - 3 + 1, x + w + 3, y + h + 3 - 1, frameColor, frameFade);
		drawGradientRect(x - 3, y - 3, x + w + 3, y - 3 + 1, frameColor, frameColor);
		drawGradientRect(x - 3, y + h + 2, x + w + 3, y + h + 3, frameFade, frameFade);
	}

	public void drawPlane(int x, int y, int w, int h, int color)
	{
		drawRect(x, y, x + w, y + h, color);
	}
}

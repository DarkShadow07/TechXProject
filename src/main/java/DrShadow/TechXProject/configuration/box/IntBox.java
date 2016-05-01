package DrShadow.TechXProject.configuration.box;

import DrShadow.TechXProject.util.GuiUtil;
import DrShadow.TechXProject.util.Util;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.text.NumberFormat;

public class IntBox
{
	public int x, y, defaultValue, minValue, maxValue, currentValue;

	public IntBox(int defaultValue, int minValue, int maxValue)
	{
		this.defaultValue = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public void onMouseClicked(int mouseX, int mouseY)
	{
		Rectangle left = new Rectangle(x - 10, y, 8, 10);
		Rectangle right = new Rectangle(x + 128, y, 8, 10);

		if (left.contains(mouseX, mouseY))
		{
			if (currentValue > minValue)
			{
				currentValue -= 1;
			}
		}

		if (right.contains(mouseX, mouseY))
		{
			if (currentValue < maxValue)
			{
				currentValue += 1;
			}
		}
	}

	public void render(int x, int y)
	{
		this.x = x;
		this.y = y;

		GuiUtil overlayHelper = new GuiUtil();
		FontRenderer fontRenderer = Util.minecraft().fontRendererObj;

		String current = NumberFormat.getInstance().format(getCurrentValue());

		overlayHelper.drawPlaneWithFullBorder(x, y, 126, 10, Color.BLACK.hashCode(), Color.GRAY.hashCode());

		overlayHelper.drawPlaneWithFullBorder(x - 10, y, 8, 10, Color.RED.hashCode(), new Color(0, 0, 0, 0).hashCode());
		overlayHelper.drawPlaneWithFullBorder(x + 128, y, 8, 10, Color.GREEN.hashCode(), new Color(0, 0, 0, 0).hashCode());

		fontRenderer.drawString(current, x + (128 / 2) - (fontRenderer.getStringWidth(current) / 2), y + 1, Color.WHITE.hashCode(), true);
	}

	public int getCurrentValue()
	{
		return currentValue;
	}

	public void setCurrentValue(int value)
	{
		this.currentValue = Util.keepInBounds(value, minValue, maxValue);
	}
}

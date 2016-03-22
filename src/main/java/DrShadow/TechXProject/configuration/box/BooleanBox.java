package DrShadow.TechXProject.configuration.box;

import DrShadow.TechXProject.util.Helper;
import DrShadow.TechXProject.util.OverlayHelper;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class BooleanBox
{
	public int x, y;
	public boolean defaultValue, currentValue;

	public BooleanBox(boolean defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public void onMouseClicked(int mouseX, int mouseY)
	{
		Rectangle button = new Rectangle(x, y, 128, 10);

		if (button.contains(mouseX, mouseY))
		{
			if (currentValue)
			{
				currentValue = false;
			} else if (!currentValue)
			{
				currentValue = true;
			}
		}
	}

	public void render(int x, int y)
	{
		this.x = x;
		this.y = y;

		OverlayHelper overlayHelper = new OverlayHelper();
		FontRenderer fontRenderer = Helper.minecraft().fontRendererObj;

		overlayHelper.drawPlaneWithFullBorder(x, y, 126, 10, Color.BLACK.hashCode(), Color.GRAY.hashCode());

		fontRenderer.drawString(currentValue + "", x + (128 / 2) - (fontRenderer.getStringWidth(currentValue + "") / 2), y + 1, Color.WHITE.hashCode(), true);
	}

	public boolean getCurrentValue()
	{
		return currentValue;
	}

	public void setCurrentValue(boolean value)
	{

		this.currentValue = value;
	}
}

package DarkS.TechXProject.configuration.box;

import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.GuiUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.awt.*;
import java.text.NumberFormat;

public class FloatBox
{
	public int x, y;
	public float defaultValue, minValue, maxValue, currentValue;

	public FloatBox(float defaultValue, float minValue, float maxValue)
	{
		this.defaultValue = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public void onMouseClicked(int mouseX, int mouseY)
	{
		Rectangle left = new Rectangle(x - 12, y, 8, 20);
		Rectangle right = new Rectangle(x + 128, y, 8, 20);

		if (left.contains(mouseX, mouseY))
		{
			if (currentValue > minValue)
			{
				currentValue -= 0.01f;
			}
		}

		if (right.contains(mouseX, mouseY))
		{
			if (currentValue < maxValue)
			{
				currentValue += 0.01f;
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

		overlayHelper.drawPlaneWithFullBorder(x, y, 126, 17, Color.BLACK.hashCode(), Color.GRAY.hashCode());

		GlStateManager.color(1, 1, 1, 1);
		Util.minecraft().renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/widgets.png"));

		GuiUtils.drawTexturedModalRect(x - 12, y - 2, 0, 0, 10, 20, 100);
		GuiUtils.drawTexturedModalRect(x + 128, y - 2, 10, 0, 10, 20, 100);

		fontRenderer.drawString(current, x + (128 / 2) - (fontRenderer.getStringWidth(current) / 2), y + 5, Color.WHITE.hashCode(), true);
	}

	public float getCurrentValue()
	{
		return currentValue;
	}

	public void setCurrentValue(float value)
	{
		this.currentValue = Util.keepInBounds(value, minValue, maxValue);
	}
}

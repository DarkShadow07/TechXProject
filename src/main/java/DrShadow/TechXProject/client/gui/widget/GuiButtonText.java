package DrShadow.TechXProject.client.gui.widget;

import DrShadow.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class GuiButtonText extends GuiButton
{
	public GuiButtonText(int buttonId, int x, int y, String buttonText)
	{
		super(buttonId, x, y, Util.minecraft().fontRendererObj.getStringWidth(buttonText), Util.minecraft().fontRendererObj.FONT_HEIGHT, buttonText);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		displayString = ChatFormatting.UNDERLINE + displayString;

		Rectangle rectangle = new Rectangle(xPosition, yPosition, width, height);

		if (rectangle.contains(mouseX, mouseY))
		{
			mc.fontRendererObj.drawString(ChatFormatting.ITALIC + displayString, xPosition, yPosition, Color.black.hashCode());
		} else mc.fontRendererObj.drawString(displayString, xPosition, yPosition, Color.black.hashCode());
	}
}

package DarkS.TechXProject.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class GuiUtil extends GuiScreen
{
	public GuiUtil()
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

	public void drawItemStack(ItemStack stack, int x, int y, RenderItem renderItem, FontRenderer fontRenderer, int overlayColor)
	{
		zLevel = 50.0f;
		renderItem.zLevel = 50.0f;

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		RenderHelper.enableGUIStandardItemLighting();
		renderItem.renderItemAndEffectIntoGUI(stack, x, y);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		String stackSize = "";
		if (stack.stackSize >= 1000)
		{
			float value = (float) stack.stackSize / 1000;
			String unit = "k";
			if (value >= 1000)
			{
				value /= 1000;
				unit = "m";
			}
			stackSize = new DecimalFormat("#.#").format(value) + unit;
		} else if (stack.stackSize > 1)
			stackSize = Integer.toString(stack.stackSize);

		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 16, y + 16, 0);

		GlStateManager.scale(0.5f, 0.5f, 0.5f);

		fontRenderer.drawStringWithShadow(stackSize, 1 - fontRenderer.getStringWidth(stackSize), -8, Color.white.getRGB());

		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();

		renderItem.renderItemOverlayIntoGUI(fontRenderer, stack, x, y, "");

		GlStateManager.disableDepth();
		GlStateManager.colorMask(true, true, true, false);

		if (overlayColor != 0)
		{
			zLevel = 100.0f;
			renderItem.zLevel = 100.0f;

			drawPlane(x, y, 16, 16, overlayColor);
		}

		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.enableDepth();

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		zLevel = 0.0f;
		renderItem.zLevel = 0.0f;
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

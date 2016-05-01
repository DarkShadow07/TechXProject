package DrShadow.TechXProject.util;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;

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

	public void drawItemStack(ItemStack itemStack, int x, int y, RenderItem renderItem)
	{
		GlStateManager.pushMatrix();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		renderItem.renderItemIntoGUI(itemStack, x, y);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

		GlStateManager.popMatrix();
	}

	public void drawItemStack(ItemStack itemStack, int x, int y, RenderItem renderItem, boolean transparent)
	{
		this.zLevel = 50.0f;
		renderItem.zLevel = 50.0f;

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int colorOverlay = new Color(139, 139, 139, 160).hashCode();

		RenderHelper.enableGUIStandardItemLighting();
		renderItem.renderItemAndEffectIntoGUI(itemStack, x, y);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GlStateManager.disableDepth();
		GlStateManager.colorMask(true, true, true, false);
		if (transparent)
		{
			this.zLevel = 100.0f;
			renderItem.zLevel = 100.0f;
			this.drawGradientRect(x, y, x + 16, y + 16, colorOverlay, colorOverlay);
		}
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.enableDepth();

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		this.zLevel = 0.0f;
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

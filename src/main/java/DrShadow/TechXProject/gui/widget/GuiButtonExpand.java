package DrShadow.TechXProject.gui.widget;

import DrShadow.TechXProject.gui.GuiContainerBase;
import DrShadow.TechXProject.util.OverlayHelper;
import DrShadow.TechXProject.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;

public class GuiButtonExpand extends GuiButton
{
	protected boolean expanded = false, expanding = false, fullExpandedW = false, fullExpandedH = false;
	protected Color color;
	private int x, y, w, h, u, v;
	private GuiContainerBase guiIn;
	private String[] data;
	private ResourceLocation texture;

	public GuiButtonExpand(GuiContainerBase guiIn, int buttonId, int w, int h, Color color, String... data)
	{
		this(guiIn, buttonId, w, h, color, null, 0, 0, data);
	}

	public GuiButtonExpand(GuiContainerBase guiIn, int buttonId, int w, int h, Color color, @Nullable ResourceLocation texture, @Nullable int u, @Nullable int v, String... data)
	{
		super(buttonId, (guiIn.width - guiIn.xSize) / 2 + guiIn.xSize, (guiIn.height - guiIn.xSize) / 2 + 24 * buttonId, 24, 24, "");
		this.guiIn = guiIn;

		this.x = (guiIn.width - guiIn.xSize) / 2 + guiIn.xSize;
		this.y = (guiIn.height - guiIn.xSize) / 2 + 24 * buttonId;
		this.w = w;
		this.h = h;
		this.color = color;
		this.data = data;
		this.u = u;
		this.v = v;

		this.texture = texture;
	}

	public GuiButtonExpand(int x, int y, int buttonId, int w, int h, Color color, @Nullable ResourceLocation texture, @Nullable int u, @Nullable int v, String... data)
	{
		super(buttonId, x, y, 24, 24, "");

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
		this.data = data;
		this.u = u;
		this.v = v;

		this.texture = texture;
	}

	public GuiButtonExpand(int x, int y, int buttonId, int w, int h, Color color, String... data)
	{
		this(x, y, buttonId, w, h, color, null, 0, 0, data);
	}

	public void setInfo(String... info)
	{
		data = info;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height)
		{
			if (guiIn != null)
			{
				for (GuiButton button : guiIn.getButtons())
				{
					if (button instanceof GuiButtonExpand && button != this)
					{
						GuiButtonExpand buttonExpand = (GuiButtonExpand) button;

						buttonExpand.expanded = false;
					}
				}
			}

			expanded = !expanded;
		}

		return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		fullExpandedW = width >= w;
		fullExpandedH = height >= h;

		expanding = width > 24 || height > 24;

		int value = 2;

		float ratio = width / height + 0.000001f;

		if (expanded)
		{
			if (!fullExpandedW)
			{
				width += value * ratio;
			}

			if (!fullExpandedH)
			{
				height += value * ratio;
			}

			width = Util.keepInBounds(width, 24, w);
			height = Util.keepInBounds(height, 24, h);
		} else
		{
			if (width > 24)
			{
				width -= value * ratio;
			}

			if (height > 24)
			{
				height -= value * ratio;
			}

			width = Util.keepInBounds(width, 24, w);
			height = Util.keepInBounds(height, 24, h);
		}

		if (visible)
		{
			drawCustomButton(xPosition, yPosition, width, height, color);

			if (texture != null && !expanding)
			{
				mc.getTextureManager().bindTexture(texture);
				drawScaledCustomSizeModalRect(x + 3, y + 4, u, v, 16, 16, 16, 16, 16, 16);
			}
		}

		if (data[0] != null && fullExpandedW && fullExpandedH)
		{
			drawString(mc.fontRendererObj, data[0], x + 2, y + 5, Color.white.getRGB());
		}

		if (data.length > 1 && data[1] != null && fullExpandedW && fullExpandedH)
		{
			mc.fontRendererObj.drawSplitString(data[1], x + 2, y + 16, w, Color.black.getRGB());
		}

		if (guiIn != null)
		{
			for (GuiButton button : guiIn.getButtons())
			{
				if (button instanceof GuiButtonExpand && button != this && expanding)
				{
					GuiButtonExpand expand = (GuiButtonExpand) button;

					if (expand.id >= id)
					{
						expand.yPosition = expand.y + height - 24;
					}
				}
			}
		}

		if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height && !expanded && data[0] != null)
		{
			String name = data[0];

			OverlayHelper helper = new OverlayHelper();

			helper.drawPlaneWithBorder(mouseX + 14, mouseY - 10, mc.fontRendererObj.getStringWidth(name), 8, -267386864, 1347420415, true);

			mc.fontRendererObj.drawString(name, mouseX + 14, mouseY - 10, Color.white.getRGB(), true);
		}
	}

	private void drawCustomButton(int x, int y, int w, int h, Color color)
	{
		Color white = mixColor(Color.white, color, 0.5f);
		Color black = Color.black;
		Color gray = mixColor(new Color(0.33203125f, 0.33203125f, 0.33203125f), color, 0.5f);
		Color body = mixColor(new Color(0.7734375f, 0.7734375f, 0.7734375f), color, 0.75f);

		drawRect(x, y, x + w - 3, y + 1, black.getRGB());
		drawRect(x, y + 1, x + w - 3, y + 3, white.getRGB());
		drawRect(x, y + 3, x + w - 3, y + h - 3, body.getRGB());
		drawRect(x, y + h, x + w - 3, y + h - 1, black.getRGB());
		drawRect(x, y + h - 1, x + w - 3, y + h - 3, gray.getRGB());
		drawRect(x + w - 3, y + 3, x + w - 1, y + h - 3, gray.getRGB());
		drawRect(x + w - 4, y + h - 2, x + w - 2, y + h - 4, gray.getRGB());
		drawRect(x + w - 1, y + 3, x + w, y + h - 3, black.getRGB());
		drawRect(x + w - 1, y + h - 2, x + w - 2, y + h - 3, black.getRGB());
		drawRect(x + w - 2, y + h - 1, x + w - 3, y + h - 2, black.getRGB());
		drawRect(x + w - 2, y + 1, x + w - 3, y + 2, black.getRGB());
		drawRect(x + w - 1, y + 2, x + w - 2, y + 3, black.getRGB());
		drawRect(x + w - 3, y + 2, x + w - 2, y + 3, body.getRGB());
	}

	private Color mixColor(Color c1, Color c2, float ratio)
	{
		if (ratio > 1f) ratio = 1f;
		else if (ratio < 0f) ratio = 0f;
		float iRatio = 1.0f - ratio;

		int i1 = c1.getRGB();
		int i2 = c2.getRGB();

		int a1 = (i1 >> 24 & 0xff);
		int r1 = ((i1 & 0xff0000) >> 16);
		int g1 = ((i1 & 0xff00) >> 8);
		int b1 = (i1 & 0xff);

		int a2 = (i2 >> 24 & 0xff);
		int r2 = ((i2 & 0xff0000) >> 16);
		int g2 = ((i2 & 0xff00) >> 8);
		int b2 = (i2 & 0xff);

		int a = (int) ((a1 * iRatio) + (a2 * ratio));
		int r = (int) ((r1 * iRatio) + (r2 * ratio));
		int g = (int) ((g1 * iRatio) + (g2 * ratio));
		int b = (int) ((b1 * iRatio) + (b2 * ratio));

		return new Color(a << 24 | r << 16 | g << 8 | b);
	}
}

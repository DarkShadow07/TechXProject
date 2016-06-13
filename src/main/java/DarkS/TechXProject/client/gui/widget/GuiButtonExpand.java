package DarkS.TechXProject.client.gui.widget;

import DarkS.TechXProject.util.GuiUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

public class GuiButtonExpand extends GuiButton
{
	protected boolean expanded, expanding, fullExpandedW, fullExpandedH;
	protected Color color;
	private int x, y, w, h, u, v;
	private GuiScreen guiIn;
	private Field buttonList;

	private String[] data;
	private ResourceLocation texture;

	public GuiButtonExpand(GuiContainer guiIn, int buttonId, int w, int h, Color color, String... data)
	{
		this(guiIn, buttonId, w, h, color, null, 0, 0, data);
	}

	public GuiButtonExpand(GuiContainer guiIn, int buttonId, int w, int h, Color color, @Nullable ResourceLocation texture, @Nullable int u, @Nullable int v, String... data)
	{
		super(buttonId, (guiIn.width - guiIn.xSize) / 2 + guiIn.xSize, (guiIn.height - guiIn.ySize) / 2 + 8 + 25 * buttonId, 24, 24, "");
		this.guiIn = guiIn;

		this.x = (guiIn.width - guiIn.xSize) / 2 + guiIn.xSize;
		this.y = (guiIn.height - guiIn.ySize) / 2 + 8 + 25 * buttonId;
		this.w = w;
		this.h = h;
		this.color = color;
		this.data = data;
		this.u = u;
		this.v = v;

		this.texture = texture;

		buttonList = ReflectionHelper.findField(GuiScreen.class, "buttonList");
		buttonList.setAccessible(true);

		try
		{
			((List<GuiButton>) buttonList.get(guiIn)).add(this);
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
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

	public void setTitle(String title)
	{
		data[0] = title;
	}

	public void setInfo(String info)
	{
		data[1] = info;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height)
		{
			if (guiIn != null)
			{
				try
				{
					for (GuiButton button : (List<GuiButton>) buttonList.get(guiIn))
					{
						if (button != null && button instanceof GuiButtonExpand && button != this)
						{
							GuiButtonExpand buttonExpand = (GuiButtonExpand) button;

							buttonExpand.expanded = false;
						}
					}
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
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

		float ratio = w / h + 0.000001f;

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

			if (texture != null && (!fullExpandedW || !fullExpandedH))
			{
				GlStateManager.color(1, 1, 1, 1);
				mc.getTextureManager().bindTexture(texture);
				drawScaledCustomSizeModalRect(x + 2, y + 4, u, v, 16, 16, 16, 16, 16, 16);
			}
		}

		if (fullExpandedW && fullExpandedH && data[0] != null)
		{
			drawString(mc.fontRendererObj, data[0], x + 2, y + 5, Color.white.getRGB());
		}

		if (data.length > 1 && data[1] != null && fullExpandedW && fullExpandedW)
		{
			GL11.glScissor(x, y, width, height);
			mc.fontRendererObj.drawSplitString(data[1], x + 2, y + 16, w, Color.black.getRGB());
		}

		if (guiIn != null)
		{
			try
			{
				for (GuiButton button : (List<GuiButton>) buttonList.get(guiIn))
				{
					if (button != null && button instanceof GuiButtonExpand && button != this)
					{
						GuiButtonExpand expand = (GuiButtonExpand) button;

						if (expand.id >= id)
						{
							expand.yPosition = expand.y + height - 24;
						}
					}
				}
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}

		if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height && !expanded && data[0] != null)
		{
			String name = data[0];

			GuiUtil helper = new GuiUtil();

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
		drawRect(x, y, x + 1, y + h, new Color(0, 0, 0, 0.35f).getRGB());
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

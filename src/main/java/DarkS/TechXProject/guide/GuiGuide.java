package DarkS.TechXProject.guide;

import DarkS.TechXProject.client.gui.GuiContainerBase;
import DarkS.TechXProject.blocks.base.ContainerDummy;
import DarkS.TechXProject.init.InitItems;
import DarkS.TechXProject.items.ItemGuide;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiGuide extends GuiContainerBase
{
	public static List<IGuideElement> elements = new ArrayList<>();
	public int midX, midY;
	private int top, left;
	private GuiButton next, prev, main;
	private int index;

	public GuiGuide(int index)
	{
		this();

		this.index = index;
	}

	public GuiGuide()
	{
		super(new ContainerDummy());

		xSize = 360;
		ySize = 180;
	}

	public static void addElement(IGuideElement element)
	{
		elements.add(element);
	}

	public void addElements()
	{
		elements.clear();

		addElement(new Elements.MainPage(this));

		addElement(new Elements.NewsPage("What's New?", this));
		for (int i = 0; i < Elements.MainPage.entries.size(); i++)
		{
			GuiGuide.addElement(new Elements.EntryPage("Entry - " + Elements.MainPage.entries.get(i), Lang.localize("guide.entry." + Elements.MainPage.entries.get(i), false), this));
		}

		elements.get(index).init();
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;

		midX = left + xSize / 2;
		midY = top;

		prev = new ButtonArrow(0, midX + 22, top + ySize - 22, 0);
		next = new ButtonArrow(1, left + xSize - 42, top + ySize - 22, 1);
		main = new ButtonArrow(2, left - 11, top + 8, 2);
		main.visible = index > 0;

		buttonList.add(prev);
		buttonList.add(next);
		buttonList.add(main);

		addElements();
	}

	private void reloadElements()
	{
		buttonList.clear();

		buttonList.add(prev);
		buttonList.add(next);
		buttonList.add(main);

		for (IGuideElement element : elements)
		{
			element.stop();
		}

		elements.get(index).init();
	}

	public int getByName(String name)
	{
		for (int i = 0; i < elements.size(); i++)
		{
			if (elements.get(i).getName().equalsIgnoreCase(name))
			{
				return i;
			}
		}

		return 0;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		elements.get(index).actionPerformed(button);

		switch (button.id)
		{
			case 0:
				setIndex(index - 1);
				break;
			case 1:
				setIndex(index + 1);
				break;
			case 2:
				setIndex(0);
				break;
		}
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		EntityPlayer player = mc.thePlayer;

		if (player != null && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem().equals(InitItems.guide.item))
		{
			ItemStack stack = player.getHeldItemMainhand();

			((ItemGuide) stack.getItem()).setIndex(stack, index);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);

		elements.get(index).onKeyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		elements.get(index).onMouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void onResize(Minecraft mcIn, int w, int h)
	{
		reloadElements();

		super.onResize(mcIn, w, h);
	}

	public void setIndex(int index)
	{
		this.index = index;

		reloadElements();
	}

	@Override
	public void updateScreen()
	{
		next.enabled = index < elements.size() - 1;
		prev.enabled = index > 0;
		main.enabled = index > 0;
		main.visible = index > 0;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		elements.get(index).render();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation leftTexture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Guide.png");
		ResourceLocation rightTexture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_GuideR.png");

		mc.getTextureManager().bindTexture(leftTexture);
		drawTexturedModalRect(left, top, 0, 0, 180, 180);
		mc.getTextureManager().bindTexture(rightTexture);
		drawTexturedModalRect(left + 180, top, 0, 0, 180, 180);

		String page = String.format("%s/%s", index + 1, elements.size());

		fontRendererObj.drawString(page, midX + 180 / 2 - fontRendererObj.getStringWidth(page) / 2, top + ySize - 20, Color.black.getRGB());

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	private class ButtonArrow extends GuiButton
	{
		private int mode = 0;

		private ButtonArrow(int buttonId, int x, int y, int mode)
		{
			super(buttonId, x, y, mode == 2 ? 14 : 18, mode == 2 ? 17 : 10, "");

			this.mode = mode;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Guide.png");
			mc.getTextureManager().bindTexture(texture);

			Rectangle button = new Rectangle(xPosition, yPosition, width, height);

			if (!visible) return;

			switch (mode)
			{
				case 0:
				case 1:
					if (button.contains(mouseX, mouseY) && enabled)
					{
						if (mode == 0)
						{
							drawTexturedModalRect(xPosition, yPosition, 18, 193, 18, 10);
						} else
						{
							drawTexturedModalRect(xPosition, yPosition, 18, 180, 18, 10);
						}
					} else
					{
						if (mode == 0)
						{
							drawTexturedModalRect(xPosition, yPosition, 0, 193, 18, 10);
						} else
						{
							drawTexturedModalRect(xPosition, yPosition, 0, 180, 18, 10);
						}
					}
					break;
				case 2:
					if (button.contains(mouseX, mouseY) && enabled)
					{
						drawTexturedModalRect(xPosition, yPosition, 50, 180, 14, 17);
					} else drawTexturedModalRect(xPosition, yPosition, 36, 180, 14, 17);
			}
		}
	}
}

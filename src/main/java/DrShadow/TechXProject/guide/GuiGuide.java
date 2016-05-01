package DrShadow.TechXProject.guide;

import DrShadow.TechXProject.client.gui.GuiContainerBase;
import DrShadow.TechXProject.container.ContainerDummy;
import DrShadow.TechXProject.init.InitItems;
import DrShadow.TechXProject.items.ItemGuide;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Util;
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
	public int midX, midY;
	private int top, left;
	private GuiButton next, prev;

	private static List<IGuideElement> elements = new ArrayList<>();

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

		addElements();
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;

		midX = left + xSize / 2;
		midY = top;

		prev = new ButtonArrow(0, midX + 22, top + ySize - 22, true);
		next = new ButtonArrow(1, left + xSize - 42, top + ySize - 22, false);

		reloadElements();
	}

	public void addElements()
	{
		elements.clear();

		addElement(new Elements.MainPage(this));
	}

	public static void addElement(IGuideElement element)
	{
		elements.add(element);
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
				index -= 1;
				reloadElements();
				break;
			case 1:
				index += 1;
				reloadElements();
				break;
			default:
				reloadElements();
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

	private void reloadElements()
	{
		buttonList.clear();

		buttonList.add(prev);
		buttonList.add(next);

		elements.get(index).init();
	}

	public void setIndex(int index)
	{
		this.index = index;

		reloadElements();
	}

	@Override
	public void updateScreen()
	{
		index = Util.keepInBounds(index, 0, elements.size() - 1);

		next.enabled = index < elements.size() - 1;
		prev.enabled = index > 0;
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
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Guide.png");
		ResourceLocation textureLeft = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_GuideLeft.png");

		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left + 180, top, 0, 0, 180, 180);
		mc.getTextureManager().bindTexture(textureLeft);
		drawTexturedModalRect(left, top, 0, 0, 180, 180);

		fontRendererObj.drawString(String.format("%s/%s", index + 1, elements.size()), midX + xSize / 4 - fontRendererObj.getStringWidth(String.format("%s/%s", index + 1, elements.size())) / 2, top + ySize - 20, Color.black.getRGB());
	}

	private class ButtonArrow extends GuiButton
	{
		private boolean inverted = false;

		private ButtonArrow(int buttonId, int x, int y, boolean inverted)
		{
			super(buttonId, x, y, 18, 10, "");

			this.inverted = inverted;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Guide.png");
			mc.getTextureManager().bindTexture(texture);

			Rectangle button = new Rectangle(xPosition, yPosition, width, height);

			if (button.contains(mouseX, mouseY) && enabled)
			{
				if (inverted)
				{
					drawTexturedModalRect(xPosition, yPosition, 18, 193, 18, 10);
				} else
				{
					drawTexturedModalRect(xPosition, yPosition, 18, 180, 18, 10);
				}
			} else
			{
				if (inverted)
				{
					drawTexturedModalRect(xPosition, yPosition, 0, 193, 18, 10);
				} else
				{
					drawTexturedModalRect(xPosition, yPosition, 0, 180, 18, 10);
				}
			}
		}
	}
}

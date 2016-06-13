package DarkS.TechXProject.client.gui;

import DarkS.TechXProject.reference.Icons;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.util.List;

public class GuiContainerBase extends GuiContainer
{
	public GuiContainerBase(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}

	protected void drawIcon(Icons icon, int x, int y)
	{

	}

	public List<GuiButton> getButtons()
	{
		return buttonList;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{

	}
}

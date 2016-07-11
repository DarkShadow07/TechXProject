package DarkS.TechXProject.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.util.List;

public abstract class GuiContainerBase extends GuiContainer
{
	public GuiContainerBase(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}

	public List<GuiButton> getButtons()
	{
		return buttonList;
	}
}

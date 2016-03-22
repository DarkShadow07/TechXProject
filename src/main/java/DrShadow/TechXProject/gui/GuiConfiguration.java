package DrShadow.TechXProject.gui;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.configuration.ConfigurationHandler;
import DrShadow.TechXProject.configuration.elements.ConfigurationElement;
import DrShadow.TechXProject.util.Helper;
import DrShadow.TechXProject.util.Lang;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiConfiguration extends GuiScreen
{
	private GuiTextField searchBar;
	private GuiButton reset;

	private ConfigurationHandler handler;
	private ScaledResolution resolution;

	private int offset, maxElements;

	public GuiConfiguration()
	{

	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		resolution = new ScaledResolution(Helper.minecraft());

		handler = TechXProject.configurationHandler;

		searchBar = new GuiTextField(0, fontRendererObj, 10, 10, 128, 12);
		searchBar.setCanLoseFocus(true);
		searchBar.setFocused(false);

		reset = new GuiButton(0, 142, 6, fontRendererObj.getStringWidth(Lang.localize("config.reset")) + 8, 20, Lang.localize("config.reset"));
		buttonList.add(reset);

		handler.readConfig();

		updateFilteredElements();
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();

		int direction = Mouse.getEventDWheel();

		if (direction != 0)
		{
			if (direction > 1) offset -= 1;
			if (direction < 1) offset += 1;

			offset = Helper.keepInBounds(offset, 0, handler.filteredElements.size() - maxElements);
		}
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		handler.writeConfig();
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		searchBar.updateCursorCounter();

		for (ConfigurationElement element : handler.elements)
		{
			element.isDefault = Helper.equals(element.getDefaultValue(), element.getValue());
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);

		switch (button.id)
		{
			case 0:
				for (ConfigurationElement element : handler.filteredElements)
				{
					element.reset();
					element.isDefault = true;
				}
				break;
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		searchBar.mouseClicked(mouseX, mouseY, mouseButton);

		handler.filteredElements.forEach(element -> element.onMouseClick(mouseX, mouseY, mouseButton));
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);

		if (searchBar.isFocused())
		{
			searchBar.textboxKeyTyped(typedChar, keyCode);

			updateFilteredElements();
		}

		handler.filteredElements.forEach(element -> element.onKeyTyped(typedChar, keyCode));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		searchBar.drawTextBox();

		super.drawScreen(mouseX, mouseY, partialTicks);

		for (int i = 0; i < maxElements; i++)
		{
			try
			{
				ConfigurationElement element = handler.filteredElements.get(i + offset);

				if (element != null)
				{
					element.renderElement(11, 32 + 16 * i);
				}
			} catch (IndexOutOfBoundsException e)
			{
			}
		}

		if (isCtrlKeyDown())
		{
			handler.filteredElements.forEach(element -> element.renderWithMousePos(mouseX, mouseY));
		}

		maxElements = resolution.getScaledHeight() / 18;
	}

	public void updateFilteredElements()
	{
		handler.filteredElements.clear();

		if (searchBar.getText() != "")
		{
			for (ConfigurationElement element : handler.elements)
			{
				if (element.getName().toLowerCase().contains(searchBar.getText().toLowerCase()) && !handler.filteredElements.contains(element))
				{
					handler.filteredElements.add(element);
				}
			}
		} else
		{
			handler.filteredElements.addAll(handler.elements);
		}
	}
}

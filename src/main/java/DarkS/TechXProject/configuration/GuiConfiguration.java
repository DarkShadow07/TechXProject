package DarkS.TechXProject.configuration;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.configuration.elements.ConfigurationElement;
import DarkS.TechXProject.util.Lang;
import DarkS.TechXProject.util.Util;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
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
		return false;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		resolution = new ScaledResolution(Util.minecraft());

		handler = TechXProject.configurationHandler;

		handler.init();

		searchBar = new GuiTextField(0, fontRendererObj, 10, 7, 128, 18);
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

			offset = Util.keepInBounds(offset, 0, handler.filteredElements.size() - maxElements);
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
			element.isDefault = Util.equals(element.getDefaultValue(), element.getValue());
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

		ConfigurationHandler.filteredElements.forEach(element -> element.onMouseClick(mouseX, mouseY, mouseButton));
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
		} else if (keyCode == Keyboard.KEY_E) mc.thePlayer.closeScreen();

		ConfigurationHandler.filteredElements.forEach(element -> element.onKeyTyped(typedChar, keyCode));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		searchBar.drawTextBox();

		super.drawScreen(mouseX, mouseY, partialTicks);

		for (int i = 0; i < Math.min(maxElements, ConfigurationHandler.filteredElements.size()); i++)
		{
			try
			{
				if (ConfigurationHandler.filteredElements.get(i + offset) != null)
				{
					ConfigurationHandler.filteredElements.get(i + offset).renderElement(11, 32 + 22 * i);
				}
			} catch (Exception ignored)
			{

			}
		}
		ConfigurationHandler.filteredElements.forEach(element -> element.renderWithMousePos(mouseX, mouseY));

		maxElements = resolution.getScaledHeight() / 22;
	}

	public void updateFilteredElements()
	{
		ConfigurationHandler.filteredElements.clear();

		if (!searchBar.getText().equals(""))
		{
			for (ConfigurationElement element : ConfigurationHandler.elements)
			{
				if (element.getName().toLowerCase().contains(searchBar.getText().toLowerCase()) && !ConfigurationHandler.filteredElements.contains(element))
				{
					ConfigurationHandler.filteredElements.add(element);
				}
			}
		} else
		{
			ConfigurationHandler.filteredElements.addAll(ConfigurationHandler.elements);
		}
	}
}

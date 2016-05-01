package DrShadow.TechXProject.guide;

import DrShadow.TechXProject.client.gui.widget.GuiButtonText;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Lang;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Elements
{
	public static class MainPage extends GuideElementBase
	{
		List<GuiButtonText> buttons = new ArrayList<>();

		List<String> entries = new ArrayList<>();

		public MainPage(GuiGuide gui)
		{
			super("Index", gui);
		}

		@Override
		public void init()
		{
			entries.clear();

			entries.add("Basic Mechanics");
			entries.add("Ores");
			entries.add("Ore Processing");
			entries.add("Ore Doubling");
			entries.add("Machine Assembling");
			entries.add("Generating Energy");
			entries.add("Storing Energy");

			buttons.clear();

			for (int i = 0; i < entries.size(); i++)
			{
				buttons.add(new GuiButtonText(gui.getButtons().size(), gui.guiLeft + 16, gui.midY + 24 + 12 * i, entries.get(i)));

				class EntryPage extends GuideElementBase
				{
					private String desc;

					public EntryPage(String name, String desc, GuiGuide gui)
					{
						super(name, gui);

						this.desc = desc;
					}

					@Override
					public void render()
					{
						String title = name.substring(8);
						font.drawString(title, 180 / 2 - font.getStringWidth(title) / 2, 12, Color.black.getRGB());

						font.drawSplitString(desc, 16, 24, 150, Color.black.getRGB());
					}
				}

				GuiGuide.addElement(new EntryPage("Entry - " + entries.get(i), Lang.localize("guide.entry." + entries.get(i), false), gui));
			}

			gui.getButtons().addAll(buttons);
		}

		@Override
		public void actionPerformed(GuiButton button)
		{
			if (button instanceof GuiButtonText)
			{
				for (String entry : entries)
				{
					if (button.displayString.substring(button.displayString.lastIndexOf("§n") + 2).equalsIgnoreCase(entry))
					{
						gui.setIndex(gui.getByName("Entry - " + entry));
					}
				}
			}
		}

		@Override
		public void render()
		{
			String title = Reference.MOD_NAME + " Guide";
			font.drawString(title, 180 / 2 - font.getStringWidth(title) / 2, 12, Color.black.getRGB());
		}
	}
}

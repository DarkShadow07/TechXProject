package DrShadow.TechXProject.guide;

import DrShadow.TechXProject.client.gui.widget.GuiButtonText;
import DrShadow.TechXProject.reference.Reference;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Elements
{
	public static class NewsPage extends GuideElementBase
	{
		public NewsPage(String name, GuiGuide gui)
		{
			super(name, gui);
		}

		@Override
		public void stop()
		{

		}

		@Override
		public void render()
		{
			String desc = "";

			try
			{
				URL url = new URL("https://raw.githubusercontent.com/DarkShadow07/TechXProject/1.9/src/changelogs/" + Reference.VERSION + ".txt");
				Scanner s = new Scanner(url.openStream());

				while (s.hasNextLine())
				{
					desc = desc + s.nextLine();
				}

				s.close();
			} catch (Exception ignored)
			{

			}

			font.drawString(name, 180 / 2 - font.getStringWidth(name) / 2, 12, Color.black.getRGB());

			String[] data = desc.split("%n%");

			int lines = 0;

			int x = 16;

			if (data.length > 0)
			{
				for (String string : data)
				{
					if (lines >= 12)
					{
						x = 196;
					}

					font.drawSplitString(string, x, lines + 24, 150, Color.black.getRGB());

					lines += font.splitStringWidth(string, 150) + 2;
				}
			} else font.drawSplitString(desc, 16, 24, 150, Color.black.getRGB());
		}
	}

	public static class MainPage extends GuideElementBase
	{
		public static List<String> entries = new ArrayList<>();
		List<GuiButtonText> buttons = new ArrayList<>();

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
			}

			gui.getButtons().addAll(buttons);
		}

		@Override
		public void stop()
		{
			gui.getButtons().removeAll(buttons);
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

	public static class EntryPage extends GuideElementBase
	{
		private String desc;

		public EntryPage(String name, String desc, GuiGuide gui)
		{
			super(name, gui);

			this.desc = desc;
		}

		@Override
		public void stop()
		{

		}

		@Override
		public void render()
		{
			String title = name.substring(8);
			font.drawString(title, 180 / 2 - font.getStringWidth(title) / 2, 12, Color.black.getRGB());

			String[] data = desc.split("%n%");

			int lines = 0;

			if (data.length > 0)
			{
				for (String string : data)
				{
					font.drawSplitString(string, 16, lines + 24, 150, Color.black.getRGB());

					lines += font.splitStringWidth(string, 150) + 5;
				}
			} else font.drawSplitString(desc, 16, 24, 150, Color.black.getRGB());
		}
	}
}

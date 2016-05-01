package DrShadow.TechXProject.util;

import DrShadow.TechXProject.reference.Reference;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker
{
	public static String currentVersion = Reference.VERSION;
	public static String newestVersion;

	public static Boolean show = false;

	public static void init()
	{
		getNewestVersion();

		if (newestVersion != null)
		{
			if (newestVersion.equalsIgnoreCase(currentVersion))
			{
				show = false;

			} else
			{
				show = true;

				Logger.info(Reference.MOD_NAME + " is Running on a older Version " + currentVersion + " - " + newestVersion);
			}
		}
	}

	private static void getNewestVersion()
	{
		try
		{
			URL url = new URL("https://raw.githubusercontent.com/DarkShadow07/TechXProject/1.9/src/version/" + MinecraftForge.MC_VERSION + ".txt");
			Scanner s = new Scanner(url.openStream());

			newestVersion = s.next() + " " + s.next();

			s.close();
		} catch (IOException ex)
		{
			Logger.error("Failed to check for Updates");
		}
	}
}

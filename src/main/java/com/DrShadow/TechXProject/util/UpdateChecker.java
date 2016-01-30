package com.DrShadow.TechXProject.util;

import com.DrShadow.TechXProject.reference.Reference;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker
{
	private static String currentVersion = Reference.VERSION;
	static String newestVersion;
	
	public static Boolean show = false;
	
	public static void init()
	{
		getNewestVersion();
		
		if (newestVersion != null)
		{
			if (newestVersion.equalsIgnoreCase(currentVersion))
			{
				show = false;

			}else {
				show = true;
			}
		}
	}
	
	private static void getNewestVersion()
	{
		try 
		{
			URL url = new URL("https://raw.githubusercontent.com/DarkSahdow07/TechXProject/master/version/" + MinecraftForge.MC_VERSION + ".txt");
			Scanner s = new Scanner(url.openStream());
			
			newestVersion = s.next();

			s.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			LogHelper.error("Failed to check for Updates");
		}
	}
}

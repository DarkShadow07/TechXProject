package DarkS.TechXProject.configuration;

import DarkS.TechXProject.configuration.elements.ConfigurationElement;
import DarkS.TechXProject.configuration.elements.ConfigurationInt;
import DarkS.TechXProject.util.FileUtil;
import DarkS.TechXProject.util.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationHandler
{
	public static List<ConfigurationElement> elements = new ArrayList<>();
	public static List<ConfigurationElement> filteredElements = new ArrayList<>();

	public static ConfigurationElement itemNodeRate = new ConfigurationInt("Item Node Transfer Speed", "Sets the Rate/Speed the Item Nodes will try to transfer Items", 1, 8, 64);

	private File configFile;

	public ConfigurationHandler()
	{
		init();

		try
		{
			setupFile();
		} catch (FileNotFoundException e)
		{
			Logger.info("Created new Configuration File!");
		}
	}

	public static void add(ConfigurationElement element)
	{
		elements.add(element);
	}

	public void init()
	{
		elements.clear();

		Logger.info("Configuration Init!");

		add(itemNodeRate);
	}

	public void setupFile() throws FileNotFoundException
	{
		String path = new File("").getAbsolutePath() + "/config/TechConfig.techfig";

		configFile = new File(path);

		if (!configFile.exists())
		{
			try
			{
				if (configFile.createNewFile())
				{
					writeDefaultConfig();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void readConfig()
	{
		try
		{
			FileReader reader = new FileReader(configFile);
			BufferedReader bufferedReader = new BufferedReader(reader);

			StringBuilder data = new StringBuilder();
			String line;

			while ((line = bufferedReader.readLine()) != null) data.append(line).append("\n");

			Gson gson = new Gson();
			JsonObject fromJson = gson.fromJson(data.toString(), JsonObject.class);

			if (elements != null)
			{
				elements.forEach(element -> {
					if (fromJson != null && element != null)
					{
						element.setValue(fromJson.get(element.getName()).getAsString());
					}
				});
			}

			bufferedReader.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeConfig()
	{
		try
		{
			FileUtil.clearFile(configFile);

			FileWriter writer = new FileWriter(configFile);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);

			Map<String, String> data = new LinkedHashMap<String, String>();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			elements.forEach(configurationElement -> data.put(configurationElement.getName(), String.valueOf(configurationElement.getValue())));

			bufferedWriter.write("# This is a Config file from Tech 'X' Project for the Custom Config System \n");
			bufferedWriter.write("# Any change will be applied in-game instantly,\n");
			bufferedWriter.write("#  you don't need to Restart the Client to apply them\n");

			bufferedWriter.write(gson.toJson(data));

			bufferedWriter.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void writeDefaultConfig()
	{
		try
		{
			FileUtil.clearFile(configFile);

			FileWriter writer = new FileWriter(configFile);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);

			Map<String, String> data = new LinkedHashMap<>();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			elements.forEach(configurationElement -> data.put(configurationElement.getName(), String.valueOf(configurationElement.getDefaultValue())));

			bufferedWriter.write("# This is a Config file from Tech 'X' Project for the Custom Config System \n");
			bufferedWriter.write("# Any change will be applied in-game instantly,\n");
			bufferedWriter.write("#  you don't need to Restart the Client to apply them\n");

			bufferedWriter.write(gson.toJson(data));

			bufferedWriter.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

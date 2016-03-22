package DrShadow.TechXProject.configuration;

import DrShadow.TechXProject.configuration.elements.ConfigurationElement;
import DrShadow.TechXProject.configuration.elements.ConfigurationFloat;
import DrShadow.TechXProject.util.FileUtil;
import DrShadow.TechXProject.util.LogHelper;
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
	public static List<ConfigurationElement> elements = new ArrayList<ConfigurationElement>();
	public static List<ConfigurationElement> filteredElements = new ArrayList<ConfigurationElement>();

	public static ConfigurationFloat CONDUIT_SPEED = new ConfigurationFloat("Conduit Transfer Speed", " ", 0.1f, 0.01f, 1);

	private File configFile;

	public ConfigurationHandler()
	{
		init();

		try
		{
			setupFile();
		} catch (FileNotFoundException e)
		{
			LogHelper.info("Created new Configuration File!");
		}
	}

	public static void add(ConfigurationElement element)
	{
		elements.add(element);
	}

	public void init()
	{
		elements.clear();

		LogHelper.info("Configuration Init!");

		add(CONDUIT_SPEED);
	}

	public void setupFile() throws FileNotFoundException
	{
		String absPath = new File("").getAbsolutePath();
		String path = absPath + "/config/TechConfig.techfig";

		configFile = new File(path);

		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();

				writeDefaultConfig();
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
			String line = null;

			while ((line = bufferedReader.readLine()) != null) data.append(line).append("\n");

			Gson gson = new Gson();
			JsonObject fromJson = gson.fromJson(data.toString(), JsonObject.class);

			elements.forEach(configurationElement ->
			{
				configurationElement.setValue(fromJson.get(configurationElement.getName()).getAsString());
			});

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

			elements.forEach(configurationElement ->
			{
				data.put(configurationElement.getName(), String.valueOf(configurationElement.getValue()));
			});

			bufferedWriter.write("#This is a Config file from Tech 'X' Project for the Custom Config System \n");
			bufferedWriter.write("#Any change will be applied in-game instantly,\n");
			bufferedWriter.write("# you don't need to Restart the Client to apply them\n");

			bufferedWriter.write(gson.toJson(data));

			bufferedWriter.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeDefaultConfig()
	{
		try
		{
			FileUtil.clearFile(configFile);

			FileWriter writer = new FileWriter(configFile);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);

			Map<String, String> data = new LinkedHashMap<String, String>();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			elements.forEach(configurationElement -> data.put(configurationElement.getName(), String.valueOf(configurationElement.getDefaultValue())));

			bufferedWriter.write("#This is a Config file from Tech 'X' Project for the Custom Config System \n");
			bufferedWriter.write("#Any change will be applied in-game instantly,\n");
			bufferedWriter.write("# you don't need to Restart the Client to apply them\n");

			bufferedWriter.write(gson.toJson(data));

			bufferedWriter.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

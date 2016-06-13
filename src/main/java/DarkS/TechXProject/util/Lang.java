package DarkS.TechXProject.util;

import net.minecraft.util.text.translation.I18n;

import java.util.IllegalFormatException;

public class Lang
{
	public static final String prefix = "techxproject.";

	public static String localize(String s, String... args)
	{
		return localize(s, true, args);
	}

	public static String localize(String s, boolean appenTP, String... args)
	{
		if (appenTP)
		{
			s = prefix + s;
		}

		String ret = I18n.translateToLocal(s);
		try
		{
			return String.format(ret, (Object[]) args);
		} catch (IllegalFormatException e)
		{
			return ret;
		}
	}

	public static String[] localizeList(String string)
	{
		String s = localize(string);
		return s.split("\\|");
	}
}


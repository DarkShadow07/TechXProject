package DrShadow.TechXProject.util;

import java.io.File;
import java.io.IOException;

public class FileUtil
{
	public static void clearFile(File file) throws IOException
	{
		file.delete();

		file.createNewFile();
	}
}

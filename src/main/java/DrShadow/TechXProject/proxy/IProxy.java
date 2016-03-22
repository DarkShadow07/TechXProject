package DrShadow.TechXProject.proxy;

import java.io.File;

public interface IProxy
{
	void preInit();

	void init();

	void postInit();

	void registerBlocks();

	void registerItems();

	void registerKeyBindings();

	void registerPacketHandler();

	void registerEvents();

	void registerConfiguration(File file);

	void registerThreads();
}

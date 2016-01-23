package com.DrShadow.TechXProject.proxy;

import java.io.File;

public interface IProxy
{
	void registerBlocks();
	void registerItems();
	void registerGuis();
	void registerKeyBindings();
	void registerPacketHandler();
	void registerEvents();
	void registerConfiguration(File file);
	void registerRenderers();
	void registerThreads();
}

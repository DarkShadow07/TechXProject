package com.DrShadow.TechXProject.init;

import com.DrShadow.TechXProject.blocks.BlockTest;
import com.DrShadow.TechXProject.core.CoreRegistry;
import com.DrShadow.TechXProject.lib.TechBlocks;
import com.DrShadow.TechXProject.reference.Reference;

public class InitBlocks
{
	private static CoreRegistry registry;

	public static void init()
	{
		initBlocks();
		registerTileEntities();

		registry = new CoreRegistry(Reference.MOD_ID);
	}

	public static void initBlocks()
	{
		TechBlocks.test = registry.registerBlock(new BlockTest().setUnlocalizedName("testBlock"));
	}

	public static void registerTileEntities()
	{

	}

	public static void initRenders()
	{
		registry.registerRender(TechBlocks.test);
	}
}

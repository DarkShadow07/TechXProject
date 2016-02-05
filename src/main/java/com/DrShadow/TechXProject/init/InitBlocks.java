package com.DrShadow.TechXProject.init;

import com.DarkLapis.DarkLapisCore.core.CoreRegistry;
import com.DrShadow.TechXProject.TechXProject;
import com.DrShadow.TechXProject.blocks.BlockTeslaTower;
import com.DrShadow.TechXProject.blocks.BlockTest;
import com.DrShadow.TechXProject.lib.TechBlocks;
import com.DrShadow.TechXProject.reference.Reference;
import com.DrShadow.TechXProject.tileEntities.TileTeslaTower;

public class InitBlocks
{
	private static CoreRegistry registry;

	public static void init()
	{
		initBlocks();
		registerTileEntities();
	}

	public static void initBlocks()
	{
		registry = new CoreRegistry(Reference.MOD_ID);

		TechBlocks.test = registry.registerBlock(new BlockTest(), "blockTest");
		TechBlocks.teslaTower = registry.registerBlock(new BlockTeslaTower(), "blockTeslaTower").setCreativeTab(TechXProject.techTab);
	}

	public static void registerTileEntities()
	{
		registry.registerTile(TileTeslaTower.class);
	}

	public static void initRenders()
	{
		registry.registerRender(TechBlocks.test);
		registry.registerRender(TechBlocks.teslaTower);
	}
}

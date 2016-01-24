package com.DrShadow.TechXProject.init;

import com.DrShadow.TechXProject.TechXProject;
import com.DrShadow.TechXProject.blocks.BlockBattery;
import com.DrShadow.TechXProject.blocks.BlockPowerTransmitter;
import com.DrShadow.TechXProject.blocks.BlockTest;
import com.DrShadow.TechXProject.lib.TechBlocks;
import com.DrShadow.TechXProject.reference.Reference;
import com.DrShadow.TechXProject.tileEntities.TileBattery;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitBlocks
{
	public static void init()
	{
		initBlocks();
		registerTileEntities();
	}

	public static void initBlocks()
	{
		TechBlocks.test = registerBlock(new BlockTest(), "testBlock");
		TechBlocks.batteryBasic = registerBlock(new BlockBattery(500000, 200), "basicBattery").setHardness(4.6F);
		TechBlocks.powerTransmitter = registerBlock(new BlockPowerTransmitter(5024, 100, 40), "powerTransmitter");
	}

	public static void registerTileEntities()
	{
		registerTile(TileBattery.class);
	}

	public static Block registerBlock(Block block, String name)
	{
		block.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ".block." + name);
		block.setCreativeTab(TechXProject.techTab);
		GameRegistry.registerBlock(block, name);
		return block;
	}

	public static void initRenders()
	{
		registerRender(TechBlocks.test);
	}

	public static void registerRender(Block block)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(24), "inventory"));
	}

	public static void registerTile(Class<? extends TileEntity> tileClass)
	{
		GameRegistry.registerTileEntity(tileClass, tileClass.getSimpleName());
	}
}

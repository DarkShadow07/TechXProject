package com.DrShadow.TechXProject.core;

import com.DrShadow.TechXProject.util.Helper;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CoreRegistry
{
	private String modId;

	public CoreRegistry(String modId)
	{
		this.modId = modId;
	}

	public Block registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, modId + ".block." + block.getUnlocalizedName());

		return block;
	}

	public Block registerBlock(Block block, Class<? extends ItemBlock> itemBlock)
	{
		GameRegistry.registerBlock(block, itemBlock, modId + ".block." + block.getUnlocalizedName());

		return block;
	}

	public Item registerItem(Item item)
	{
		GameRegistry.registerItem(item, modId + ".item." + item.getUnlocalizedName());

		return item;
	}

	public void registerTile(Class<? extends TileEntity> tileClass)
	{
		GameRegistry.registerTileEntity(tileClass, tileClass.getSimpleName());
	}

	public void registerRender(Block block)
	{
		Helper.minecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modId + ":" + block.getUnlocalizedName().lastIndexOf("."), "inventory"));
	}

	public void registerRender(Item item)
	{
		Helper.minecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modId + ":" + item.getUnlocalizedName().lastIndexOf("."), "inventory"));
	}
}

package DrShadow.TechXProject.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class InventoryRenderHelper
{
	private final String domain;

	public InventoryRenderHelper(String domain)
	{
		this.domain = domain.endsWith(":") ? domain.replace(":", "") : domain;
	}

	public void registerRender(Block block, int meta)
	{
		registerRender(getItemFromBlock(block), meta);
	}

	public void registerRender(Item item, int meta)
	{
		registerRender(item, meta, new ResourceLocation(domain, item.getRegistryName().getResourcePath()));
	}

	public void registerRender(Item item, int meta, String name)
	{
		registerRender(item, meta, new ResourceLocation(domain, name));
	}

	public void registerRender(Item item, int meta, ResourceLocation location)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
	}

	public Item getItemFromBlock(Block block)
	{
		return Item.getItemFromBlock(block);
	}
}

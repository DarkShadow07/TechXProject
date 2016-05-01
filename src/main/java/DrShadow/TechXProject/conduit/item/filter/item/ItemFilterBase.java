package DrShadow.TechXProject.conduit.item.filter.item;

import DrShadow.TechXProject.conduit.item.filter.EnumFilterType;
import DrShadow.TechXProject.conduit.item.filter.IItemStackFilter;
import DrShadow.TechXProject.items.ItemBase;
import DrShadow.TechXProject.items.inventory.ItemInventory;
import DrShadow.TechXProject.util.GhostItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemFilterBase extends ItemBase implements IItemStackFilter
{
	private List<ItemStack> filteredItems = new ArrayList<>();

	public ItemFilterBase()
	{
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		for (ItemStack filterStack : getFilteredItems(stack))
		{
			tooltip.add(filterStack.getDisplayName());
		}
	}

	@Override
	public List<ItemStack> getFilteredItems(ItemStack stack)
	{
		filteredItems.clear();

		ItemInventory itemInventory = new ItemInventory(stack, 8, "");

		for (int i = 0; i < itemInventory.getSizeInventory(); i++)
		{
			ItemStack ghostStack = itemInventory.getStackInSlot(i);

			if (ghostStack == null || filteredItems.contains(GhostItemUtil.getStackFromGhost(ghostStack).getItem()))
				continue;

			filteredItems.add(GhostItemUtil.getStackFromGhost(ghostStack));
		}

		return filteredItems;
	}

	@Override
	public void addToFilter(ItemStack item)
	{
		filteredItems.add(item);
	}

	@Override
	public EnumFilterType getFilterType()
	{
		return EnumFilterType.BASIC;
	}

	@Override
	public int getStackSize(ItemStack stack, ItemStack filterStack)
	{
		int size = 0;

		ItemInventory itemInventory = new ItemInventory(filterStack, 8, "");

		for (int i = 0; i < itemInventory.getSizeInventory(); i++)
		{
			ItemStack ghostStack = itemInventory.getStackInSlot(i);

			if (ghostStack != null && ghostStack.isItemEqual(stack))
			{
				size = GhostItemUtil.getItemGhostAmount(ghostStack);
			}
		}

		return size;
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}
}

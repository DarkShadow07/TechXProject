package DrShadow.TechXProject.conduit.item.filter.item;

import DrShadow.TechXProject.conduit.item.filter.EnumFilterType;
import DrShadow.TechXProject.conduit.item.filter.IItemStackFilter;
import DrShadow.TechXProject.items.ItemBase;
import DrShadow.TechXProject.items.inventory.ItemInventory;
import DrShadow.TechXProject.util.GhostItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ItemFilterOreDict extends ItemBase implements IItemStackFilter
{
	private List<ItemStack> filteredItems = new ArrayList<>();

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		for (ItemStack filterStack : getFilteredItems(stack))
		{
			int[] oreIds = OreDictionary.getOreIDs(filterStack);

			for (int id : oreIds)
			{
				String ore = OreDictionary.getOreName(id);

				if (!tooltip.contains(ore)) tooltip.add(ore);
			}
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

			if (ghostStack == null || filteredItems.contains(GhostItemHelper.getStackFromGhost(ghostStack).getItem()))
				continue;

			filteredItems.add(GhostItemHelper.getStackFromGhost(ghostStack));
		}

		return filteredItems;
	}

	@Override
	public void addToFilter(ItemStack stack)
	{
		filteredItems.add(stack);
	}

	@Override
	public EnumFilterType getFilterType()
	{
		return EnumFilterType.ORE_DICTIONARY;
	}

	public boolean isValid(ItemStack stack)
	{
		boolean valid = false;

		for (ItemStack filterStack : filteredItems)
		{
			int[] filterIds = OreDictionary.getOreIDs(filterStack);
			int[] stackIds = OreDictionary.getOreIDs(stack);

			if (filterIds.length <= 0 || stackIds.length <= 0) valid = false;

			for (int filterId : filterIds)
			{
				for (int stackId : stackIds)
				{
					if (filterId == stackId)
					{
						valid = true;
					}
				}
			}
		}

		return valid;
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
				size = GhostItemHelper.getItemGhostAmount(ghostStack);
			}
		}

		return size;
	}

}

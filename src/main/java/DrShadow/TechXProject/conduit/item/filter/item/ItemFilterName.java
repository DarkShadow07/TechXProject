package DrShadow.TechXProject.conduit.item.filter.item;

import DrShadow.TechXProject.conduit.item.filter.EnumFilterType;
import DrShadow.TechXProject.conduit.item.filter.IItemStackFilter;
import DrShadow.TechXProject.items.ItemBase;
import DrShadow.TechXProject.items.inventory.ItemInventory;
import DrShadow.TechXProject.util.GhostItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemFilterName extends ItemBase implements IItemStackFilter
{
	private List<ItemStack> filteredItems = new ArrayList<>();

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
		return EnumFilterType.NAME;
	}

	public boolean isValid(ItemStack stack)
	{
		boolean valid = false;

		for (ItemStack filterStack : filteredItems)
		{
			String[] filterNames = filterStack.getDisplayName().split("\\W");
			String[] stackNames = stack.getDisplayName().split("\\W");

			for (String filterName : filterNames)
			{
				for (String stackName : stackNames)
				{
					if (filterName.equalsIgnoreCase(stackName))
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

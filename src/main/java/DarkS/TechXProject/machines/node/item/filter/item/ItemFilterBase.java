package DarkS.TechXProject.machines.node.item.filter.item;

import DarkS.TechXProject.items.ItemBase;
import DarkS.TechXProject.items.inventory.ItemInventory;
import DarkS.TechXProject.machines.node.item.filter.EnumFilterType;
import DarkS.TechXProject.machines.node.item.filter.IItemStackFilter;
import DarkS.TechXProject.util.GhostItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		tooltip.add("Filtered Items: ");

		tooltip.addAll(getFilteredItems(stack).stream().map(filterStack -> " " + filterStack.getDisplayName()).collect(Collectors.toList()));
	}

	@Override
	public List<ItemStack> getFilteredItems(ItemStack stack)
	{
		filteredItems.clear();

		ItemInventory itemInventory = new ItemInventory(stack, 8, "");

		for (int i = 0; i < itemInventory.getSizeInventory(); i++)
		{
			ItemStack ghostStack = GhostItemUtil.getStackFromGhost(itemInventory.getStackInSlot(i));

			if (ghostStack == null || filteredItems.contains(ghostStack.getItem()))
				continue;

			if (GhostItemUtil.getStackFromGhost(ghostStack).getItem() instanceof IItemStackFilter)
			{
				filteredItems.addAll(((IItemStackFilter) GhostItemUtil.getStackFromGhost(ghostStack).getItem()).getFilteredItems(GhostItemUtil.getStackFromGhost(ghostStack)));
			}

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

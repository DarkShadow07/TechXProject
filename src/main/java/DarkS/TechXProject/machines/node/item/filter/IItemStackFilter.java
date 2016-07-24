package DarkS.TechXProject.machines.node.item.filter;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IItemStackFilter
{
	List<ItemStack> getFilteredItems(ItemStack stack);

	void addToFilter(ItemStack stack);

	EnumFilterType getFilterType();

	int getStackSize(ItemStack stack, ItemStack filterStack);
}

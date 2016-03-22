package DrShadow.TechXProject.conduit.item.filter;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IFilterElement
{
	List<ItemStack> getFilteredStacks();

	IItemStackFilter getFilter();

	void setFilter(IItemStackFilter filter);

	EnumFilterType getType();

	boolean hasFilter();

	boolean isInFilter(ItemStack stack);
}

package DrShadow.TechXProject.conduit.item.filter.item;

import DrShadow.TechXProject.conduit.item.ItemConduitUtil;
import DrShadow.TechXProject.conduit.item.filter.EnumFilterType;
import DrShadow.TechXProject.conduit.item.filter.IItemStackFilter;
import DrShadow.TechXProject.items.ItemBase;
import DrShadow.TechXProject.items.inventory.ItemInventory;
import DrShadow.TechXProject.util.GhostItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemFilterMod extends ItemBase implements IItemStackFilter
{
	private List<ItemStack> filteredItems = new ArrayList<>();

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		for (ItemStack filterStack : getFilteredItems(stack))
		{
			String mod = ItemConduitUtil.getModID(filterStack.getItem());

			if (!tooltip.contains(mod)) tooltip.add(mod);
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
	public void addToFilter(ItemStack stack)
	{
		filteredItems.add(stack);
	}

	@Override
	public EnumFilterType getFilterType()
	{
		return EnumFilterType.MOD;
	}

	public boolean isValid(ItemStack stack)
	{
		boolean valid = false;

		for (ItemStack filterStack : filteredItems)
		{
			valid = ItemConduitUtil.isSameMod(stack, filterStack);
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
				size = GhostItemUtil.getItemGhostAmount(ghostStack);
			}
		}

		return size;
	}

}

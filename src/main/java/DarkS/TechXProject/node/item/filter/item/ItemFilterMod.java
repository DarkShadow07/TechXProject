package DarkS.TechXProject.node.item.filter.item;

import DarkS.TechXProject.items.ItemBase;
import DarkS.TechXProject.items.inventory.ItemInventory;
import DarkS.TechXProject.node.item.NodeUtil;
import DarkS.TechXProject.node.item.filter.EnumFilterType;
import DarkS.TechXProject.node.item.filter.IItemStackFilter;
import DarkS.TechXProject.util.GhostItemUtil;
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
			String mod = NodeUtil.getModID(filterStack.getItem());

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
			valid = NodeUtil.isSameMod(stack, filterStack);
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

package DrShadow.TechXProject.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class ItemHelper
{
	public static ItemStack cloneItemStack(ItemStack itemStack, int stackSize)
	{
		ItemStack clonedItemStack = itemStack.copy();
		clonedItemStack.stackSize = stackSize;
		return clonedItemStack;
	}


	public static boolean equals(ItemStack first, ItemStack second)
	{
		return (first.equals(second));
	}

	public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2)
	{
		if (itemStack1 != null && itemStack2 != null)
		{
			if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0)
			{
				if (itemStack1.getItem() == itemStack2.getItem())
				{
					if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
					{
						if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
						{
							if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
							{
								return true;
							}
						} else if (!itemStack1.hasTagCompound() && !itemStack2.hasTagCompound())
						{
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public static String toString(ItemStack itemStack)
	{
		if (itemStack != null)
		{
			if (itemStack.hasTagCompound())
			{
				return String.format("%sxitemStack[%s@%s:%s]", itemStack.stackSize, itemStack.getUnlocalizedName(), itemStack.getItemDamage(), itemStack.getTagCompound());
			} else
			{
				return String.format("%sxitemStack[%s@%s]", itemStack.stackSize, itemStack.getUnlocalizedName(), itemStack.getItemDamage());
			}
		}

		return "null";
	}
}

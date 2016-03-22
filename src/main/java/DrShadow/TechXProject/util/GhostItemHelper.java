package DrShadow.TechXProject.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GhostItemHelper
{
	public static void setItemGhostAmount(ItemStack stack, int amount)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		NBTTagCompound tag = stack.getTagCompound();

		tag.setInteger("Size", amount);
	}

	public static int getItemGhostAmount(ItemStack stack)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		NBTTagCompound tag = stack.getTagCompound();

		return tag.getInteger("Size");
	}

	public static ItemStack getStackFromGhost(ItemStack ghostStack)
	{
		ItemStack newStack = ghostStack.copy();

		if (newStack.getTagCompound() == null) newStack.setTagCompound(new NBTTagCompound());
		NBTTagCompound tag = newStack.getTagCompound();
		int amount = getItemGhostAmount(ghostStack);
		tag.removeTag("Size");
		if (tag.hasNoTags())
		{
			newStack.setTagCompound(null);
		}
		newStack.stackSize = amount;

		return newStack;
	}
}

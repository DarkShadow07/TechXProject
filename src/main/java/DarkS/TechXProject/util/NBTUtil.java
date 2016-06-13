package DarkS.TechXProject.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil
{
	public static ItemStack checkNBT(ItemStack stack)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		return stack;
	}
}

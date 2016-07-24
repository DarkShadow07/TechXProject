package DarkS.TechXProject.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemProcessingMatrix extends ItemBase
{
	public ItemProcessingMatrix()
	{
		setMaxStackSize(4);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}

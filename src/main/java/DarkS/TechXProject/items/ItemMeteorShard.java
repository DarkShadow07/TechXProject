package DarkS.TechXProject.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemMeteorShard extends ItemBase
{
	public ItemMeteorShard()
	{

	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}

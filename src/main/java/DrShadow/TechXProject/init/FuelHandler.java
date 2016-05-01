package DrShadow.TechXProject.init;

import DrShadow.TechXProject.blocks.metal.EnumMetals;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if (fuel.isItemEqual(new ItemStack(InitItems.dust.item, 1, EnumMetals.COAL.ordinal()))) return 400;

		return 0;
	}
}

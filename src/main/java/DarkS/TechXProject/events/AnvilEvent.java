package DarkS.TechXProject.events;

import DarkS.TechXProject.init.InitItems;
import DarkS.TechXProject.node.item.filter.IItemStackFilter;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnvilEvent
{
	@SubscribeEvent
	public void updateAnvil(AnvilUpdateEvent event)
	{
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();

		if (left == null || right == null) return;

		if (left.getItem() instanceof IItemStackFilter)
		{
			handleFilterRecipe(event);
		}
	}

	public void handleFilterRecipe(AnvilUpdateEvent event)
	{
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();

		if (left.getItem().equals(InitItems.filterBase.item) && right.getItem().equals(InitItems.wrench.item))
		{
			event.setCost(5);
			event.setOutput(new ItemStack(InitItems.filterMod.item));
		} else if (left.getItem().equals(InitItems.filterBase.item) && right.getItem().equals(Items.NAME_TAG))
		{
			event.setCost(7);
			event.setOutput(new ItemStack(InitItems.filterName.item));
		} else if (left.getItem().equals(InitItems.filterBase.item) && right.getItem().equals(Items.WRITABLE_BOOK))
		{
			event.setCost(10);
			event.setOutput(new ItemStack(InitItems.filterOreDict.item));
		}
	}
}

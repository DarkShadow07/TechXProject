package DrShadow.TechXProject;

import DrShadow.TechXProject.blocks.metal.EnumMetalType;
import DrShadow.TechXProject.blocks.metal.EnumMetals;
import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.init.InitItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreativeTabTech extends CreativeTabs
{
	public CreativeTabTech(String label)
	{
		super(label);
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(InitBlocks.networkController.block);
	}

	public static class CreativeTabOres extends CreativeTabs
	{
		private List<ItemStack> displayStacks = new ArrayList<>();

		public CreativeTabOres(String label)
		{
			super(label);

			addItemStacks();
		}

		private void addItemStacks()
		{
			for (EnumMetals metal : EnumMetals.values())
			{
				if (metal.contains(EnumMetalType.ORE))
				{
					displayStacks.add(new ItemStack(InitBlocks.metalOre.block, 1, metal.ordinal()));
				}

				if (metal.contains(EnumMetalType.BLOCK))
				{
					displayStacks.add(new ItemStack(InitBlocks.metalBlock.block, 1, metal.ordinal()));
				}

				if (metal.contains(EnumMetalType.INGOT))
				{
					displayStacks.add(new ItemStack(InitItems.ingot.item, 1, metal.ordinal()));
				}
			}
		}


		@Override
		public Item getTabIconItem()
		{
			Random r = new Random();

			return displayStacks.get(r.nextInt(displayStacks.size() - 1)).getItem();
		}
	}
}

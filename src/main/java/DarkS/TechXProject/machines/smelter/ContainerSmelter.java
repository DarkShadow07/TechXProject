package DarkS.TechXProject.machines.smelter;

import DarkS.TechXProject.compat.jei.smelter.SmelterRecipeHandler;
import DarkS.TechXProject.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerSmelter extends Container
{
	public ContainerSmelter(InventoryPlayer inventoryPlayer, TileSmelter inventory)
	{
		addSlotToContainer(new SlotInput(inventory, 0, 57, 18));
		addSlotToContainer(new SlotInput(inventory, 1, 81, 8));
		addSlotToContainer(new SlotInput(inventory, 2, 105, 18));
		addSlotToContainer(new SlotSmelter(inventory, 3, 135, 52));

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index > 2)
			{
				if (SmelterRecipeHandler.instance.isValidStack(itemstack1))
					if (!this.mergeItemStack(itemstack1, 0, 3, false))
						return null;

				slot.onSlotChange(itemstack1, itemstack);
			} else if (!this.mergeItemStack(itemstack1, 4, 36, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	public static class SlotSmelter extends Slot
	{
		public SlotSmelter(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
		{
			if (!playerIn.worldObj.isRemote)
				Util.giveExperience(playerIn, SmelterRecipeHandler.instance.getSmeltingXP(stack));

			if (stack.getItem() == Items.IRON_INGOT)
				playerIn.addStat(AchievementList.ACQUIRE_IRON, 1);

			if (stack.getItem() == Items.COOKED_FISH)
				playerIn.addStat(AchievementList.COOK_FISH, 1);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return false;
		}
	}

	public static class SlotInput extends Slot
	{
		TileSmelter smelter;

		public SlotInput(TileSmelter inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);

			smelter = inventoryIn;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			ItemStack[] inventory = smelter.inventory;
			ItemStack[] notNullInventory = Util.getStackArrayNoNull(new ItemStack[]{inventory[0], inventory[1], inventory[2]});

			if (notNullInventory.length == 0)
			{
				return SmelterRecipeHandler.instance.isValidStack(stack);
			} else
			{
				for (ItemStack invStack : notNullInventory)
				{
					if (SmelterRecipeHandler.instance.isVanillaRecipe(invStack))
					{
						return OreDictionary.itemMatches(invStack, stack, true);
					} else
					{
						return SmelterRecipeHandler.instance.isValidStackNoVanilla(stack);
					}
				}
			}

			return false;
		}
	}
}

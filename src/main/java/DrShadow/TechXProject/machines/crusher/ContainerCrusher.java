package DrShadow.TechXProject.machines.crusher;

import DrShadow.TechXProject.compat.jei.crusher.CrusherRecipeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrusher extends Container
{
	public ContainerCrusher(InventoryPlayer inventoryPlayer, TileCrusher inventory)
	{
		addSlotToContainer(new SlotInput(inventory, 0, 81, 8));

		addSlotToContainer(new SlotOutput(inventory, 1, 51, 48));
		addSlotToContainer(new SlotOutput(inventory, 2, 71, 48));
		addSlotToContainer(new SlotOutput(inventory, 3, 91, 48));
		addSlotToContainer(new SlotOutput(inventory, 4, 111, 48));

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

			if (index > 4)
			{
				if (CrusherRecipeHandler.instance.isValidStack(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, true))
					{
						return null;
					}
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index > 0 && index <= 4)
			{
				if (!this.mergeItemStack(itemstack1, 5, 4 + 36, false))
				{
					return null;
				}
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

	public static class SlotInput extends Slot
	{
		TileCrusher crusher;

		public SlotInput(TileCrusher inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);

			crusher = inventoryIn;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return CrusherRecipeHandler.instance.isValidStack(stack);
		}
	}

	public static class SlotOutput extends Slot
	{
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return false;
		}
	}
}

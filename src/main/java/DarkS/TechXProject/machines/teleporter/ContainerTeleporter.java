package DarkS.TechXProject.machines.teleporter;

import DarkS.TechXProject.items.ItemTeleporterCard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTeleporter extends Container
{
	public ContainerTeleporter(InventoryPlayer inventoryPlayer, IInventory inventory)
	{
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			addSlotToContainer(new SlotCard(inventory, i, 8 + 18 * i, 18));
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 149 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 207));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
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

			if (index < 9)
			{
				if (!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 9, false))
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
		}

		return itemstack;
	}

	public static class SlotCard extends Slot
	{
		public SlotCard(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return stack.getItem() instanceof ItemTeleporterCard;
		}
	}
}

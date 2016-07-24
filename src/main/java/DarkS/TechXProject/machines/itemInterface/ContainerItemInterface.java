package DarkS.TechXProject.machines.itemInterface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerItemInterface extends Container
{
	public ContainerItemInterface(InventoryPlayer inventoryPlayer, TileItemInterface tile)
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				//addSlotToContainer(new Slot(tile, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}


		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 162 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 220));
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

			if (index <= 54)
			{
				if (!this.mergeItemStack(itemstack1, 54, this.inventorySlots.size(), false))
					return null;
			} else if (!this.mergeItemStack(itemstack1, 54, 54, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}

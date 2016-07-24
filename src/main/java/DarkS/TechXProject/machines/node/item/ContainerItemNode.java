package DarkS.TechXProject.machines.node.item;

import DarkS.TechXProject.items.inventory.ItemInventory;
import DarkS.TechXProject.machines.node.item.filter.IItemStackFilter;
import DarkS.TechXProject.util.GhostItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerItemNode extends Container
{
	private IInventory inventory;
	private ItemInventory itemInventory;
	private ItemStack filter;

	public ContainerItemNode(InventoryPlayer inventoryPlayer, IInventory inventory)
	{
		if (inventory == null) return;

		this.inventory = inventory;

		addSlotToContainer(new SlotItemFilter(this, inventory, 0, 8, 32));

		filter = inventory.getStackInSlot(0);
		itemInventory = new ItemInventory(filter, 8, "");

		itemInventory.initializeInventory(filter);

		for (int i = 0; i < 8; i++)
			addSlotToContainer(new SlotGhostItem(itemInventory, i, 30 + i * 18, 32));


		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 10 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 10 + i * 18, 142));
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType mode, EntityPlayer player)
	{
		if (!(inventory instanceof TileItemNode)) return super.slotClick(slotId, dragType, mode, player);

		InventoryPlayer inventoryPlayer = player.inventory;
		{
			if (slotId >= 0)
			{
				Slot slot = this.inventorySlots.get(slotId);

				if (slot instanceof SlotGhostItem)
				{
					if (dragType == 0 || dragType == 1)
					{
						ItemStack slotStack = slot.getStack();
						ItemStack heldStack = inventoryPlayer.getItemStack();

						if (dragType == 0)
						{
							if (heldStack != null && inventory.getStackInSlot(0) != null)
							{
								ItemStack copyStack = heldStack.copy();
								GhostItemUtil.setItemGhostAmount(copyStack, 0);
								copyStack.stackSize = 1;
								slot.putStack(copyStack);

								itemInventory.writeToStack(inventory.getStackInSlot(0));
							} else
							{
								if (slotStack != null) slot.putStack(null);

								itemInventory.writeToStack(inventory.getStackInSlot(0));
							}
						}
					}
				}
			}
		}
		return super.slotClick(slotId, dragType, mode, player);
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

			if (index == 0)
			{
				if (!this.mergeItemStack(itemstack1, 9, 1 + 36, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index > 0)
			{
				if (itemstack1.getItem() instanceof IItemStackFilter)
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}
				}
			} else if (!this.mergeItemStack(itemstack1, 9, 1 + 36, false))
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

	public void updateItemFilter(ItemStack filterStack)
	{
		itemInventory.clear();

		itemInventory.readFromStack(filterStack);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	private class SlotItemFilter extends Slot
	{
		public ContainerItemNode container;

		public SlotItemFilter(ContainerItemNode container, IInventory inventory, int slotIndex, int x, int y)
		{
			super(inventory, slotIndex, x, y);
			this.container = container;
		}

		@Override
		public boolean isItemValid(ItemStack itemStack)
		{
			return itemStack.getItem() instanceof IItemStackFilter;
		}

		@Override
		public int getSlotStackLimit()
		{
			return 1;
		}

		@Override
		public void onSlotChanged()
		{
			super.onSlotChanged();

			container.updateItemFilter(getStack());
		}
	}

	public class SlotGhostItem extends Slot
	{
		public SlotGhostItem(ItemInventory inventory, int slotIndex, int x, int y)
		{
			super(inventory, slotIndex, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return false;
		}

		@Override
		public boolean canTakeStack(EntityPlayer playerIn)
		{
			return false;
		}
	}
}

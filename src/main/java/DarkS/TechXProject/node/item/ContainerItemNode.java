package DarkS.TechXProject.node.item;

import DarkS.TechXProject.items.inventory.ItemInventory;
import DarkS.TechXProject.node.item.filter.IItemStackFilter;
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
		this.inventory = inventory;

		filter = inventory.getStackInSlot(0);
		itemInventory = new ItemInventory(filter, 8, "");

		itemInventory.initializeInventory(filter);

		addSlotToContainer(new SlotItemFilter(this, inventory, 0, 8, 32));

		if (inventory.getStackInSlot(0) != null)
		{
			for (int i = 0; i < 2; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					addSlotToContainer(new SlotGhostItem(itemInventory, j + i * 4, 30 + j * 18, 32 + i * 20));
				}
			}
		}

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
	public ItemStack slotClick(int slotId, int dragType, ClickType mode, EntityPlayer player)
	{
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
							if (heldStack != null)
							{
								if (!((SlotGhostItem) slot).canBeAccessed())
								{
									return super.slotClick(slotId, dragType, mode, player);
								} else
								{
									ItemStack copyStack = heldStack.copy();
									GhostItemUtil.setItemGhostAmount(copyStack, 0);
									copyStack.stackSize = 1;
									slot.putStack(copyStack);
								}
							} else
							{
								if (slotStack != null) slot.putStack(null);
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
	}

	public class SlotGhostItem extends Slot
	{
		private ItemInventory itemInv;

		public SlotGhostItem(ItemInventory inventory, int slotIndex, int x, int y)
		{
			super(inventory, slotIndex, x, y);
			itemInv = inventory;
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

		public boolean canBeAccessed()
		{
			return itemInv.canInventoryBeManipulated();
		}

		@Override
		public void onSlotChanged()
		{
			super.onSlotChanged();
		}
	}
}

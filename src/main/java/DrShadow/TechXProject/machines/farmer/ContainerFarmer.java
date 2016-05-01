package DrShadow.TechXProject.machines.farmer;

import DrShadow.TechXProject.machines.crusher.ContainerCrusher;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;

public class ContainerFarmer extends Container
{
	public ContainerFarmer(InventoryPlayer inventoryPlayer, TileFarmer inventory)
	{
		addSlotToContainer(new SlotAxe(inventory, 0, 27, 18));
		addSlotToContainer(new ContainerCrusher.SlotOutput(inventory, 1, 45, 18));
		addSlotToContainer(new SlotPlantable(inventory, 2, 27, 40));
		addSlotToContainer(new SlotPlantable(inventory, 3, 45, 40));
		addSlotToContainer(new SlotPlantable(inventory, 4, 27, 58));
		addSlotToContainer(new SlotPlantable(inventory, 5, 45, 58));
		addSlotToContainer(new SlotFertiliser(inventory, 6, 67, 18));
		addSlotToContainer(new SlotFertiliser(inventory, 7, 85, 18));
		addSlotToContainer(new ContainerCrusher.SlotOutput(inventory, 8, 67, 40));
		addSlotToContainer(new ContainerCrusher.SlotOutput(inventory, 9, 85, 40));
		addSlotToContainer(new ContainerCrusher.SlotOutput(inventory, 10, 67, 58));
		addSlotToContainer(new ContainerCrusher.SlotOutput(inventory, 11, 85, 58));

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 90 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 148));
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

			if (index <= 11)
			{
				if (!this.mergeItemStack(itemstack1, 11, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 11, false))
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

	public static class SlotFertiliser extends Slot
	{

		public SlotFertiliser(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return stack.isItemEqual(new ItemStack(Items.dye, 1, 15));
		}
	}

	public static class SlotAxe extends Slot
	{

		public SlotAxe(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return stack.getItem().isItemTool(stack);
		}
	}

	public static class SlotPlantable extends Slot
	{

		public SlotPlantable(IInventory inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			if (stack.getItem() instanceof ItemBlock)
			{
				if (((ItemBlock) stack.getItem()).getBlock() instanceof IGrowable)
				{
					return true;
				}
			} else if (stack.getItem() instanceof IPlantable)
			{
				return true;
			}

			return false;
		}
	}
}

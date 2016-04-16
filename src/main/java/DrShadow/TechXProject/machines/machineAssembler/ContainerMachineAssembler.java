package DrShadow.TechXProject.machines.machineAssembler;

import DrShadow.TechXProject.machines.crusher.ContainerCrusher;
import DrShadow.TechXProject.machines.recipeStamper.ContainerRecipeStamper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineAssembler extends Container
{
	public ContainerMachineAssembler(InventoryPlayer inventoryPlayer, TileMachineAssembler assembler)
	{
		addSlotToContainer(new ContainerRecipeStamper.SlotStamp(assembler, 0, 8, 18));
		addSlotToContainer(new ContainerCrusher.SlotOutput(assembler, 1, 152, 18));

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new SlotAssembler(assembler, j + i * 9 + 2, 8 + j * 18, 40 + i * 18));
			}
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 166));
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

			if (index < 28)
			{
				if (!this.mergeItemStack(itemstack1, 28, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 28, false))
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

	public static class SlotAssembler extends Slot
	{
		private TileMachineAssembler assembler;

		public SlotAssembler(TileMachineAssembler inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);

			assembler = inventoryIn;
		}

		@Override
		public int getSlotStackLimit()
		{
			return 64;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return assembler.isItemValidForSlot(getSlotIndex(), stack);
		}
	}
}

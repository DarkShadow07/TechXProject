package DrShadow.TechXProject.machines.machineAssembler;

import DrShadow.TechXProject.items.ItemMachineRecipe;
import DrShadow.TechXProject.machines.crusher.ContainerCrusher;
import DrShadow.TechXProject.machines.recipeStamper.ContainerRecipeStamper;
import DrShadow.TechXProject.machines.recipeStamper.MachineRecipeType;
import DrShadow.TechXProject.util.Logger;
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
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	public static class SlotAssembler extends Slot
	{
		private TileMachineAssembler assembler;

		private MachineRecipeType type;

		public SlotAssembler(TileMachineAssembler inventoryIn, int index, int xPosition, int yPosition)
		{
			super(inventoryIn, index, xPosition, yPosition);

			assembler = inventoryIn;

			if (assembler.getStackInSlot(0) != null)
			{
				type = ((ItemMachineRecipe) assembler.getStackInSlot(0).getItem()).getType(assembler.getStackInSlot(0));
			}else type = MachineRecipeType.CLEAR;
		}

		@Override
		public boolean canBeHovered()
		{
			return true;
		}

		@Override
		public int getSlotStackLimit()
		{
			return type.inputs[getSlotIndex() - 2].stackSize;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return type.inputs.length > getSlotIndex() - 2 && type.inputs[getSlotIndex() - 2].isItemEqual(stack);
		}
	}
}

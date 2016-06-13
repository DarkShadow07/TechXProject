package DarkS.TechXProject.api.energy.item;

import net.minecraft.item.ItemStack;

public interface IEnergyItem
{
	/**
	 * Returns the Energy of the given ItemStack
	 *
	 * @param stack ItemStack to read the Energy Level
	 * @return Energy of the stack
	 */
	int getEnergy(ItemStack stack);

	/**
	 * Returns the Max Energy of the given ItemStack
	 *
	 * @param stack ItemStack to read the Max Energy Level
	 * @return Max Energy of the stack
	 */
	int getMaxEnergy(ItemStack stack);

	/**
	 * Sets the Max Transfer of the given ItemStack
	 *
	 * @param stack    ItemStack to set the Max Transfer
	 * @param transfer Energy to set as Max Transfer of stack
	 */
	void setMaxTransfer(ItemStack stack, int transfer);

	int getMaxTransfer(ItemStack stack);

	/**
	 * Sets the Energy of the given ItemStack
	 *
	 * @param stack  ItemStack to set the Energy Level
	 * @param energy Energy to set to the stack
	 */
	void setEnergy(ItemStack stack, int energy);

	/**
	 * Adds Energy to the given ItemStack
	 *
	 * @param stack  ItemStack to add the Energy to
	 * @param energy Energy to add to the stack
	 * @param test   If the Energy is actually added to the stack or is just testing
	 * @return Energy Added
	 */
	int addEnergy(ItemStack stack, int energy, boolean test);

	/**
	 * Subtracts Energy from the given ItemStack
	 *
	 * @param stack  ItemStack to subtract the Energy from
	 * @param energy Energy to subtract from the stack
	 * @param test   If the Energy is actually subtracted from the stack or is just testing
	 * @return If it can subtract the Energy from the stack
	 */
	boolean subtractEnergy(ItemStack stack, int energy, boolean test);
}

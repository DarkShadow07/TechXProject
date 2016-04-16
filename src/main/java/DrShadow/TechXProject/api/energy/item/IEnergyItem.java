package DrShadow.TechXProject.api.energy.item;

import net.minecraft.item.ItemStack;

public interface IEnergyItem
{
	int getEnergy(ItemStack stack);

	void setEnergy(ItemStack stack, int energy);

	int addEnergy(ItemStack stack, int energy, boolean test);

	boolean subtractEnergy(ItemStack stack, int energy, boolean test);

	int getMaxEnergy();

	void setMaxTransfer(ItemStack stack, int transfer);

	int getMaxTransfer(ItemStack stack);
}

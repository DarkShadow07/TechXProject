package DrShadow.TechXProject.api.energy.item;

import DrShadow.TechXProject.items.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemEnergyContainer extends ItemBase implements IEnergyItem
{
	public static final String ENERGY_NBT = "energy";

	protected int energy, maxEnergy, maxTransfer;

	public ItemEnergyContainer(int energy, int maxEnergy, int maxTransfer)
	{
		this.energy = energy;
		this.maxEnergy = maxEnergy;
		this.maxTransfer = maxTransfer;
	}

	@Override
	public int getEnergy(ItemStack stack)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		energy = stack.getTagCompound().getInteger(ENERGY_NBT);

		return energy;
	}

	@Override
	public void setEnergy(ItemStack stack, int energy)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		this.energy = energy;
		stack.getTagCompound().setInteger(ENERGY_NBT, energy);
	}

	@Override
	public int addEnergy(ItemStack stack, int energy, boolean test)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		int transfer = Math.min(energy, getMaxTransfer(stack));
		int lastEnergy = energy;

		lastEnergy += transfer;

		if (!test)
		{
			setEnergy(stack, getEnergy(stack) + energy);

			if (energy < 0) setEnergy(stack, 0);

			if (getEnergy(stack) > getMaxEnergy())
			{
				setEnergy(stack, getMaxEnergy());
			}
		}

		if (lastEnergy > getMaxEnergy())
		{
			return transfer - (lastEnergy - getMaxEnergy());
		}

		return transfer;
	}

	@Override
	public boolean subtractEnergy(ItemStack stack, int energy, boolean test)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		if (getEnergy(stack) >= energy)
		{
			if (!test)
			{
				setEnergy(stack, getEnergy(stack) - energy);

				if (energy < 0) setEnergy(stack, 0);
				if (energy > getMaxEnergy()) setEnergy(stack, getMaxEnergy());
			}

			return true;
		}

		return false;
	}

	@Override
	public int getMaxEnergy()
	{
		return maxEnergy;
	}

	@Override
	public void setMaxTransfer(ItemStack stack, int transfer)
	{
		this.maxTransfer = transfer;
	}

	@Override
	public int getMaxTransfer(ItemStack stack)
	{
		return maxTransfer;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return getDurabilityForDisplay(stack) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		int maxStored = getMaxEnergy();
		double stored = maxStored - getEnergy(stack);
		double max = maxStored;

		return stored / max;
	}
}

package DarkS.TechXProject.items.energy;

import DarkS.TechXProject.api.energy.item.IEnergyItem;
import DarkS.TechXProject.items.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemEnergyContainer extends ItemBase implements IEnergyItem
{
	public static final String ENERGY_NBT = "generator";
	public static final String MAX_ENERGY_NBT = "maxEnergy";

	protected int energy, maxEnergy, maxTransfer;

	public ItemEnergyContainer(int maxEnergy, int maxTransfer)
	{
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
	public int getMaxEnergy(ItemStack stack)
	{
		if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

		energy = stack.getTagCompound().getInteger(MAX_ENERGY_NBT);

		return energy == 0 ? maxEnergy : energy;
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
		int lastEnergy = getEnergy(stack);

		lastEnergy += transfer;

		if (!test)
		{
			setEnergy(stack, getEnergy(stack) + energy);

			if (energy < 0) setEnergy(stack, 0);

			if (getEnergy(stack) > getMaxEnergy(stack))
			{
				setEnergy(stack, getMaxEnergy(stack));
			}
		}

		if (lastEnergy > getMaxEnergy(stack))
		{
			return transfer - (lastEnergy - getMaxEnergy(stack));
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
				if (energy > getMaxEnergy(stack)) setEnergy(stack, getMaxEnergy(stack));
			}

			return true;
		}

		return false;
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
		int maxStored = getMaxEnergy(stack);
		double stored = maxStored - getEnergy(stack);
		double max = maxStored;

		return stored / max;
	}
}

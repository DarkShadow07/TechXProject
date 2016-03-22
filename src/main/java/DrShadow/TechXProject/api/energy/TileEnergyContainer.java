package DrShadow.TechXProject.api.energy;

import DrShadow.TechXProject.tileEntities.ModTileEntity;
import DrShadow.TechXProject.util.Helper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class TileEnergyContainer extends ModTileEntity implements IEnergyContainer
{
	public static final String ENERGY_NBT = "energy";
	protected List<EnumFacing> inputSides;
	protected List<EnumFacing> outputSides;
	protected List<EnumFacing> lockedSides;
	private int energy, maxEnergy, maxTransfer;

	public TileEnergyContainer(int maxEnergy, int maxTransfer)
	{
		this.maxEnergy = maxEnergy;
		this.maxTransfer = maxTransfer;

		inputSides = new ArrayList<>();
		outputSides = new ArrayList<>();
		lockedSides = new ArrayList<>();
	}

	public void setSideInput(EnumFacing input)
	{
		if (!inputSides.contains(input))
		{
			inputSides.add(input);
			outputSides.remove(input);
			lockedSides.remove(input);
		}
	}

	public void setSideOutput(EnumFacing output)
	{
		if (!outputSides.contains(output))
		{
			outputSides.add(output);
			inputSides.remove(output);
			lockedSides.remove(output);
		}
	}

	public void setSideLocked(EnumFacing locked)
	{
		if (!lockedSides.contains(locked))
		{
			lockedSides.add(locked);
			outputSides.remove(locked);
			inputSides.remove(locked);
		}
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		NBTTagCompound energy = new NBTTagCompound();

		energy.setInteger(ENERGY_NBT, this.energy);

		tag.setTag(ENERGY_NBT + "NBT", energy);

		NBTTagList sides = new NBTTagList();

		NBTTagCompound inputs = new NBTTagCompound();

		for (int i = 0; i < inputSides.size(); i++)
		{
			EnumFacing facing = inputSides.get(i);

			inputs.setInteger("input_" + i, facing.getIndex());
		}

		sides.appendTag(inputs);

		NBTTagCompound outputs = new NBTTagCompound();

		for (int i = 0; i < outputSides.size(); i++)
		{
			EnumFacing facing = outputSides.get(i);

			outputs.setInteger("output_" + i, facing.getIndex());
		}

		sides.appendTag(outputs);

		NBTTagCompound locked = new NBTTagCompound();

		for (int i = 0; i < lockedSides.size(); i++)
		{
			EnumFacing facing = lockedSides.get(i);

			locked.setInteger("locked_" + i, facing.getIndex());
		}

		sides.appendTag(locked);

		tag.setTag("sides", sides);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		NBTTagCompound energy = tag.getCompoundTag(ENERGY_NBT + "NBT");

		this.energy = energy.getInteger(ENERGY_NBT);

		NBTTagList sides = tag.getTagList("sides", 10);

		NBTTagCompound input = sides.getCompoundTagAt(0);
		NBTTagCompound output = sides.getCompoundTagAt(1);
		NBTTagCompound locked = sides.getCompoundTagAt(2);

		inputSides.clear();
		outputSides.clear();
		lockedSides.clear();

		for (int i = 0; i < EnumFacing.VALUES.length; i++)
		{
			EnumFacing in = EnumFacing.getFront(input.getInteger("input_" + i));
			EnumFacing out = EnumFacing.getFront(output.getInteger("output_" + i));
			EnumFacing lock = EnumFacing.getFront(locked.getInteger("locked_" + i));

			if (in != null && !inputSides.contains(in)) inputSides.add(in);
			if (out != null && !outputSides.contains(out)) outputSides.add(out);
			if (lock != null && !lockedSides.contains(lock)) lockedSides.add(lock);
		}
	}

	@Override
	public int getEnergy()
	{
		return energy;
	}

	@Override
	public void setEnergy(int energy)
	{
		this.energy = energy;

		this.energy = Helper.keepInBounds(this.energy, 0, maxEnergy);
	}

	@Override
	public int addEnergy(int amount, boolean test)
	{
		int transfer = Math.min(amount, getMaxTransfer());
		int lastEnergy = energy;

		lastEnergy += transfer;

		if (!test)
		{
			energy = Helper.keepInBounds(lastEnergy, 0, maxEnergy);
		}

		if (lastEnergy > maxEnergy)
		{
			return transfer - (lastEnergy - maxEnergy);
		}
		return transfer;
	}

	@Override
	public boolean subtractEnergy(int energy, boolean test)
	{
		if (getEnergy() >= energy)
		{
			if (!test)
			{
				this.energy -= energy;
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
	public int getMaxTransfer()
	{
		return maxTransfer;
	}

	@Override
	public boolean canInsert(EnumFacing side)
	{
		return inputSides.contains(side);
	}

	@Override
	public boolean canExtract(EnumFacing side)
	{
		return outputSides.contains(side);
	}

	@Override
	public boolean canConnect(EnumFacing side)
	{
		return !lockedSides.contains(side);
	}
}

package DarkS.TechXProject.blocks.tile;

import DarkS.TechXProject.api.energy.IEnergyContainer;
import DarkS.TechXProject.compat.waila.IWailaBody;
import com.mojang.realmsclient.gui.ChatFormatting;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TileEnergyContainer extends TileBase implements IEnergyContainer, ITickable, IWailaBody
{
	public static final String ENERGY_NBT = "energy";
	public static final String MAX_ENERGY_NBT = "maxEnergy";
	public static final String MAX_TRANSFER_ENERGY_NBT = "maxTransferEnergy";
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

	@Override
	public void update()
	{

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

	public void setSideInOut(EnumFacing side)
	{
		if (!inputSides.contains(side))
		{
			inputSides.add(side);
		}

		if (!outputSides.contains(side))
		{
			outputSides.add(side);
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound tag)
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

		return super.writeToNBT(tag);
	}

	public void readFromNBT(NBTTagCompound tag)
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

			if (!inputSides.contains(in)) inputSides.add(in);
			if (!outputSides.contains(out)) outputSides.add(out);
			if (!lockedSides.contains(lock)) lockedSides.add(lock);
		}

		super.readFromNBT(tag);
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

		this.energy = MathHelper.clamp_int(this.energy, 0, maxEnergy);
	}

	@Override
	public int addEnergy(int amount, boolean test)
	{
		int transfer = Math.min(amount, getMaxTransfer());
		int lastEnergy = energy;

		lastEnergy += transfer;

		if (!test)
		{
			energy += amount;

			if (energy < 0) energy = 0;
			if (energy > maxEnergy) energy = maxEnergy;
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

				if (this.energy < 0) this.energy = 0;
				if (this.energy > maxEnergy) this.energy = maxEnergy;
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
	public void setMaxTransfer(int maxTransfer)
	{
		this.maxTransfer = maxTransfer;
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

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		currenttip.add(ChatFormatting.GRAY + NumberFormat.getInstance().format(getEnergy()) + "/" + NumberFormat.getInstance().format(getMaxEnergy()));

		return currenttip;
	}
}

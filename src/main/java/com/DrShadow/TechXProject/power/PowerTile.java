package com.DrShadow.TechXProject.power;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import sun.plugin.dom.exception.InvalidStateException;

public class PowerTile extends TileEntity implements IPower, ITickable
{
	protected int power, maxPower, transfer;

	private boolean hasInit = false;

	public PowerTile()
	{

	}

	public void init(int maxPower, int transfer)
	{
		if (!hasInit)
		{
			hasInit = true;

			setMaxPower(maxPower);
			setTransfer(transfer);
		}
	}

	protected void checkForInit()
	{
		if (!hasInit)
		{
			throw new InvalidStateException("The has Not Initialised");
		}
	}

	@Override
	public void setPower(int power)
	{
		this.power = power;
	}

	@Override
	public int addPower(int power)
	{
		checkForInit();

		int left = 0;

		if (getPower() + power <= maxPower)
		{
			this.power += power;
		} else if (getPower() + power >= maxPower)
		{
			left = (getPower() + power) - maxPower;

			this.power += (power - left);
		}

		return left;
	}

	@Override
	public void substractPower(int power)
	{
		checkForInit();

		if (getPower() >= power)
		{
			this.power -= power;
		}
	}

	@Override
	public int getPower()
	{
		checkForInit();

		return power;
	}

	@Override
	public void setMaxPower(int maxPower)
	{
		checkForInit();

		this.maxPower = maxPower;
	}

	@Override
	public int getMaxPower()
	{
		checkForInit();

		return maxPower;
	}

	@Override
	public void setTransfer(int transfer)
	{
		checkForInit();

		this.transfer = transfer;
	}

	@Override
	public int getTransfer()
	{
		checkForInit();

		return transfer;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("power", power);
		tag.setInteger("maxPower", maxPower);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.power = tag.getInteger("power");
		this.maxPower = tag.getInteger("maxPower");
	}

	@Override
	public void update()
	{
		checkForInit();
	}
}

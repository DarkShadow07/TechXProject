package com.DrShadow.TechXProject.power;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class PowerTile extends TileEntity implements IPower, ITickable
{
	protected int power, maxPower;

	public PowerTile(int maxPower)
	{
		setMaxPower(maxPower);
	}

	@Override
	public void setPower(int power)
	{
		this.power = power;
	}

	@Override
	public int addPower(int power)
	{
		int left = 0;

		if (worldObj.isRemote)
		{
			if (getPower() + power <= maxPower)
			{
				this.power += power;
			} else if (getPower() + power >= maxPower)
			{
				left = (getPower() + power) - maxPower;

				this.power += (power - left);
			}
		}

		return left;
	}

	@Override
	public void substractPower(int power)
	{
		if (getPower() >= power)
		{
			this.power -= power;
		}
	}

	@Override
	public int getPower()
	{
		return power;
	}

	@Override
	public void setMaxPower(int maxPower)
	{
		this.maxPower = maxPower;
	}

	@Override
	public int getMaxPower()
	{
		return maxPower;
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

	}
}

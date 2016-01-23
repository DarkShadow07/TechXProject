package com.DrShadow.TechXProject.power;

import net.minecraft.util.EnumFacing;

public class PowerSides
{
	private EnumFacing in;
	private EnumFacing out;

	public PowerSides(EnumFacing in, EnumFacing out)
	{
		this.in = in;
		this.out = out;
	}

	public boolean isSideIn(int side)
	{
		return false;
	}
}

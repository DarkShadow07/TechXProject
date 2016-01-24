package com.DrShadow.TechXProject.tileEntities;

import com.DrShadow.TechXProject.power.IPower;
import com.DrShadow.TechXProject.power.PowerTile;
import com.DrShadow.TechXProject.util.PowerHelper;

public class TilePowerTransmitter extends PowerTile
{
	private int lost;

	private IPower target;

	public TilePowerTransmitter()
	{

	}

	@Override
	public void update()
	{
		super.update();

		if (target != null)
		{
			PowerHelper.moveWithLost(this, target, PowerHelper.getMaxSpeed(this, target), lost);
		}

		addPower(10);
	}

	public void setTarget(IPower power)
	{
		target = power;
	}

	public void setLost(int lost)
	{
		this.lost = lost;
	}
}

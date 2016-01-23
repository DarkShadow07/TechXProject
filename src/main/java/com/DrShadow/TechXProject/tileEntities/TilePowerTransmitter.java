package com.DrShadow.TechXProject.tileEntities;

import com.DrShadow.TechXProject.power.PowerTile;
import net.minecraft.util.ITickable;

public class TilePowerTransmitter extends PowerTile implements ITickable
{
	private int lost;

	private PowerTile target;

	public TilePowerTransmitter(int maxPower, int lost)
	{
		super(maxPower);

		this.lost = lost;

		setPower(maxPower);
	}

	@Override
	public void update()
	{
		super.update();

		if (target != null)
		{
			transferPower(target, 1);
		}

		addPower(1);
	}

	public boolean transferPower(PowerTile tile, int power)
	{
		if (tile.addPower(power) == 0)
		{
			int percentage = (lost * getMaxPower()) / 100;

			tile.addPower(power - percentage);

			substractPower(power - tile.addPower(power));

			return true;
		}

		else return false;
	}

	public void setTarget(PowerTile powerTile)
	{
		target = powerTile;
	}
}

package com.DrShadow.TechXProject.util;

import com.DrShadow.TechXProject.power.IPower;

public class PowerHelper
{
	public static boolean isPowerTile(Object obj)
	{
		return obj instanceof IPower;
	}

	public static float getPowerPercentage(IPower power)
	{
		return (float) power.getPower() / (float) power.getMaxPower();
	}

	public static int getHowMuchToSendFromToForEquate(IPower from, IPower to)
	{
		int result = -1;

		int sender = -1, target = -1;

		sender = (int) ((from.getPower() - to.getPower()) / 2.0);
		target = to.getMaxPower() - to.getPower();

		int abc = Math.min(sender, target);
		result = Math.min(getMaxSpeed(from, to), abc);

		return result;
	}

	public static int getMaxSpeed(IPower tile1, IPower tile2)
	{
		int result = -1;

		result = Math.max(tile1.getTransfer(), tile2.getTransfer());

		return result;
	}

	public static boolean tryToEquateEnergy(IPower fromTile, IPower toTile, int amount)
	{
		if (toTile.getPower() + amount <= toTile.getMaxPower() && fromTile.getPower() >= toTile.getPower() + amount && fromTile.getPower() >= amount)
		{
			return moveFromTo(fromTile, toTile, amount);
		} else
		{
			IPower a = fromTile;
			fromTile = toTile;
			toTile = a;

			if (shouldTransfer(fromTile, toTile, amount))
			{
				return moveFromTo(fromTile, toTile, amount);
			}
		}

		return false;
	}

	public static boolean tryToDrainFromTo(Object fromTile, Object toTile, int amount)
	{
		if (fromTile instanceof IPower && toTile instanceof IPower)
		{
			IPower tileFrom = (IPower) fromTile;
			IPower tileTo = (IPower) toTile;
			if (shouldTransfer(tileFrom, tileTo, amount))
			{
				boolean canMove = moveFromTo(tileFrom, tileTo, amount);
				return amount > 0 && canMove;
			}
		} else error();
		return false;
	}

	public static boolean moveWithLost(IPower from, IPower to, int amount, int lost)
	{
		if (shouldTransfer(from, to, amount))
		{
			int percentage = (lost * amount) / 100;

			from.substractPower(amount - to.addPower(amount));

			to.substractPower(percentage);

			return true;
		} else return false;
	}

	public static boolean shouldTransfer(IPower from, IPower to, int amount)
	{
		return from.getPower() >= amount && to.getPower() + amount <= to.getMaxPower();
	}

	public static boolean moveFromTo(IPower fromTile, IPower toTile, int amount)
	{
		if (fromTile instanceof IPower && toTile instanceof IPower)
		{
			fromTile.substractPower(amount);
			toTile.addPower(amount);
			return true;
		} else error();

		return false;
	}

	public static void error()
	{
		LogHelper.error("PowerHelper: The Object needs to implement the IPower interface");
	}
}

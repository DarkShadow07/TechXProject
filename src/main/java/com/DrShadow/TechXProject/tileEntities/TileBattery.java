package com.DrShadow.TechXProject.tileEntities;

import com.DrShadow.TechXProject.power.PowerTile;
import com.DrShadow.TechXProject.util.LogHelper;

public class TileBattery extends PowerTile
{
	public TileBattery()
	{

	}

	@Override
	public void update()
	{
		super.update();

		LogHelper.info("Battery " + getPower());
	}
}

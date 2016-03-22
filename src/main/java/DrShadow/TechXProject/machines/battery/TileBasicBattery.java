package DrShadow.TechXProject.machines.battery;

import net.minecraft.util.EnumFacing;

public class TileBasicBattery extends TileBatteryBase
{
	public TileBasicBattery()
	{
		super(1500000, 4096);

		setEnergy(1500000);

		for (EnumFacing facing : EnumFacing.VALUES)
		{
			setSideOutput(facing);
		}
	}

	@Override
	public void update()
	{
		doTransferForSides();
	}
}

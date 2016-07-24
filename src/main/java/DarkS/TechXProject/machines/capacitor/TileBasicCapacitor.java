package DarkS.TechXProject.machines.capacitor;

import net.minecraft.util.EnumFacing;

public class TileBasicCapacitor extends TileCapacitor
{
	public TileBasicCapacitor()
	{
		super(1500000, 4096);

		setEnergy(1500000);

		for (EnumFacing facing : EnumFacing.VALUES)
		{
			setSideInOut(facing);
		}
	}
}

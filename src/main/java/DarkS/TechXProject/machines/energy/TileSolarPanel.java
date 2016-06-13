package DarkS.TechXProject.machines.energy;

import DarkS.TechXProject.api.energy.IEnergyGenerator;
import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import net.minecraft.util.EnumFacing;

public class TileSolarPanel extends TileEnergyContainer implements IEnergyGenerator
{
	public TileSolarPanel()
	{
		super(24000, 80);

		setSideOutput(EnumFacing.DOWN);
	}

	@Override
	public void update()
	{
		if (worldObj.isDaytime())
		{
			generateEnergy();
		}
	}

	@Override
	public int getGenerating()
	{
		return 20;
	}

	@Override
	public void generateEnergy()
	{
		addEnergy(getGenerating(), false);
	}
}

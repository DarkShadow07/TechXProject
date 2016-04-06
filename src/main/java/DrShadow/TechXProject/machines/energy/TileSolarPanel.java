package DrShadow.TechXProject.machines.energy;

import DrShadow.TechXProject.api.energy.IEnergyGenerator;
import DrShadow.TechXProject.tileEntities.TileEnergyContainer;
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

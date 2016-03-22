package DrShadow.TechXProject.api.energy;

import net.minecraft.util.EnumFacing;

public interface IEnergyContainer
{
	int getEnergy();

	void setEnergy(int energy);

	int addEnergy(int energy, boolean test);

	boolean subtractEnergy(int energy, boolean test);

	int getMaxEnergy();

	int getMaxTransfer();

	boolean canInsert(EnumFacing side);

	boolean canExtract(EnumFacing side);

	boolean canConnect(EnumFacing side);
}

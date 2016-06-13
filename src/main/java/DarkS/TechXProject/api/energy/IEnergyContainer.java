package DarkS.TechXProject.api.energy;

import net.minecraft.util.EnumFacing;

public interface IEnergyContainer
{
	/**
	 * Returns the Energy Level
	 *
	 * @return Energy Level
	 */
	int getEnergy();

	/**
	 * Sets the Energy Level
	 *
	 * @param energy Energy to set
	 */
	void setEnergy(int energy);

	/**
	 * Adds Energy
	 *
	 * @param energy Energy to add
	 * @param test   If the Energy is actually added or is just testing
	 * @return Energy Added
	 */
	int addEnergy(int energy, boolean test);

	/**
	 * Subtracts Energy
	 *
	 * @param energy Energy to subtract
	 * @param test   if the Energy is actually subtracted or is just testing
	 * @return If it can subtract the Energy
	 */
	boolean subtractEnergy(int energy, boolean test);

	/**
	 * Returns the Max Energy
	 *
	 * @return Max Energy
	 */
	int getMaxEnergy();

	/**
	 * Returns Max Transfer
	 *
	 * @return Max Transfer
	 */
	int getMaxTransfer();

	/**
	 * Sets the Max Transfer
	 *
	 * @param transfer Energy to set as Max Transfer
	 */
	void setMaxTransfer(int transfer);

	/**
	 * Returns if it can insert Energy to the given Side
	 *
	 * @param side Side to check
	 * @return If can insert Energy
	 */
	boolean canInsert(EnumFacing side);

	/**
	 * Returns if it can extract Energy to the given Side
	 *
	 * @param side Side to check
	 * @return If can extract Energy
	 */
	boolean canExtract(EnumFacing side);

	/**
	 * Returns if it can connect to the given Side
	 *
	 * @param side Side to check
	 * @return If can connect to the side
	 */
	boolean canConnect(EnumFacing side);
}

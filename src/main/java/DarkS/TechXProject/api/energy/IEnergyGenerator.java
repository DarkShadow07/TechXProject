package DarkS.TechXProject.api.energy;

public interface IEnergyGenerator
{
	/**
	 * Returns the Energy generating
	 *
	 * @return Energy Generating
	 */
	int getGenerating();

	/**
	 * Called when should Generate Energy
	 */
	void generateEnergy();
}

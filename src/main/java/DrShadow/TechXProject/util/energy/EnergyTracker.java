package DrShadow.TechXProject.util.energy;

import DrShadow.TechXProject.util.PerTickCalculator;

public class EnergyTracker
{
	private final PerTickCalculator recTracker = new PerTickCalculator();
	private final PerTickCalculator sentTracker = new PerTickCalculator();
	private long prev = -1;
	private int sentThisTick = 0;

	private int receivedThisTick = 0;

	public void tickStart(long storedEnergy)
	{
		if (prev > -1)
		{
			long received = storedEnergy - prev;
			received = Math.max(0, received);
			receivedThisTick += received;
		}
	}

	public void receiveEnergy(int energy)
	{
		receivedThisTick += energy;
	}

	public void sendEnergy(int energy)
	{
		sentThisTick += energy;
	}

	public void tickEnd(long storedEnergy)
	{
		prev = storedEnergy;
		sentTracker.tick(sentThisTick);
		recTracker.tick(receivedThisTick);
		receivedThisTick = 0;
		sentThisTick = 0;
	}

	public float getReceivePerTick()
	{
		return recTracker.getAverage();
	}

	public float getSendPerTick()
	{
		return sentTracker.getAverage();
	}
}

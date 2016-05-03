package DrShadow.TechXProject.machines.energyMonitor;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.api.energy.IEnergyGenerator;
import DrShadow.TechXProject.blocks.tile.TileEnergyContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.conduit.energy.TileConduitEnergy;
import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import DrShadow.TechXProject.util.energy.EnergyTracker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

public class TileEnergyMonitor extends TileEnergyContainer
{
	public EnergyTracker networkTracker = new EnergyTracker();
	private ConduitNetwork network;

	public TileEnergyMonitor()
	{
		super(500000, 500);
	}

	@Override
	public void update()
	{
		networkTracker.tickStart(getNetworkEnergy());

		checkForNetwork();

		subtractEnergy(1, false);

		worldObj.updateComparatorOutputLevel(pos, getBlockType());

		networkTracker.tickEnd(getNetworkEnergy());

		for (EnumFacing facing : EnumFacing.VALUES)
		{
			setSideInput(facing);
		}
	}

	private void checkForNetwork()
	{
		for (TileEntity tile : getTilesOnSides())
		{
			if (tile != null && tile instanceof TileConduitEnergy && ((TileConduitEnergy) tile).hasNetwork())
			{
				network = ((TileConduitEnergy) tile).getNetwork();
			}
		}

		if (Arrays.asList(getTilesOnSides()).stream().allMatch(tileEntity -> tileEntity == null || !(tileEntity instanceof TileConduitEnergy) || !((TileConduitEnergy) tileEntity).hasNetwork()))
		{
			network = null;
		}
	}

	public long getNetworkEnergy()
	{
		long networkEnergy = 0;

		if (getEnergy() >= 1 && network != null && network.getElements() != null && !network.getElements().isEmpty())
		{
			for (INetworkElement element : network.getElements())
			{
				if (element != null && element instanceof IEnergyContainer)
				{
					networkEnergy += ((IEnergyContainer) element).getEnergy();

					TileEntity tile = element.getAttachedTile();

					if (tile != null && tile instanceof IEnergyContainer)
					{
						networkEnergy += ((IEnergyContainer) tile).getEnergy();
					}
				}
			}
		}

		return networkEnergy;
	}

	public long getMaxNetworkEnergy()
	{
		long maxNetworkEnergy = 0;

		if (getEnergy() >= 1 && network != null && network.getElements() != null && !network.getElements().isEmpty())
		{
			for (INetworkElement element : network.getElements())
			{
				if (element != null && element instanceof IEnergyContainer)
				{
					maxNetworkEnergy += ((IEnergyContainer) element).getMaxEnergy();

					TileEntity tile = element.getAttachedTile();

					if (tile != null && tile instanceof IEnergyContainer)
					{
						maxNetworkEnergy += ((IEnergyContainer) tile).getMaxEnergy();
					}
				}
			}
		}

		return maxNetworkEnergy;
	}

	public long getGeneratingEnergy()
	{
		long generating = 0;

		if (network != null && network.getElements() != null && !network.getElements().isEmpty())
		{
			for (INetworkElement element : network.getElements())
			{
				if (element != null && element instanceof IEnergyContainer)
				{
					TileEntity tile = element.getAttachedTile();

					if (tile != null && tile instanceof IEnergyGenerator)
					{
						networkTracker.receiveEnergy(((IEnergyGenerator) tile).getGenerating());

						generating += ((IEnergyGenerator) tile).getGenerating();
					}
				}
			}
		}

		return generating;
	}

	public boolean hasNetowork()
	{
		return network != null;
	}
}

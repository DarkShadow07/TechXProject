package DarkS.TechXProject.machines.energyMonitor;

import DarkS.TechXProject.api.energy.IEnergyContainer;
import DarkS.TechXProject.api.energy.IEnergyGenerator;
import DarkS.TechXProject.api.network.ConduitNetwork;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.node.energy.TileEnergyNode;
import DarkS.TechXProject.util.energy.EnergyTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

public class TileEnergyMonitor extends TileEnergyContainer
{
	public EnergyTracker networkTracker = new EnergyTracker();
	public int maxE = 75, minE = 15;
	private ConduitNetwork network;
	private boolean emiting = false;

	public TileEnergyMonitor()
	{
		super(500000, 500);
	}

	public int getRedstoneLevel()
	{
		if (!hasNetowork()) return 0;
		int percent = (int) ((getNetworkEnergy() * 100) / getMaxNetworkEnergy());

		if (percent < minE)
		{
			emiting = true;
		}

		if (percent >= maxE)
		{
			emiting = false;
		}

		return emiting ? 15 : 0;
	}

	@Override
	public void update()
	{
		markForUpdate();

		networkTracker.tickStart(getNetworkEnergy());

		checkForNetwork();

		subtractEnergy(1, false);

		networkTracker.tickEnd(getNetworkEnergy());

		for (EnumFacing facing : EnumFacing.VALUES)
		{
			setSideInput(facing);
		}
	}


	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = tag.getCompoundTag("dataEnergy");

		minE = data.getInteger("minE");
		maxE = data.getInteger("maxE");

		super.fromNBT(tag);
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("minE", minE);
		data.setInteger("maxE", maxE);

		tag.setTag("dataEnergy", data);

		super.toNBT(tag);
	}

	private void checkForNetwork()
	{
		for (TileEntity tile : getTilesOnSides())
		{
			if (tile != null && tile instanceof TileEnergyNode && ((TileEnergyNode) tile).hasNetwork())
			{
				network = ((TileEnergyNode) tile).getNetwork();
			}
		}

		if (Arrays.asList(getTilesOnSides()).stream().allMatch(tileEntity -> tileEntity == null || !(tileEntity instanceof TileEnergyNode) || !((TileEnergyNode) tileEntity).hasNetwork()))
		{
			network = null;
		}
	}

	public long getNetworkEnergy()
	{
		long networkEnergy = 1;

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
		long maxNetworkEnergy = 1;

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

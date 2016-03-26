package DrShadow.TechXProject.conduit.energy;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.api.energy.TileEnergyContainer;
import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.conduit.item.ItemConduitUtil;
import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class TileConduitEnergy extends TileEnergyContainer implements INetworkElement
{
	public ConduitNetwork network;

	private IEnergyContainer energyContainer;

	private boolean active = true, isInput = false, isOutput = false;

	public TileConduitEnergy()
	{
		super(32768, 32768);
	}

	@Override
	public void update()
	{
		updateEnergyContainer();

		doTransfer();
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		super.toNBT(tag);

		NBTTagCompound booleans = new NBTTagCompound();

		booleans.setBoolean("input", isInput);
		booleans.setBoolean("output", isOutput);

		tag.setTag("booleans", booleans);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		super.fromNBT(tag);

		NBTTagCompound booleans = tag.getCompoundTag("booleans");

		isInput = booleans.getBoolean("input");
		isOutput = booleans.getBoolean("output");
	}

	public void doTransfer()
	{
		if (!hasNetwork() || network.getOutputContainers() == null) return;

		if (isInput() && hasInventory())
		{
			transferFromContainer();
		}

		if (isOutput() && hasInventory())
		{
			transferToContainer();
		}

		transferForOthers();
	}

	public void transferToContainer()
	{
		if (energyContainer.canInsert(EnumFacing.getFront(getBlockMetadata()).getOpposite()))
		{
			int transfer = Math.min(getMaxTransfer(), energyContainer.getMaxTransfer());

			if (subtractEnergy(energyContainer.addEnergy(transfer, true), true))
			{
				subtractEnergy(energyContainer.addEnergy(transfer, false), false);
			}
		}
	}

	public void transferFromContainer()
	{
		if (energyContainer.canExtract(EnumFacing.getFront(getBlockMetadata()).getOpposite()))
		{
			int transfer = Math.min(getMaxTransfer(), energyContainer.getMaxTransfer());

			if (energyContainer.subtractEnergy(addEnergy(transfer, true), true))
			{
				energyContainer.subtractEnergy(addEnergy(transfer, false), false);
			}
		}
	}

	public void transferForOthers()
	{
		List<INetworkElement> elements = ItemConduitUtil.sortElements(this);

		List<INetworkElement> outputs = ItemConduitUtil.getOutputs(elements);

		if (!elements.isEmpty() && !outputs.isEmpty())
		{
			for (INetworkElement output : outputs)
			{
				if (output instanceof IEnergyContainer && output.hasInventory() && hasInventory() && output.isActive() && isActive())
				{
					IEnergyContainer outputEnergy = (IEnergyContainer) output;

					int transfer = Math.max(getMaxTransfer(), ((IEnergyContainer) output).getMaxTransfer());

					subtractEnergy(outputEnergy.addEnergy(transfer, false), false);

					return;
				}
			}
		}
	}

	public void updateEnergyContainer()
	{
		TileEntity tile = worldObj.getTileEntity(pos.offset(EnumFacing.getFront(getBlockMetadata())));

		if (tile != null && tile instanceof IEnergyContainer)
		{
			energyContainer = (IEnergyContainer) tile;
		} else energyContainer = null;
	}

	@Override
	public ConduitNetwork getNetwork()
	{
		return network;
	}

	@Override
	public void setNetwork(ConduitNetwork network)
	{
		this.network = network;
	}

	@Override
	public ConduitNetwork addToNetwork(INetworkElement toAdd)
	{
		network.addToNetwork(toAdd);

		return network;
	}

	@Override
	public ConduitNetwork removeFromNetwork(INetworkElement toRemove)
	{
		network.removeFromNetwork(toRemove);

		return network;
	}

	@Override
	public TileEntity getTile()
	{
		return this;
	}

	@Override
	public IInventory getInventory()
	{
		return null;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return (TileEntity) energyContainer;
	}

	@Override
	public boolean hasInventory()
	{
		return energyContainer != null;
	}

	@Override
	public int distanceTo(TileEntity to)
	{
		return (int) getTile().getPos().distanceSq(to.getPos());
	}

	@Override
	public INetworkContainer getController()
	{
		return network.getController();
	}

	@Override
	public boolean isInput()
	{
		return isInput;
	}

	public void setInput(boolean input)
	{
		isInput = input;
	}

	@Override
	public boolean isOutput()
	{
		return isOutput;
	}

	public void setOutput(boolean output)
	{
		isOutput = output;
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	@Override
	public void setActive(boolean act)
	{
		active = act;
	}

	@Override
	public boolean hasNetwork()
	{
		return network != null;
	}
}

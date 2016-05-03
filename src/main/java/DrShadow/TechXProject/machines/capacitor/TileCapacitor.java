package DrShadow.TechXProject.machines.capacitor;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.blocks.tile.TileEnergyContainer;
import DrShadow.TechXProject.util.energy.EnergyTracker;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileCapacitor extends TileEnergyContainer
{
	public EnergyTracker tracker = new EnergyTracker();

	public TileCapacitor(int maxEnergy, int maxTransfer)
	{
		super(maxEnergy, maxTransfer);
	}

	@Override
	public void update()
	{
		tracker.tickStart(getEnergy());

		tracker.tickEnd(getEnergy());
	}

	protected void doTransferForSides()
	{
		for (EnumFacing side : outputSides)
		{
			BlockPos offsetPos = pos.offset(side);

			if (worldObj.getTileEntity(offsetPos) != null && worldObj.getTileEntity(offsetPos) instanceof IEnergyContainer)
			{
				IEnergyContainer container = (IEnergyContainer) worldObj.getTileEntity(offsetPos);

				int canTransfer = Math.min(getMaxTransfer(), getEnergy());
				int maxTransfer = Math.min(canTransfer, container.getMaxTransfer());

				if (container.canInsert(side.getOpposite()))
				{
					subtractEnergy(container.addEnergy(maxTransfer, false), false);
				}
			}
		}

		for (EnumFacing side : inputSides)
		{
			BlockPos offsetPos = pos.offset(side);

			if (worldObj.getTileEntity(offsetPos) != null && worldObj.getTileEntity(offsetPos) instanceof IEnergyContainer)
			{
				IEnergyContainer container = (IEnergyContainer) worldObj.getTileEntity(offsetPos);

				int canTransfer = Math.min(getMaxTransfer(), getEnergy());
				int maxTransfer = Math.min(canTransfer, container.getMaxTransfer());

				if (container.canExtract(side.getOpposite()))
				{
					container.subtractEnergy(addEnergy(maxTransfer, false), false);
				}
			}
		}
	}

	@Override
	public int addEnergy(int amount, boolean test)
	{
		if (!test)
		{
			tracker.receiveEnergy(amount);

			markDirty();
			markForUpdate();
		}

		return super.addEnergy(amount, test);
	}

	@Override
	public boolean subtractEnergy(int energy, boolean test)
	{
		if (!test)
		{
			tracker.sendEnergy(energy);

			markDirty();
			markForUpdate();
		}

		return super.subtractEnergy(energy, test);
	}
}

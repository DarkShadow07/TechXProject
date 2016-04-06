package DrShadow.TechXProject.machines.capacitor;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.tileEntities.TileEnergyContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileCapacitor extends TileEnergyContainer
{
	public TileCapacitor(int maxEnergy, int maxTransfer)
	{
		super(maxEnergy, maxTransfer);
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
}

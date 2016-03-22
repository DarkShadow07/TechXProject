package DrShadow.TechXProject.machines.battery;

import DrShadow.TechXProject.api.energy.IEnergyContainer;
import DrShadow.TechXProject.api.energy.TileEnergyContainer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class TileBatteryBase extends TileEnergyContainer
{
	public TileBatteryBase(int maxEnergy, int maxTransfer)
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
	}
}

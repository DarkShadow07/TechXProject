package DarkS.TechXProject.machines.fluidTank;

import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.compat.waila.IWailaBody;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public class TileFluidTank extends TileBase implements IFluidHandler, IWailaBody
{
	public FluidTank tank;
	;

	public TileFluidTank()
	{
		tank = new FluidTank(32000);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		tank.fill(FluidStack.loadFluidStackFromNBT(tag), true);

		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		if (tank.getFluid() != null)
			tank.getFluid().writeToNBT(tag);

		return super.writeToNBT(tag);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY))
			return (T) this;

		return (T) this;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY))
			return true;

		return super.hasCapability(capability, facing);
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (tank.getFluid() != null)
			currenttip.add(tank.getFluid().getLocalizedName() + ", " + NumberFormat.getInstance().format(tank.getFluidAmount()) + "mb");

		return currenttip;
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		return tank.fill(resource, doFill);
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		return tank.drain(resource, doDrain);
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return tank.drain(maxDrain, doDrain);
	}
}

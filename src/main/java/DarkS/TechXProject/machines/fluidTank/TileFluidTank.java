package DarkS.TechXProject.machines.fluidTank;

import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.compat.waila.IWailaBody;
import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
import DarkS.TechXProject.util.Logger;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;

import java.text.NumberFormat;
import java.util.List;

public class TileFluidTank extends TileBase implements IFluidHandler, IWailaBody, IHighlightProvider
{
	public FluidTank tank;

	private int maxOut = 500;

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
	public int fill(EnumFacing from, FluidStack resource, boolean doFill)
	{
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
	{
		return drain(from, maxOut, doDrain);
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
	{
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		return tank.getFluidAmount() < tank.getCapacity();
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid)
	{
		return tank.getFluidAmount() > 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from)
	{
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return (T) this;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (tank.getFluid() != null)
			currenttip.add(tank.getFluid().getLocalizedName() + ", " + NumberFormat.getInstance().format(tank.getFluidAmount()) + "mb");

		return currenttip;
	}

	@Override
	public SelectionBox[] getBoxes()
	{
		return new SelectionBox[]{new SelectionBox(0, new AxisAlignedBB(0.15625f, 0.0f, 0.15625f, 0.84375f, 0.125f, 0.84375f), new AxisAlignedBB(0.15625f, 0.875f, 0.15625f, 0.84375f, 1.0f, 0.84375f), new AxisAlignedBB(0.1875f, 0.125f, 0.1875f, 0.8125f, 0.875f, 0.8125f))};
	}

	@Override
	public SelectionBox[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end)
	{
		return getBoxes();
	}

	@Override
	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end)
	{
		return SelectionBox.rayTraceAll(getBoxes(), pos, start, end);
	}

	@Override
	public boolean onBoxClicked(SelectionBox box, EntityPlayer clicker)
	{
		return false;
	}
}

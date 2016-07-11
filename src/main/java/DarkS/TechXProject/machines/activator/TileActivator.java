package DarkS.TechXProject.machines.activator;

import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.compat.waila.IWailaBody;
import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
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

import java.util.ArrayList;
import java.util.List;

public class TileActivator extends TileBase implements IWailaBody, ITickable, IHighlightProvider
{
	public int power = 15, defaultOn = 40, defaultOff = 60, timeOn = defaultOn, timeOff = defaultOff;

	public TileActivator()
	{

	}

	@Override
	public void update()
	{
		if (timeOn > 0)
			timeOn--;
		else
		{
			if (timeOff <= 0 && timeOn <= 0)
			{
				timeOff = defaultOff;

				markDirty();
				markForUpdate();
			}

			timeOff--;

			if (timeOff <= 0)
			{
				timeOn = defaultOn;

				markDirty();
				markForUpdate();
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		timeOn = tag.getInteger("timeOn");
		timeOff = tag.getInteger("timeOff");

		defaultOn = tag.getInteger("defOn");
		defaultOff = tag.getInteger("defOff");

		power = tag.getInteger("power");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("timeOn", timeOn);
		tag.setInteger("timeOff", timeOff);

		tag.setInteger("defOn", defaultOn);
		tag.setInteger("defOff", defaultOff);

		tag.setInteger("power", power);

		return tag;
	}

	public int getPower()
	{
		return timeOn > 0 ? power : 0;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		currenttip.add(String.format("Time On: %s, Time Off: %s", defaultOn, defaultOff));

		return currenttip;
	}

	@Override
	public SelectionBox[] getBoxes()
	{
		return new SelectionBox[]{new SelectionBox(1, new AxisAlignedBB(0.0625f, 0.125f, 0.0625f, 0.9375f, 0.15625f, 0.9375f)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite()), new SelectionBox(0, new AxisAlignedBB(0, 0, 0, 1, 0.125, 1)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite())};
	}

	@Override
	public SelectionBox[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end)
	{
		List<SelectionBox> result = new ArrayList<>();

		for (SelectionBox box : getBoxes())
		{
			if (box.rayTrace(pos, start, end) != null)
			{
				result.add(box);

				return result.toArray(new SelectionBox[0]);
			}
		}

		return result.toArray(new SelectionBox[0]);
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

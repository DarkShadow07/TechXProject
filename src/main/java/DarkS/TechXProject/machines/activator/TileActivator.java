package DarkS.TechXProject.machines.activator;

import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.compat.waila.IWailaBody;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class TileActivator extends TileBase implements IWailaBody
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
	public void fromNBT(NBTTagCompound tag)
	{
		timeOn = tag.getInteger("timeOn");
		timeOff = tag.getInteger("timeOff");

		defaultOn = tag.getInteger("defOn");
		defaultOff = tag.getInteger("defOff");

		power = tag.getInteger("power");
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		tag.setInteger("timeOn", timeOn);
		tag.setInteger("timeOff", timeOff);

		tag.setInteger("defOn", defaultOn);
		tag.setInteger("defOff", defaultOff);

		tag.setInteger("power", power);
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
}

package DarkS.TechXProject.machines.wirelessCharger;

import DarkS.TechXProject.api.energy.item.IEnergyItem;
import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.capability.CustomCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class TileWirelessCharger extends TileEnergyContainer
{
	private final int rad = 64;

	public boolean working = false;

	public TileWirelessCharger()
	{
		super(1250000, 2000);

		for (EnumFacing facing : EnumFacing.VALUES)
			setSideInput(facing);
	}

	@Override
	public void update()
	{
		transferToNearPlayers();
	}

	private void transferToNearPlayers()
	{
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-rad, -rad, -rad), pos.add(rad, rad, rad)));

		for (EntityPlayer player : players)
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); i++)
			{
				ItemStack stack = player.inventory.getStackInSlot(i);

				if (stack != null && stack.getItem() instanceof IEnergyItem)
				{
					IEnergyItem item = (IEnergyItem) stack.getItem();

					int max = Math.min(getMaxTransfer(), item.getMaxTransfer(stack));

					if (subtractEnergy(item.addEnergy(stack, max, true), true) && item.getEnergy(stack) < item.getMaxEnergy(stack))
					{
						working = true;
						markForUpdate();

						subtractEnergy(item.addEnergy(stack, max, false), false);

						break;
					} else
					{
						working = false;

						markDirty();
						markForUpdate();
					}
				} else
				{
					working = false;

					markDirty();
					markForUpdate();
				}
			}

			if (player.hasCapability(CustomCapabilities.ENERGY_CAPABILITY, null))
			{
				CustomCapabilities.IEnergyData data = player.getCapability(CustomCapabilities.ENERGY_CAPABILITY, null);

				int max = Math.min(getMaxTransfer() / 10, data.getMaxEnergy() - data.getEnergy());

				if (getEnergy() < max) return;

				data.setEnergy(player, data.getEnergy() + max);

				subtractEnergy(max, false);
			}
		}
	}
}

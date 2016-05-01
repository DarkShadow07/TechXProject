package DrShadow.TechXProject.conduit.logic.condition;

import DrShadow.TechXProject.util.Util;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Condition
{
	private EnumConditionType type;

	public Condition(EnumConditionType type)
	{
		this.type = type;
	}

	public boolean validState(BlockPos pos, World world)
	{
		switch (type)
		{
			case NO_CONDITION:
				return true;
			case REDSTONE_ON:
				return world.isBlockIndirectlyGettingPowered(pos) > 0;
			case REDSTONE_OFF:
				return !(world.isBlockIndirectlyGettingPowered(pos) > 0);
			case TIME_DAY:
				return world.isDaytime();
			case TIME_NIGHT:
				return !world.isDaytime();
			case LIGHT_HIGH:
				return world.getLight(pos) >= 7;
			case LIGHT_LOW:
				return world.getLight(pos) < 7;
			case INVENTORY_FULL:
				for (int i = 0; i < getTilesOnSides(pos, world).length; i++)
				{
					TileEntity[] tiles = getTilesOnSides(pos, world);

					if (tiles[i] != null && tiles[i] instanceof IInventory)
					{
						IInventory inventory = (IInventory) tiles[i];

						return Util.inventoryFull(inventory);
					}
				}
				break;
			case INVENTORY_EMPTY:
				for (int i = 0; i < getTilesOnSides(pos, world).length; i++)
				{
					TileEntity[] tiles = getTilesOnSides(pos, world);

					if (tiles[i] != null && tiles[i] instanceof IInventory)
					{
						IInventory inventory = (IInventory) tiles[i];

						List<ItemStack> result = new ArrayList<>();
						int size = inventory.getSizeInventory();
						for (int k = 0; k < size; k++)
						{
							result.add(inventory.getStackInSlot(k));
						}

						return result.stream().allMatch(stack -> stack == null || stack.stackSize == 0);
					}
				}
				break;
			case INVENTORY_SPACE:
				for (int i = 0; i < getTilesOnSides(pos, world).length; i++)
				{
					TileEntity[] tiles = getTilesOnSides(pos, world);

					if (tiles[i] != null && tiles[i] instanceof IInventory)
					{
						IInventory inventory = (IInventory) tiles[i];

						for (int k = 0; k < inventory.getSizeInventory(); k++)
						{
							if (inventory.getStackInSlot(k) == null)
							{
								return true;
							}
						}
					}
				}
				break;
		}

		return false;
	}

	public EnumConditionType getType()
	{
		return type;
	}

	private TileEntity[] getTilesOnSides(BlockPos pos, World world)
	{
		TileEntity[] result = new TileEntity[6];

		if (!Util.isNull(world, pos))
		{
			for (int i = 0; i < 6; i++) result[i] = world.getTileEntity(pos.offset(EnumFacing.getFront(i)));
		}
		return result;
	}
}

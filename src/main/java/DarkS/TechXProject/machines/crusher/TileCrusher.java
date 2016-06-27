package DarkS.TechXProject.machines.crusher;

import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.compat.jei.crusher.CrusherRecipeHandler;
import DarkS.TechXProject.node.item.NodeUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileCrusher extends TileEnergyContainer implements ISidedInventory
{
	public static final int drainTick = 20;
	public boolean working = false;
	public ItemStack[] inventory;
	private int[] slotsIn = new int[]{0};
	private int[] slotsOut = new int[]{1, 2, 3, 4};
	private List<EnumFacing> itemInputSides = new ArrayList<>();
	private List<EnumFacing> itemOutputSides = new ArrayList<>();
	private int ticks = 0, targetTicks = 1;

	public TileCrusher()
	{
		super(100000, 500);

		inventory = new ItemStack[6];

		for (EnumFacing facing : EnumFacing.VALUES)
		{
			setSideInput(facing);
		}

		setSideItemInOut(EnumFacing.UP);

		for (EnumFacing facing : EnumFacing.HORIZONTALS)
		{
			setSideItemInput(facing);
		}

		setSideItemOutput(EnumFacing.DOWN);
	}

	@Override
	public void update()
	{
		transferItems();

		if (canWork() && getEnergy() >= drainTick)
		{
			working = true;

			subtractEnergy(drainTick, false);

			targetTicks = CrusherRecipeHandler.instance.getCrushingTicks(inventory[0]);

			if (ticks < targetTicks) ticks += 1;

			if (ticks >= targetTicks)
			{
				List<ItemStack> result = CrusherRecipeHandler.instance.getCrushingResult(inventory[0]);
				working = false;

				ticks = 0;
				targetTicks = 1;

				for (ItemStack resultStack : result)
				{
					if (getAvailableSlot(resultStack) < 0)
					{
						return;
					}

					int chance = CrusherRecipeHandler.instance.getItemChance(resultStack);

					Random r = new Random();

					if (r.nextInt(100) <= chance)
					{
						setInventorySlotContents(getAvailableSlot(resultStack), Util.ItemStackUtil.stack(inventory[getAvailableSlot(resultStack)], resultStack));
					}
				}

				decrStackSize(0, 1);

				markDirty();
				markForUpdate();
			}

		} else
		{
			ticks = 0;
			targetTicks = 1;
			working = false;
		}
	}

	private int getAvailableSlot(ItemStack stack)
	{
		for (int i = 1; i < 5; i++)
		{
			if (NodeUtil.canStack(stack, inventory[i]))
			{
				return i;
			}
		}

		return -1;
	}

	private void transferItems()
	{
		for (EnumFacing outputFacing : itemOutputSides)
		{
			BlockPos offsetPos = pos.offset(outputFacing);

			if (worldObj.getTileEntity(offsetPos) != null && worldObj.getTileEntity(offsetPos) instanceof IInventory)
			{
				IInventory inventory = (IInventory) worldObj.getTileEntity(offsetPos);

				for (int i = 1; i <= 4; i++)
				{
					if (getStackInSlot(i) != null)
					{
						NodeUtil.transferStack(getStackInSlot(i), inventory, outputFacing);

						if (getStackInSlot(i) != null && getStackInSlot(i).stackSize <= 0)
						{
							setInventorySlotContents(i, null);
						}
					}
				}
			}
		}
	}

	public int getProgress()
	{
		return ticks * 100 / targetTicks;
	}

	private boolean canWork()
	{
		boolean ret = false;

		if (inventory[0] == null) return false;

		List<ItemStack> result = CrusherRecipeHandler.instance.getCrushingResult(inventory[0]);

		if (result == null) return false;
		if (Util.isAnyNull(inventory[1], inventory[2], inventory[3], inventory[4])) return true;

		for (int i = 0; i < result.size(); i++)
		{
			for (int k = 1; k < 5; k++)
			{
				if (OreDictionary.itemMatches(result.get(i), inventory[k], true))
				{
					int stackSize;

					stackSize = inventory[k].stackSize + result.get(i).stackSize;

					return stackSize <= inventory[i].getMaxStackSize();
				} else
				{
					ret = false;
				}
			}
		}

		return ret;
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		super.toNBT(tag);

		NBTTagCompound integers = new NBTTagCompound();

		integers.setInteger("ticks", ticks);
		integers.setInteger("targetTicks", targetTicks);

		tag.setTag("integers", integers);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.length; ++i)
		{
			if (inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		tag.setTag("Items", nbttaglist);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		super.fromNBT(tag);

		NBTTagCompound integers = tag.getCompoundTag("integers");

		ticks = integers.getInteger("ticks");
		targetTicks = integers.getInteger("targetTicks");

		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < inventory.length)
			{
				inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}


	public void setSideItemInput(EnumFacing input)
	{
		if (!this.itemInputSides.contains(input))
		{
			itemInputSides.add(input);
			itemOutputSides.remove(input);
		}
	}

	public void setSideItemOutput(EnumFacing output)
	{
		if (!this.itemOutputSides.contains(output))
		{
			itemOutputSides.add(output);
			itemInputSides.remove(output);
		}
	}

	public void setSideItemInOut(EnumFacing side)
	{
		if (!this.itemOutputSides.contains(side) && !itemOutputSides.contains(side))
		{
			itemOutputSides.add(side);
			itemInputSides.add(side);
		}
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (inventory[index] != null)
		{
			if (inventory[index].stackSize <= count)
			{
				ItemStack itemStack = inventory[index];
				inventory[index] = null;
				markDirty();
				return itemStack;
			}

			ItemStack itemStack = inventory[index].splitStack(count);
			if (inventory[index].stackSize == 0)
				inventory[index] = null;

			markDirty();
			return itemStack;
		}

		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		if (inventory[slot] != null)
		{
			ItemStack itemStack = inventory[slot];
			setInventorySlotContents(slot, null);
			return itemStack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
		markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return CrusherRecipeHandler.instance.isValidStack(stack);
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{

	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{

	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (itemInputSides.contains(side)) return slotsIn;
		if (itemOutputSides.contains(side)) return slotsOut;

		return new int[]{};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return true;
	}
}

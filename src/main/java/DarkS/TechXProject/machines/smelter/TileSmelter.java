package DarkS.TechXProject.machines.smelter;

import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.compat.jei.smelter.SmelterRecipeHandler;
import DarkS.TechXProject.conduit.item.ItemConduitUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TileSmelter extends TileEnergyContainer implements ISidedInventory
{
	public static final int drainTick = 40;
	public boolean working = false;
	public ItemStack[] inventory;
	private int[] slotsIn = new int[]{0, 1, 2};
	private int[] slotsOut = new int[]{3};
	private List<EnumFacing> itemInputSides = new ArrayList<>();
	private List<EnumFacing> itemOutputSides = new ArrayList<>();
	private int ticks = 0, targetTicks = 1;

	public TileSmelter()
	{
		super(400000, 160);

		inventory = new ItemStack[5];

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
		if (working)
		{
			int r = worldObj.rand.nextInt(100);

			if (r < 5)
				worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 0.5f, worldObj.rand.nextFloat() * 0.2f, false);
		}

		transferItems();

		if (canWork() && getEnergy() >= drainTick)
		{
			markDirty();
			markForUpdate();

			working = true;

			subtractEnergy(drainTick, false);

			targetTicks = SmelterRecipeHandler.instance.getSmeltingTicks(inventory[0], inventory[1], inventory[2]);

			if (ticks < targetTicks) ticks += 1;

			if (ticks >= targetTicks)
			{
				ItemStack result = SmelterRecipeHandler.instance.getSmeltingResult(inventory[0], inventory[1], inventory[2]);
				ItemStack resultCopy = result.copy();
				resultCopy.stackSize = result.stackSize;

				boolean called = false;

				for (int slot : getActiveSlots())
				{
					boolean vanilla = SmelterRecipeHandler.instance.isVanillaRecipe(getStackInSlot(slot));

					if (vanilla && !called)
					{
						called = true;

						resultCopy.stackSize *= getActiveSlots().size();
					}

					decrStackSize(slot, 1);
				}

				setInventorySlotContents(3, stack(inventory[3], resultCopy));

				working = false;

				ticks = 0;
				targetTicks = 1;
			}
		} else
		{
			markDirty();
			markForUpdate();

			ticks = 0;
			targetTicks = 1;
			working = false;
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

	private void transferItems()
	{
		for (EnumFacing outputFacing : itemOutputSides)
		{
			BlockPos offsetPos = pos.offset(outputFacing);

			if (worldObj.getTileEntity(offsetPos) != null && worldObj.getTileEntity(offsetPos) instanceof IInventory)
			{
				IInventory inventory = (IInventory) worldObj.getTileEntity(offsetPos);

				if (getStackInSlot(3) != null)
				{
					ItemConduitUtil.transferStack(getStackInSlot(3), inventory, outputFacing);

					if (getStackInSlot(3) != null && getStackInSlot(3).stackSize <= 0)
					{
						setInventorySlotContents(3, null);
					}
				}
			}
		}
	}

	private List<Integer> getActiveSlots()
	{
		List<Integer> slots = new ArrayList<>();

		for (int i = 0; i <= 2; i++)
		{
			if (inventory[i] != null && !slots.contains(i)) slots.add(i);
		}

		return slots;
	}

	public ItemStack stack(ItemStack in, ItemStack toStack)
	{
		ItemStack result;

		if (in != null)
		{
			result = in.copy();
			result.stackSize += toStack.stackSize;
		} else result = toStack.copy();

		return result;
	}

	public int getProgress()
	{
		return ticks * 100 / targetTicks;
	}

	private boolean canWork()
	{
		if (inventory[0] == null && inventory[1] == null && inventory[2] == null) return false;

		ItemStack result = SmelterRecipeHandler.instance.getSmeltingResult(inventory[0], inventory[1], inventory[2]);

		if (result == null) return false;
		if (inventory[3] == null) return true;
		if (!OreDictionary.itemMatches(inventory[3], result, true)) return false;

		for (ItemStack stack : Util.getStackArrayNoNull(new ItemStack[]{inventory[0], inventory[1], inventory[2]}))
		{
			int stackSize;
			if (SmelterRecipeHandler.instance.isVanillaRecipe(stack))
			{
				stackSize = inventory[3].stackSize + result.stackSize * getActiveSlots().size();
			} else stackSize = inventory[3].stackSize + result.stackSize;

			return stackSize <= getInventoryStackLimit() && stackSize <= inventory[3].getMaxStackSize();
		}

		return false;
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
		return index != 3 && SmelterRecipeHandler.instance.isValidStack(stack);
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
}

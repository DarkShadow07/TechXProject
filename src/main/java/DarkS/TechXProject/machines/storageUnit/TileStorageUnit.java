package DarkS.TechXProject.machines.storageUnit;

import DarkS.TechXProject.blocks.tile.TileBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileStorageUnit extends TileBase implements IInventory
{
	IItemHandler handlerAll;
	private ItemStack[] inventory;

	public TileStorageUnit()
	{
		inventory = new ItemStack[72];

		handlerAll = new InvWrapper(this);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) handlerAll;

		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
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

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
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

		super.readFromNBT(tag);
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
		return true;
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

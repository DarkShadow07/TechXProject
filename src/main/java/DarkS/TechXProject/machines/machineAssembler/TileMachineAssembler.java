package DarkS.TechXProject.machines.machineAssembler;

import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.conduit.item.ItemConduitUtil;
import DarkS.TechXProject.items.ItemMachineRecipe;
import DarkS.TechXProject.machines.recipeStamper.MachineRecipeType;
import DarkS.TechXProject.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

public class TileMachineAssembler extends TileBase implements ISidedInventory
{
	private static final int[] slotsIn = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
	private static final int[] slotsOut = {1};
	public boolean working = false;
	public int progress;
	private ItemStack[] inventory;

	public TileMachineAssembler()
	{
		inventory = new ItemStack[29];
	}

	@Override
	public void update()
	{
		if (inventory[0] == null && !worldObj.isRemote)
		{
			for (int i = 2; i < getSizeInventory(); ++i)
			{
				ItemStack itemstack = getStackInSlot(i);

				if (itemstack != null)
				{
					InventoryHelper.spawnItemStack(worldObj, pos.getX(), pos.getY(), pos.getZ(), itemstack);

					setInventorySlotContents(i, null);
				}
			}

			markDirty();
			markForUpdate();
		}

		assemble();
	}

	private void assemble()
	{
		markDirty();
		markForUpdate();

		if (working)
		{
			progress += 1;
		} else progress = 0;

		if (inventory[0] != null)
		{
			ItemMachineRecipe item = (ItemMachineRecipe) inventory[0].getItem();
			MachineRecipeType type = item.getType(inventory[0]);

			ItemStack[] newInv = inventory;
			newInv = ArrayUtils.removeAll(newInv, 0, 1);

			working = Util.isStackArrayExactEqual(newInv, type.inputs) && (ItemConduitUtil.canStack(inventory[1], type.out) || inventory[1] == null);

			if (progress >= 800)
			{
				working = false;

				setInventorySlotContents(1, Util.ItemStackUtil.stack(inventory[1], type.out));

				for (int i = 2; i < inventory.length; i++)
				{
					setInventorySlotContents(i, null);
				}
			}
		} else working = false;
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("progress", progress);
		data.setBoolean("active", working);

		tag.setTag("data", data);

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
		NBTTagCompound data = tag.getCompoundTag("data");

		progress = data.getInteger("progress");
		working = data.getBoolean("active");

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

	private boolean isInType(ItemStack stack, MachineRecipeType type)
	{
		if (stack != null && type != null)
		{
			for (ItemStack recipe : type.inputs)
			{
				if (OreDictionary.itemMatches(recipe, stack, true))
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side.equals(EnumFacing.DOWN)) return slotsOut;
		else return slotsIn;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isInType(itemStackIn, ((ItemMachineRecipe) inventory[0].getItem()).getType(inventory[0]));
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
		return inventory[0] != null && isInType(stack, ((ItemMachineRecipe) inventory[0].getItem()).getType(inventory[0]));
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

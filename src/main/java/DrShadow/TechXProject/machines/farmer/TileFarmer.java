package DrShadow.TechXProject.machines.farmer;

import DrShadow.TechXProject.api.energy.TileEnergyContainer;
import DrShadow.TechXProject.conduit.item.ItemConduitUtil;
import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.init.InitItems;
import DrShadow.TechXProject.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Random;

public class TileFarmer extends TileEnergyContainer implements ISidedInventory
{
	public static final int drainTick = 20;

	public static final int rad = 4;
	public static final int radHarvest = rad ^ 2;

	private int[] slots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

	private int x, xP;
	private int y;
	private int z, zP;

	private boolean started;

	private ItemStack[] inventory;

	public TileFarmer()
	{
		super(800000, 2048);

		inventory = new ItemStack[12];

		for (EnumFacing facing : EnumFacing.values())
		{
			setSideInput(facing);
		}
	}

	@Override
	public void update()
	{
		if (!started)
		{
			started = true;

			x = pos.getX() - radHarvest;
			y = pos.getY() + radHarvest * 4;
			z = pos.getZ() - radHarvest;

			xP = pos.getX() - rad;
			zP = pos.getZ() - rad;
		}

		if (getEnergy() >= drainTick)
		{
			if (!worldObj.isRemote)
			{
				harvest();
				plant();
			}

			subtractEnergy(drainTick, false);
		}
	}

	private void plant()
	{
		xP += 1;

		if (xP > pos.getX() + rad)
		{
			xP = pos.getX() - rad;

			zP += 1;

			if (zP > pos.getZ() + rad)
			{
				zP = pos.getZ() - rad;
			}
		}

		BlockPos pos = new BlockPos(xP, this.pos.getY(), zP);

		if (inventory[6] != null || inventory[7] != null)
		{
			Random r = new Random();

			if (r.nextInt(200) <= 1 && worldObj.getBlockState(pos).getBlock() != null && worldObj.getBlockState(pos).getBlock() instanceof IGrowable)
			{
				IGrowable growable = (IGrowable) worldObj.getBlockState(pos).getBlock();

				if (growable.canUseBonemeal(worldObj, worldObj.rand, pos, worldObj.getBlockState(pos)) && growable.canGrow(worldObj, pos, worldObj.getBlockState(pos), false))
				{
					worldObj.playAuxSFX(2005, new BlockPos(xP + 0.5, pos.getY(), zP + 0.5), 0);

					growable.grow(worldObj, worldObj.rand, pos, worldObj.getBlockState(pos));

					if (inventory[6] != null)
					{
						decrStackSize(6, 1);
					} else decrStackSize(7, 1);
				}
			}
		}

		IBlockState state = worldObj.getBlockState(pos);

		if (state.getBlock() instanceof BlockCrops)
		{
			BlockCrops crops = (BlockCrops) state.getBlock();

			if (crops.getMetaFromState(state) >= crops.func_185526_g())
			{
				for (ItemStack stack : state.getBlock().getDrops(worldObj, pos, state, 0))
				{
					if (ItemConduitUtil.transferStack(stack, this, EnumFacing.DOWN) == null)
					{
						return;
					}
				}

				worldObj.setBlockToAir(pos);
			}
		}

		for (int i = 2; i <= 5; i++)
		{
			ItemStack stack = getStackInSlot(i);

			if (stack != null)
			{
				if (stack.getItem() instanceof IPlantable)
				{
					IPlantable plant = (IPlantable) stack.getItem();

					if (worldObj.isAirBlock(pos))
					{
						worldObj.setBlockState(pos, plant.getPlant(worldObj, pos));

						decrStackSize(i, 1);
					}
				} else if (Block.getBlockFromItem(stack.getItem()) != null)
				{
					Block block = Block.getBlockFromItem(stack.getItem());

					if (block instanceof IGrowable)
					{
						if (worldObj.canBlockBePlaced(block, pos, true, EnumFacing.UP, null, stack))
						{
							worldObj.setBlockState(pos, block.getStateFromMeta(stack.getItemDamage()));

							decrStackSize(i, 1);
						}
					}
				}
			}
		}
	}


	private void harvest()
	{
		if (inventory[0] == null || inventory[0].getItemDamage() >= inventory[0].getMaxDamage())
		{
			if (inventory[0] != null && inventory[1] == null)
			{
				setInventorySlotContents(1, inventory[0]);
				setInventorySlotContents(0, null);
			}

			return;
		}

		if (isFull()) return;

		move();

		BlockPos pos = new BlockPos(x, y, z);

		IBlockState state = worldObj.getBlockState(pos);

		if ((state.getBlock().isLeaves(state, worldObj, pos)) || (state.getBlock().isWood(worldObj, pos)))
		{
			for (ItemStack stack : state.getBlock().getDrops(worldObj, pos, state, 0))
			{
				if (ItemConduitUtil.transferStack(stack, this, EnumFacing.DOWN) == null)
				{
					return;
				}
			}

			if (state.getBlock().isWood(worldObj, pos))
			{
				inventory[0].damageItem(1, new EntityCow(worldObj));
			}

			worldObj.setBlockToAir(pos);
		}
	}

	private boolean isFull()
	{
		if (inventory[8] == null || inventory[9] == null || inventory[10] == null || inventory[11] == null)
		{
			return false;
		} else for (int i = 8; i <= 11; i++)
		{
			if (inventory[i] != null && inventory[i].stackSize < inventory[i].getMaxStackSize())
			{
				return false;
			}
		}

		return true;
	}

	private void move()
	{
		BlockPos pos = new BlockPos(x, y, z);

		EnumFacing[] values = {EnumFacing.NORTH ,EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN};

		for (EnumFacing facing : values)
		{
			if (worldObj.getBlockState(pos.offset(facing)).getBlock().isLeaves(worldObj.getBlockState(pos.offset(facing)), worldObj, pos.offset(facing)))
			{
				x = pos.getX() + facing.getFrontOffsetX();
				y = pos.getY() + facing.getFrontOffsetY();
				z = pos.getZ() + facing.getFrontOffsetZ();
			} else if (Arrays.asList(values).stream().allMatch(facing1 -> !worldObj.getBlockState(pos.offset(facing1)).getBlock().isLeaves(worldObj.getBlockState(pos.offset(facing1)), worldObj, pos.offset(facing1))))
			{
				if (worldObj.getBlockState(pos.offset(facing)).getBlock().isWood(worldObj, pos.offset(facing)))
				{
					x = pos.getX() + facing.getFrontOffsetX();
					y = pos.getY() + facing.getFrontOffsetY();
					z = pos.getZ() + facing.getFrontOffsetZ();
				} else if (Arrays.asList(EnumFacing.values()).stream().allMatch(facing1 -> !worldObj.getBlockState(pos.offset(facing1)).getBlock().isWood(worldObj, pos.offset(facing1))))
				{
					y += 1;

					if (y >= this.pos.getY() + radHarvest * 4)
					{
						y = this.pos.getY() - 1;

						x += 1;

						if (x > this.pos.getX() + radHarvest)
						{
							x = this.pos.getX() - radHarvest;

							z += 1;

							if (z > this.pos.getZ() + radHarvest)
							{
								z = this.pos.getZ() - radHarvest;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		super.toNBT(tag);

		NBTTagCompound posTag = new NBTTagCompound();

		posTag.setInteger("x", x);
		posTag.setInteger("y", y);
		posTag.setInteger("z", z);

		posTag.setInteger("xP", xP);
		posTag.setInteger("zP", zP);

		posTag.setBoolean("started", started);

		tag.setTag("harvesting", posTag);

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

		NBTTagCompound posTag = tag.getCompoundTag("harvesting");

		x = posTag.getInteger("x");
		y = posTag.getInteger("y");
		z = posTag.getInteger("z");

		xP = posTag.getInteger("xP");
		zP = posTag.getInteger("zP");

		started = posTag.getBoolean("started");

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
		return slots;
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
		switch (index)
		{
			case 0:
				return stack.getItem().isItemTool(stack);
			case 1:
				return false;
			case 2:
			case 3:
			case 4:
			case 5:
				if (stack.getItem() instanceof ItemBlock)
				{
					return ((ItemBlock) stack.getItem()).getBlock() instanceof IGrowable;
				} else return stack.getItem() instanceof IPlantable;
			case 6:
			case 7:
				return OreDictionary.itemMatches(new ItemStack(Items.dye, 1, 15), stack, true);
			case 8:
				return true;
			case 9:
				return true;
			case 10:
				return true;
			case 11:
				return true;
			default:
				return false;
		}
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

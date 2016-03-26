package DrShadow.TechXProject.conduit.item;

import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.conduit.item.filter.EnumFilterType;
import DrShadow.TechXProject.conduit.item.filter.IFilterElement;
import DrShadow.TechXProject.conduit.item.filter.IItemStackFilter;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterBase;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterMod;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterName;
import DrShadow.TechXProject.conduit.item.filter.item.ItemFilterOreDict;
import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import DrShadow.TechXProject.tileEntities.ModTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TileConduitItem extends ModTileEntity implements INetworkElement, ISidedInventory, IFilterElement
{
	public int size = 1;
	public ItemStack[] itemInventory;
	private ConduitNetwork network;
	private IInventory inventory = null;
	private boolean isInput = false, isOutput = false, active = true;

	private IItemStackFilter filter;

	private float cooldown = 1;
	private float speed = 0.2f;

	public TileConduitItem()
	{
		itemInventory = new ItemStack[size];
	}

	@Override
	public void update()
	{
		updateInventory();

		if (!worldObj.isRemote)
		{
			cooldown -= speed;

			if (getStackInSlot(0) != null)
			{
				setFilter((IItemStackFilter) getStackInSlot(0).getItem());
			} else
			{
				setFilter(null);
			}

			if (cooldown <= 0 && isInput())
			{
				cooldown = 1;

				doTransfer();
			}
		}
	}

	public void updateInventory()
	{
		net.minecraft.tileentity.TileEntity tile = worldObj.getTileEntity(pos.offset(EnumFacing.getFront(getBlockMetadata())));

		if (tile != null && tile instanceof IInventory)
		{
			inventory = (IInventory) tile;
		} else inventory = null;
	}

	public void doTransfer()
	{
		if (!hasNetwork() || network.getOutputContainers() == null) return;

		List<INetworkElement> elements = ItemConduitUtil.sortElements(this);

		List<INetworkElement> outputs = ItemConduitUtil.getOutputs(elements);

		if (!elements.isEmpty() && !outputs.isEmpty())
		{
			for (INetworkElement output : outputs)
			{
				if (output.hasInventory() && hasInventory() && !getTransferItem().isEmpty() && output.isActive() && isActive())
				{
					for (int i = 0; i < getTransferItem().size(); i++)
					{
						ItemStack stack = getTransferItem().get(i);

						if (!ItemConduitUtil.canTransferWithFilter(this, output, stack))
							continue;

						ItemStack remaining;

						remaining = transfer(stack, output.getInventory());

						setStackInInventory(inventory, remaining);

						return;
					}
				}
			}
		}
	}

	public ItemStack transfer(ItemStack inputStack, IInventory inventoryTo)
	{
		int allowedAmount = Math.min(4, inputStack.stackSize);

		if (allowedAmount <= 0)
		{
			return null;
		}

		ItemStack testStack = inputStack.copy();
		testStack.stackSize = allowedAmount;

		ItemStack remainderStack;

		remainderStack = ItemConduitUtil.transferStack(testStack, inventoryTo, EnumFacing.getFront(getBlockMetadata()).getOpposite());

		int changeAmount = allowedAmount - (remainderStack == null ? 0 : remainderStack.stackSize);
		testStack = inputStack.copy();
		testStack.stackSize -= changeAmount;

		net.minecraft.tileentity.TileEntity tile = (net.minecraft.tileentity.TileEntity) inventoryTo;
		World world = tile.getWorld();
		BlockPos pos = tile.getPos();
		world.markBlockForUpdate(pos);

		return testStack;
	}

	public List<ItemStack> getTransferItem()
	{
		List<ItemStack> result = new ArrayList<>();

		if (hasInventory())
		{
			if (inventory instanceof ISidedInventory)
			{
				ISidedInventory sidedInventory = (ISidedInventory) inventory;

				for (int slot : sidedInventory.getSlotsForFace(EnumFacing.getFront(getBlockMetadata()).getOpposite()))
				{
					if (sidedInventory.getStackInSlot(slot) != null && !result.contains(sidedInventory.getStackInSlot(slot)))
						result.add(sidedInventory.getStackInSlot(slot));
				}
			} else
			{
				for (int i = 0; i < inventory.getSizeInventory(); i++)
				{
					if (inventory.getStackInSlot(i) != null && !result.contains(inventory.getStackInSlot(i)))
						result.add(inventory.getStackInSlot(i));
				}
			}
		}

		return result;
	}

	public void setStackInInventory(IInventory inv, ItemStack stack)
	{
		if (ItemConduitUtil.getStackSlot(inv, stack) != -1)
		{
			int slot = ItemConduitUtil.getStackSlot(inv, stack);

			if (slot != -1)
			{
				inv.setInventorySlotContents(slot, stack.stackSize <= 0 ? null : stack);
			}
		}
	}

	@Override
	public ConduitNetwork getNetwork()
	{
		return network;
	}

	@Override
	public void setNetwork(ConduitNetwork network)
	{
		this.network = network;
	}

	@Override
	public ConduitNetwork addToNetwork(INetworkElement toAdd)
	{
		network.addToNetwork(toAdd);

		return network;
	}

	@Override
	public ConduitNetwork removeFromNetwork(INetworkElement toRemove)
	{
		network.removeFromNetwork(toRemove);

		return network;
	}

	@Override
	public net.minecraft.tileentity.TileEntity getTile()
	{
		return this;
	}

	public IInventory getInventory()
	{
		return inventory;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return (TileEntity) inventory;
	}

	@Override
	public boolean hasInventory()
	{
		return inventory != null;
	}

	@Override
	public int distanceTo(TileEntity to)
	{
		return (int) getTile().getPos().distanceSq(to.getPos());
	}

	@Override
	public INetworkContainer getController()
	{
		return network.getController();
	}

	@Override
	public IItemStackFilter getFilter()
	{
		return filter;
	}

	@Override
	public void setFilter(IItemStackFilter filter)
	{
		this.filter = filter;
	}

	@Override
	public boolean isInput()
	{
		return isInput;
	}

	public void setInput(boolean input)
	{
		isInput = input;
	}

	@Override
	public boolean isOutput()
	{
		return isOutput;
	}

	public void setOutput(boolean output)
	{
		isOutput = output;
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	@Override
	public void setActive(boolean act)
	{
		active = act;
	}

	@Override
	public boolean hasNetwork()
	{
		return network != null;
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		NBTTagCompound booleans = new NBTTagCompound();

		booleans.setBoolean("input", isInput);
		booleans.setBoolean("output", isOutput);

		tag.setTag("booleans", booleans);

		NBTTagList items = new NBTTagList();

		for (int i = 0; i < itemInventory.length; i++)
		{
			if ((itemInventory[i] != null))
			{
				NBTTagCompound data = new NBTTagCompound();
				data.setByte("Slot", (byte) i);
				itemInventory[i].writeToNBT(data);
				items.appendTag(data);
			}
		}

		tag.setTag("Items", items);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		NBTTagCompound booleans = tag.getCompoundTag("booleans");

		isInput = booleans.getBoolean("input");
		isOutput = booleans.getBoolean("output");

		NBTTagList tags = tag.getTagList("Items", 10);
		itemInventory = new ItemStack[getSizeInventory()];

		for (int i = 0; i < tags.tagCount(); i++)
		{
			NBTTagCompound data = tags.getCompoundTagAt(i);
			byte j = data.getByte("Slot");

			if (j >= 0 && j < itemInventory.length)
			{
				itemInventory[j] = ItemStack.loadItemStackFromNBT(data);
			}
		}
	}

	@Override
	public List<ItemStack> getFilteredStacks()
	{
		return filter.getFilteredItems(getStackInSlot(0));
	}

	@Override
	public EnumFilterType getType()
	{
		return filter.getFilterType();
	}

	@Override
	public boolean hasFilter()
	{
		return filter == null ? false : true;
	}

	@Override
	public boolean isInFilter(ItemStack stack)
	{
		if (hasFilter())
		{
			switch (filter.getFilterType())
			{
				case BASIC:
					ItemFilterBase filterBase = (ItemFilterBase) filter;
					filterBase.getFilteredItems(stack);

					for (ItemStack itemStack : filterBase.getFilteredItems(stack))
					{
						if (itemStack.isItemEqual(stack))
						{
							return true;
						} else return false;
					}
					break;
				case MOD:
					ItemFilterMod modFilter = (ItemFilterMod) filter;
					modFilter.getFilteredItems(getStackInSlot(0));
					return modFilter.isValid(stack);
				case NAME:
					ItemFilterName nameFilter = (ItemFilterName) filter;
					nameFilter.getFilteredItems(getStackInSlot(0));
					return nameFilter.isValid(stack);
				case ORE_DICTIONARY:
					ItemFilterOreDict oreDictFilter = (ItemFilterOreDict) filter;
					oreDictFilter.getFilteredItems(getStackInSlot(0));
					return oreDictFilter.isValid(stack);
			}
		}

		return true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return size;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return itemInventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (itemInventory[index] != null)
		{
			if (!worldObj.isRemote)
				worldObj.markBlockForUpdate(this.pos);

			if (itemInventory[index].stackSize <= count)
			{
				ItemStack itemStack = itemInventory[index];
				itemInventory[index] = null;
				markDirty();
				return itemStack;
			}

			ItemStack itemStack = itemInventory[index].splitStack(count);
			if (itemInventory[index].stackSize == 0)
				itemInventory[index] = null;

			markDirty();
			return itemStack;
		}

		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		if (itemInventory[slot] != null)
		{
			ItemStack itemStack = itemInventory[slot];
			setInventorySlotContents(slot, null);
			return itemStack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		itemInventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
		markDirty();
		if (!worldObj.isRemote)
			worldObj.markBlockForUpdate(this.pos);
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
		return stack.getItem() instanceof IItemStackFilter;
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
		this.itemInventory = new ItemStack[size];
	}

	@Override
	public String getName()
	{
		return "Conduit";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return null;
	}
}

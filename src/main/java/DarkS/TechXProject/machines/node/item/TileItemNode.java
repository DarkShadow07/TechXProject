package DarkS.TechXProject.machines.node.item;

import DarkS.TechXProject.api.IWrench;
import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.blocks.tile.highlight.IHighlightProvider;
import DarkS.TechXProject.blocks.tile.highlight.SelectionBox;
import DarkS.TechXProject.machines.node.item.filter.EnumFilterType;
import DarkS.TechXProject.machines.node.item.filter.IFilterElement;
import DarkS.TechXProject.machines.node.item.filter.IItemStackFilter;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterBase;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterMod;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterName;
import DarkS.TechXProject.machines.node.item.filter.item.ItemFilterOreDict;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileItemNode extends TileBase implements INetworkElement<TileItemNode>, ISidedInventory, IFilterElement, ITickable, IHighlightProvider
{
	public int size = 1;
	private ItemStack[] itemInventory;
	private NodeNetwork network;
	private IInventory inventory = null;
	private boolean isInput = false, isOutput = false;

	private IItemStackFilter filter;

	private float cooldown = 1;
	private float speed = 0.2f;

	public TileItemNode()
	{
		itemInventory = new ItemStack[size];
	}

	@Override
	public void update()
	{
		if (!isActive()) return;

		updateInventory();

		if (Util.player() != null && Util.player().getHeldItemMainhand() != null && Util.player().getHeldItemMainhand().getItem() instanceof IWrench)
		{
			Random r = new Random();

			float x = pos.getX() + r.nextFloat();
			float y = pos.getY() + r.nextFloat();
			float z = pos.getZ() + r.nextFloat();

			if (isInput())
				worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, x, y, z, -1, 1, 0, 0);
			else
				worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, x, y, z, -1, -1, 1, 0);

		}

		if (!worldObj.isRemote)
		{
			cooldown -= speed;

			if (getStackInSlot(0) != null)
				setFilter((IItemStackFilter) getStackInSlot(0).getItem());
			else
				setFilter(null);

			if (cooldown <= 0 && isInput())
			{
				cooldown = 1;

				doTransfer();
			}
		}
	}

	public void updateInventory()
	{
		TileEntity tile = worldObj.getTileEntity(pos.offset(EnumFacing.getFront(getBlockMetadata())));

		if (tile != null && tile instanceof IInventory)
		{
			inventory = (IInventory) tile;
		} else inventory = null;
	}

	public void doTransfer()
	{
		if (!hasNetwork() || network.getOutputContainers() == null) return;

		List<INetworkElement> elements = NodeUtil.sortElements(this);

		List<INetworkElement> outputs = NodeUtil.getOutputs(elements);

		if (!elements.isEmpty() && !outputs.isEmpty())
		{
			for (INetworkElement output : outputs)
			{
				if (output instanceof IFilterElement && output.isAttached() && isAttached() && !getTransferItem().isEmpty() && output.isActive() && isActive())
				{
					for (int i = 0; i < getTransferItem().size(); i++)
					{
						ItemStack stack = getTransferItem().get(i);

						if (!NodeUtil.canTransferWithFilter(this, output, stack))
							continue;

						ItemStack remaining;

						remaining = transfer(stack, (IInventory) output.getAttachedTile());

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

		remainderStack = NodeUtil.transferStack(testStack, inventoryTo, EnumFacing.getFront(getBlockMetadata()).getOpposite());

		int changeAmount = allowedAmount - (remainderStack == null ? 0 : remainderStack.stackSize);
		testStack = inputStack.copy();
		testStack.stackSize -= changeAmount;

		net.minecraft.tileentity.TileEntity tile = (net.minecraft.tileentity.TileEntity) inventoryTo;
		World world = tile.getWorld();
		BlockPos pos = tile.getPos();

		world.markBlockRangeForRenderUpdate(pos, pos);

		return testStack;
	}

	public List<ItemStack> getTransferItem()
	{
		List<ItemStack> result = new ArrayList<>();

		if (isAttached())
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
		if (NodeUtil.getStackSlot(inv, stack) != -1)
		{
			int slot = NodeUtil.getStackSlot(inv, stack);

			if (slot != -1)
			{
				inv.setInventorySlotContents(slot, stack.stackSize <= 0 ? null : stack);
			}
		}
	}

	@Override
	public NodeNetwork getNetwork()
	{
		return network;
	}

	@Override
	public void setNetwork(NodeNetwork network)
	{
		this.network = network;
	}

	@Override
	public TileItemNode getTile()
	{
		return this;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return (TileEntity) inventory;
	}

	@Override
	public boolean isAttached()
	{
		return inventory != null;
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
		return hasNetwork() && network.getController().isActive();
	}

	@Override
	public boolean hasNetwork()
	{
		return network != null;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
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

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
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

		super.readFromNBT(tag);
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
		return filter != null;
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
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
	public SelectionBox[] getBoxes()
	{
		return new SelectionBox[]{new SelectionBox(0, new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375), new AxisAlignedBB(0.1875, 0.125, 0.1875, 0.8125, 0.1875, 0.8125)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite())};
	}

	@Override
	public SelectionBox[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end)
	{
		return getBoxes();
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

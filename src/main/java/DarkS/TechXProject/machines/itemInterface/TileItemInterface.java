package DarkS.TechXProject.machines.itemInterface;

import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.machines.node.item.TileItemNode;
import DarkS.TechXProject.machines.node.network.NetworkUtil;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileItemInterface extends TileEnergyContainer implements IInventory
{
	public List<ItemStack> stacks = new ArrayList<>();
	private ItemStack[] inventory;

	private NodeNetwork network;

	public TileItemInterface()
	{
		super(100000, 500);

		inventory = new ItemStack[getSizeInventory()];
	}

	public void extractItemFronNetwork(@Nonnull ItemStack stack)
	{
		if (attachedNetwork() != null)
			Logger.info(NetworkUtil.getFromNetwork(stack, attachedNetwork()).stackSize);
	}

	@Override
	public void update()
	{
		EnumFacing facing = EnumFacing.getFront(getBlockMetadata()).getOpposite();

		if (worldObj.getTileEntity(pos.offset(facing)) != null && worldObj.getTileEntity(pos.offset(facing)) instanceof INetworkElement)
		{
			INetworkElement attached = (INetworkElement) worldObj.getTileEntity(pos.offset(facing));

			if (!attached.hasNetwork() || !attached.isActive() || attached.getNetwork().getElements().isEmpty())
			{
				PacketHandler.sendToAllAround(new PacketSendInventory(this), this);
				return;
			}

			network = attached.getNetwork();

			if (worldObj.isRemote) return;

			stacks.clear();

			for (INetworkElement element : attached.getNetwork().getElements())
				if (element instanceof TileItemNode && element.getAttachedTile() != null && element.getAttachedTile() != this && element.getAttachedTile() instanceof IInventory)
					for (int i = 0; i < ((IInventory) element.getAttachedTile()).getSizeInventory(); i++)
						if (((IInventory) element.getAttachedTile()).getStackInSlot(i) != null)
							stacks.add(((IInventory) element.getAttachedTile()).getStackInSlot(i));

			PacketHandler.sendToAllAround(new PacketSendInventory(this), this);
		}

		for (int i = 0; i < inventory.length; i++)
			if (stacks.size() > i)
				inventory[i] = stacks.get(i);
	}

	public NodeNetwork attachedNetwork()
	{
		return network;
	}

	@Override
	public int getSizeInventory()
	{
		return 54;
	}

	@Nullable
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
}

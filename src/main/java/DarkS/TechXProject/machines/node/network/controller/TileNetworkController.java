package DarkS.TechXProject.machines.node.network.controller;

import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.INetworkRelay;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.machines.node.network.NetworkUtil;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.packets.PacketSendNetwork;
import DarkS.TechXProject.util.Logger;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileNetworkController extends TileEnergyContainer implements INetworkContainer, ITickable
{
	private NodeNetwork network;

	private int drain;

	private boolean wasActive = false;

	public TileNetworkController()
	{
		super(25000, 250);

		initNetwork();

		for (EnumFacing facing : EnumFacing.values())
			setSideInput(facing);
	}

	@Override
	public void update()
	{
		drain = 8 + network.getElements().size() * 4;

		if (wasActive && !isActive())
		{
			markDirty();
			markForUpdate();
		}

		if (!isActive())
			return;

		wasActive = true;

		subtractEnergy(drain, false);

		searchNetwork();
	}

	@Override
	public boolean isActive()
	{
		return subtractEnergy(drain, true);
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
	public void searchNetwork()
	{
		if (!worldObj.isRemote)
		{
			network.clear();

			for (TileEntity tile : getTilesOnSides())
				if (tile != null && tile instanceof INetworkRelay)
				{
					((INetworkRelay) tile).getElements().forEach(element ->
					{
						if (element instanceof INetworkRelay)
							((INetworkRelay) element).getElements().forEach(this::addToNetwork);
						else addToNetwork(element);
					});
				}

			PacketHandler.sendToAllAround(new PacketSendNetwork(this), this);
			network.getElements().forEach(element -> element.setNetwork(network));
			network.getElements().forEach(element -> PacketHandler.sendToAllAround(new PacketSendNetwork((TileBase) element), element.getTile()));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		NetworkUtil.writeNetworkNBT(tag, network);

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		network = NetworkUtil.readNetwork(worldObj, NetworkUtil.readNetworkNBT(tag), this);

		PacketHandler.sendToAllAround(new PacketSendNetwork(this), this);

		super.readFromNBT(tag);
	}

	@Override
	public NodeNetwork addToNetwork(INetworkElement toAdd)
	{
		if (!network.isInNetwork(toAdd))
			network.addToNetwork(toAdd);

		return network;
	}

	@Override
	public NodeNetwork removeFromNetwork(INetworkElement toRemove)
	{
		network.removeFromNetwork(toRemove);

		toRemove.setNetwork(null);

		return network;
	}

	@Override
	public void updateNetwork()
	{

	}

	@Override
	public TileEntity getController()
	{
		return this;
	}

	@Override
	public void initNetwork()
	{
		if (network == null)
			network = new NodeNetwork(this);
	}

	@Override
	public void drawLines()
	{
		for (INetworkElement element : network.getElements())
		{
			if (element instanceof INetworkRelay)
			{
				((INetworkRelay) element).drawLines();
			}
		}
	}
}

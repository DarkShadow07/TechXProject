package DarkS.TechXProject.node.network.controller;

import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.INetworkRelay;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.node.network.NetworkUtil;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.packets.PacketSendNetwork;
import DarkS.TechXProject.packets.PacketUpdateEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileNetworkController extends TileEnergyContainer implements INetworkContainer, ITickable
{
	private NodeNetwork network;

	private int drain;

	public TileNetworkController()
	{
		super(25000, 2500);

		initNetwork();

		for (EnumFacing facing : EnumFacing.values())
			setSideInput(facing);
	}

	@Override
	public void update()
	{
		if (!isActive()) return;
		searchNetwork();

		drain = 8 + network.getElements().size() * 4;

		if (!worldObj.isRemote)
		{
			subtractEnergy(drain, false);

			PacketHandler.sendToAllAround(new PacketUpdateEnergy(getEnergy(), this), this);
		}
	}

	@Override
	public boolean isActive()
	{
		return getEnergy() >= drain;
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
		network.clear();

		for (TileEntity tile : getTilesOnSides())
			if (tile != null && tile instanceof INetworkRelay)
				((INetworkRelay) tile).getElements().forEach(this::addToNetwork);

		for (INetworkElement element : network.getElements())
			if (element != null)
			{
				if (element instanceof INetworkRelay)
				{
					INetworkRelay relay = (INetworkRelay) element;

					for (INetworkElement relayElement : relay.getElements())
						if (relayElement != null && !network.isInNetwork(relayElement))
							addToNetwork(relayElement);
				}

				element.setNetwork(network);
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

		super.readFromNBT(tag);
	}

	@Override
	public NodeNetwork addToNetwork(INetworkElement toAdd)
	{
		if (!network.isInNetwork(toAdd))
		{
			network.addToNetwork(toAdd);

			toAdd.setNetwork(network);

			if (!worldObj.isRemote)
				PacketHandler.sendToAllAround(new PacketSendNetwork(toAdd), toAdd.getTile());
		}

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

package DarkS.TechXProject.node.network.controller;

import DarkS.TechXProject.api.network.ConduitNetwork;
import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.INetworkRelay;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.node.network.NetworkUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TileNetworkController extends TileBase implements INetworkContainer, ITickable
{
	private List<BlockPos> elementsPos = new ArrayList<>();
	private ConduitNetwork network;

	public TileNetworkController()
	{
		initNetwork();
	}

	@Override
	public void update()
	{
		searchNetwork();
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
	public void searchNetwork()
	{
		network = NetworkUtil.networkFromPosList(worldObj, elementsPos, this);

		searchRelays();
	}

	@Override
	public void searchRelays()
	{
		for (INetworkElement element : network.getElements())
		{
			if (element != null && element instanceof INetworkRelay)
			{
				INetworkRelay relay = (INetworkRelay) element;

				for (INetworkElement relayElement : relay.getElements())
				{
					if (relayElement != null && !network.isInNetwork(relayElement))
					{
						addToNetwork(relayElement);
					}
				}
			}
		}
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		NetworkUtil.writeNetworkNBT(tag, network);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		elementsPos = NetworkUtil.readNetworkNBT(tag);
	}

	@Override
	public ConduitNetwork addToNetwork(INetworkElement toAdd)
	{
		if (!network.isInNetwork(toAdd))
		{
			elementsPos.add(toAdd.getTile().getPos());
			network.addToNetwork(toAdd);

			toAdd.setNetwork(network);
		}

		return network;
	}

	@Override
	public ConduitNetwork removeFromNetwork(INetworkElement toRemove)
	{
		elementsPos.remove(toRemove.getTile().getPos());
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
		{
			network = new ConduitNetwork(this);
		}
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

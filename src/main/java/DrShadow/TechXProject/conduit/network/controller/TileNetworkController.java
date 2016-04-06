package DrShadow.TechXProject.conduit.network.controller;

import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.api.network.INetworkRelay;
import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import DrShadow.TechXProject.conduit.network.NetworkUtil;
import DrShadow.TechXProject.fx.EntityReddustFXT;
import DrShadow.TechXProject.tileEntities.ModTileEntity;
import DrShadow.TechXProject.util.Util;
import DrShadow.TechXProject.util.VectorUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class TileNetworkController extends ModTileEntity implements INetworkContainer
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
						network.addToNetwork(relayElement);
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
		}

		return network;
	}

	@Override
	public ConduitNetwork removeFromNetwork(INetworkElement toRemove)
	{
		elementsPos.remove(toRemove.getTile().getPos());
		network.removeFromNetwork(toRemove);

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

			for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(pos).addVector(0.5, 0.5, 0.5), new Vec3d(element.getTile().getPos()).addVector(0.5, 0.5, 0.5), 0.1f))
			{
				Util.spawnEntityFX(new EntityReddustFXT(worldObj, vec.xCoord, vec.yCoord, vec.zCoord, 0.01f, 0, 1));
			}
		}
	}
}

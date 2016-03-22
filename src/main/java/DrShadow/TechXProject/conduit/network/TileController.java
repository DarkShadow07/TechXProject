package DrShadow.TechXProject.conduit.network;

import DrShadow.TechXProject.tileEntities.ModTileEntity;
import net.minecraft.util.BlockPos;

public class TileController extends ModTileEntity implements INetworkContainer
{
	private ConduitNetwork network;

	public TileController()
	{

	}

	@Override
	public void update()
	{
		initNetwork();
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
		network.clear();

		int rad = 16;

		for (int x = pos.getX() - rad; x < pos.getX() + rad; x++)
			for (int y = pos.getY() - rad / 2; y < pos.getY() + rad / 2; y++)
				for (int z = pos.getZ() - rad; z < pos.getZ() + rad; z++)
				{
					net.minecraft.tileentity.TileEntity tile = worldObj.getTileEntity(new BlockPos(x, y, z));

					if (tile != null)
					{
						if (tile instanceof INetworkElement)
						{
							INetworkElement element = (INetworkElement) tile;

							network.addToNetwork(element);

							for (INetworkElement networkElement : network.getElements())
							{
								networkElement.setNetwork(network);
							}
						}
					}
				}
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
	public void updateNetwork()
	{
		network.update();
	}

	@Override
	public net.minecraft.tileentity.TileEntity getController()
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
}

package DrShadow.TechXProject.conduit.network.relay;

import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.api.network.INetworkRelay;
import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import DrShadow.TechXProject.fx.EntityReddustFXT;
import DrShadow.TechXProject.items.ItemWrench;
import DrShadow.TechXProject.tileEntities.ModTileEntity;
import DrShadow.TechXProject.util.Util;
import DrShadow.TechXProject.util.VectorUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class TileNetworkRelay extends ModTileEntity implements INetworkRelay, INetworkElement
{
	public static int relayRad = 8;

	public TileNetworkRelay()
	{

	}

	@Override
	public List<INetworkElement> getElements()
	{
		drawArea();

		List<INetworkElement> elements = new ArrayList<>();

		for (int x = pos.getX() - relayRad; x < pos.getX() + relayRad; x++)
		{
			for (int y = pos.getY() - relayRad / 2; y < pos.getY() + relayRad / 2; y++)
			{
				for (int z = pos.getZ() - relayRad; z < pos.getZ() + relayRad; z++)
				{
					TileEntity tile = worldObj.getTileEntity(new BlockPos(x, y, z));

					if (tile != null && tile instanceof INetworkElement)
					{
						if (!elements.contains(tile))
						{
							elements.add((INetworkElement) tile);
						}
					}
				}
			}
		}

		return elements;
	}

	@Override
	public void drawArea()
	{
		if (Util.player() != null && Util.player().getHeldItem() != null && Util.player().getHeldItem().getItem() instanceof ItemWrench)
		{
			Util.spawnParticlesOnBorder(pos.getX() - relayRad, pos.getY() - relayRad / 2, pos.getZ() - relayRad, pos.getX() + relayRad, pos.getY() + relayRad / 2, pos.getZ() + relayRad, worldObj, 0.01f, 0.45f, 0.55f);
		}
	}

	@Override
	public void drawLines()
	{
		for (INetworkElement element : getElements())
		{
			for (Vec3 vec : VectorUtil.dotsOnRay(new Vec3(pos).addVector(0.5, 0.5, 0.5), new Vec3(element.getTile().getPos()).addVector(0.5, 0.5, 0.5), 0.1f))
			{
				Util.spawnEntityFX(new EntityReddustFXT(worldObj, vec.xCoord, vec.yCoord, vec.zCoord, 0.01f, 0.45f, 0.55f));
			}
		}
	}

	@Override
	public ConduitNetwork getNetwork()
	{
		return null;
	}

	@Override
	public void setNetwork(ConduitNetwork network)
	{

	}

	@Override
	public ConduitNetwork addToNetwork(INetworkElement toAdd)
	{
		return null;
	}

	@Override
	public ConduitNetwork removeFromNetwork(INetworkElement toRemove)
	{
		return null;
	}

	@Override
	public TileEntity getTile()
	{
		return this;
	}

	@Override
	public IInventory getInventory()
	{
		return null;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return null;
	}

	@Override
	public boolean hasInventory()
	{
		return false;
	}

	@Override
	public int distanceTo(TileEntity to)
	{
		return 0;
	}

	@Override
	public INetworkContainer getController()
	{
		return null;
	}

	@Override
	public boolean isInput()
	{
		return false;
	}

	@Override
	public boolean isOutput()
	{
		return false;
	}

	@Override
	public boolean isActive()
	{
		return false;
	}

	@Override
	public void setActive(boolean act)
	{

	}

	@Override
	public boolean hasNetwork()
	{
		return false;
	}
}

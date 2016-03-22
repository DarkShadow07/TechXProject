package DrShadow.TechXProject.conduit.network;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public interface INetworkElement
{
	ConduitNetwork getNetwork();

	void setNetwork(ConduitNetwork network);

	ConduitNetwork addToNetwork(INetworkElement toAdd);

	ConduitNetwork removeFromNetwork(INetworkElement toRemove);

	net.minecraft.tileentity.TileEntity getTile();

	IInventory getInventory();

	TileEntity getAttachedTile();

	boolean hasInventory();

	int distanceTo(TileEntity to);

	INetworkContainer getController();

	boolean isInput();

	boolean isOutput();

	boolean isActive();

	void setActive(boolean act);

	boolean hasNetwork();
}

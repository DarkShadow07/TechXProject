package DrShadow.TechXProject.api.network;

import DrShadow.TechXProject.conduit.network.ConduitNetwork;

public interface INetworkContainer
{
	ConduitNetwork getNetwork();

	void setNetwork(ConduitNetwork network);

	void searchNetwork();

	void searchRelays();

	ConduitNetwork addToNetwork(INetworkElement toAdd);

	ConduitNetwork removeFromNetwork(INetworkElement toRemove);

	void updateNetwork();

	net.minecraft.tileentity.TileEntity getController();

	void initNetwork();
}

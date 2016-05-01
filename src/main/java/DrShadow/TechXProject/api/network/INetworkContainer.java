package DrShadow.TechXProject.api.network;

import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import net.minecraft.tileentity.TileEntity;

public interface INetworkContainer
{
	ConduitNetwork getNetwork();

	void setNetwork(ConduitNetwork network);

	void searchNetwork();

	void searchRelays();

	ConduitNetwork addToNetwork(INetworkElement toAdd);

	ConduitNetwork removeFromNetwork(INetworkElement toRemove);

	void updateNetwork();

	TileEntity getController();

	void initNetwork();

	void drawLines();
}

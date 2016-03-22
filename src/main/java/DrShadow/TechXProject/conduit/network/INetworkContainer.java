package DrShadow.TechXProject.conduit.network;

public interface INetworkContainer
{
	ConduitNetwork getNetwork();

	void setNetwork(ConduitNetwork network);

	void searchNetwork();

	ConduitNetwork addToNetwork(INetworkElement toAdd);

	ConduitNetwork removeFromNetwork(INetworkElement toRemove);

	void updateNetwork();

	net.minecraft.tileentity.TileEntity getController();

	void initNetwork();
}

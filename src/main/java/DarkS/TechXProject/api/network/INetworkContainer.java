package DarkS.TechXProject.api.network;

import net.minecraft.tileentity.TileEntity;

public interface INetworkContainer<T extends TileEntity>
{
	/**
	 * Returns the ConduitNetwork
	 *
	 * @return ConduitNetwork
	 */
	ConduitNetwork getNetwork();

	/**
	 * Sets the ConduitNetwork
	 *
	 * @param network ConduitNetwork to set
	 */
	void setNetwork(ConduitNetwork network);

	/**
	 * Called when should search {@link INetworkElement}
	 */
	void searchNetwork();

	/**
	 * Called when should search {@link INetworkRelay}
	 */
	void searchRelays();

	/**
	 * Adds a {@link INetworkElement} to the ConduitNetwork
	 *
	 * @param toAdd INetworkElement to add to the ConduitNetwork
	 * @return {@link ConduitNetwork} with the INetworkElement added to it
	 */
	ConduitNetwork addToNetwork(INetworkElement toAdd);

	/**
	 * Removes a {@link INetworkElement} from the ConduitNetwork
	 *
	 * @param toRemove INetworkElement to remove from the ConduitNetwork
	 * @return {@link ConduitNetwork} without the INetworkElement removed from it
	 */
	ConduitNetwork removeFromNetwork(INetworkElement toRemove);

	/**
	 * Called when should update the ConduitNetwork
	 */
	void updateNetwork();

	/**
	 * Returns the {@link TileEntity} that contains the ConduitNetwork
	 *
	 * @return
	 */
	T getController();

	/**
	 * Called when should init the ConduitNetwork
	 */
	void initNetwork();

	/**
	 * Called when should draw lines to all the {@link INetworkElement} in the ConduitNetwork
	 */
	void drawLines();
}

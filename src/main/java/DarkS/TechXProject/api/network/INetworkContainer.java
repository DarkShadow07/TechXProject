package DarkS.TechXProject.api.network;

import net.minecraft.tileentity.TileEntity;

public interface INetworkContainer<T extends TileEntity>
{
	/**
	 * Returns if the NodeNetwork is currently active
	 *
	 * @return If the NodeNetwork is currently active
	 */
	boolean isActive();

	/**
	 * Returns the NodeNetwork
	 *
	 * @return NodeNetwork
	 */
	NodeNetwork getNetwork();

	/**
	 * Sets the NodeNetwork
	 *
	 * @param network NodeNetwork to set
	 */
	void setNetwork(NodeNetwork network);

	/**
	 * Called when should search {@link INetworkElement}
	 */
	void searchNetwork();

	/**
	 * Adds a {@link INetworkElement} to the NodeNetwork
	 *
	 * @param toAdd INetworkElement to add to the NodeNetwork
	 * @return {@link NodeNetwork} with the INetworkElement added to it
	 */
	NodeNetwork addToNetwork(INetworkElement toAdd);

	/**
	 * Removes a {@link INetworkElement} from the NodeNetwork
	 *
	 * @param toRemove INetworkElement to remove from the NodeNetwork
	 * @return {@link NodeNetwork} without the INetworkElement removed from it
	 */
	NodeNetwork removeFromNetwork(INetworkElement toRemove);

	/**
	 * Called when should update the NodeNetwork
	 */
	void updateNetwork();

	/**
	 * Returns the {@link TileEntity} that contains the NodeNetwork
	 *
	 * @return
	 */
	T getController();

	/**
	 * Called when should init the NodeNetwork
	 */
	void initNetwork();

	/**
	 * Called when should draw lines to all the {@link INetworkElement} in the NodeNetwork
	 */
	void drawLines();
}

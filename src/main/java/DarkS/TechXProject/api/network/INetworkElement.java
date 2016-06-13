package DarkS.TechXProject.api.network;

import net.minecraft.tileentity.TileEntity;

public interface INetworkElement<T extends TileEntity>
{
	/**
	 * Returns the {@link ConduitNetwork} that contains the INetworkElement
	 *
	 * @return ConduitNetwork of the INetworkElement
	 */
	ConduitNetwork getNetwork();

	/**
	 * Sets the {@link ConduitNetwork} of the INetworkElement
	 *
	 * @param network ConduitNetwork to set
	 */
	void setNetwork(ConduitNetwork network);

	/**
	 * Adds a INetworkElement to the {@link ConduitNetwork}
	 *
	 * @param toAdd INetworkElement to add to the ConduitNetwork
	 * @return {@link ConduitNetwork} with the INetworkElement added to it
	 */
	ConduitNetwork addToNetwork(INetworkElement toAdd);

	/**
	 * Removes a INetworkElement from the {@link ConduitNetwork}
	 *
	 * @param toRemove INetworkElement to remove form  the ConduitNetwork
	 * @return {@link ConduitNetwork} without the INetworkElement
	 */
	ConduitNetwork removeFromNetwork(INetworkElement toRemove);

	/**
	 * Returns the {@link TileEntity} that contains the INetworkElement
	 *
	 * @return The TileEntity that contains the INetworkElement
	 */
	T getTile();

	/**
	 * Returns the {@link TileEntity} attached on the Side of the INetworkElement
	 *
	 * @return TileEntity attached on the Side of the INetworkElement
	 */
	TileEntity getAttachedTile();

	/**
	 * Returns if the INetworkElement is attached to a {@link TileEntity}
	 *
	 * @return If the INetworkElement is attached to a TileEntity
	 */
	boolean isAttached();

	/**
	 * Returns the distance of the INetworkElement to the given {@link TileEntity}
	 *
	 * @param to TileEntity to get the distance to
	 * @return The Distance of the INetworkElement to the TileEntity
	 */
	int distanceTo(TileEntity to);

	/**
	 * Returns the {@link INetworkContainer} of the attached {@link ConduitNetwork}
	 *
	 * @return The INetworkContainer of the attached ConduitNetwork
	 */
	INetworkContainer getController();

	/**
	 * Returns if the INetworkElement is input
	 *
	 * @return If the INetworkElement is input
	 */
	boolean isInput();

	/**
	 * Returns if the INetworkElement is output
	 *
	 * @return If the INetworkElement is output
	 */
	boolean isOutput();

	/**
	 * Returns if the INetworkElement is active
	 *
	 * @return If the INetworkElement is active
	 */
	boolean isActive();

	/**
	 * Sets the INetworkElement active
	 *
	 * @param act
	 */
	void setActive(boolean act);

	/**
	 * Returns if the INetworkElement is attached to a {@link ConduitNetwork}
	 *
	 * @return If the INetworkElement is attached to a ConduitNetwork
	 */
	boolean hasNetwork();
}

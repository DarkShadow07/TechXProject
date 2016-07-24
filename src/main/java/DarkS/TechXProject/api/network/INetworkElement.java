package DarkS.TechXProject.api.network;

import net.minecraft.tileentity.TileEntity;

public interface INetworkElement<T extends TileEntity>
{
	/**
	 * Returns the {@link NodeNetwork} that contains the INetworkElement
	 *
	 * @return NodeNetwork of the INetworkElement
	 */
	NodeNetwork getNetwork();

	/**
	 * Sets the {@link NodeNetwork} of the INetworkElement
	 *
	 * @param network NodeNetwork to set
	 */
	void setNetwork(NodeNetwork network);

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
	 * Returns the {@link INetworkContainer} of the attached {@link NodeNetwork}
	 *
	 * @return The INetworkContainer of the attached NodeNetwork
	 */
	INetworkContainer getController();

	/**
	 * Returns if the INetworkElement is input
	 *
	 * @return If the INetworkElement is input
	 */
	boolean isInput();

	void setInput(boolean input);

	/**
	 * Returns if the INetworkElement is output
	 *
	 * @return If the INetworkElement is output
	 */
	boolean isOutput();

	void setOutput(boolean output);

	/**
	 * Returns if the INetworkElement is active
	 *
	 * @return If the INetworkElement is active
	 */
	boolean isActive();

	/**
	 * Returns if the INetworkElement is attached to a {@link NodeNetwork}
	 *
	 * @return If the INetworkElement is attached to a NodeNetwork
	 */
	boolean hasNetwork();
}

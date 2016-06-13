package DarkS.TechXProject.api.network;

import java.util.List;

public interface INetworkRelay
{
	/**
	 * Returns the {@link List} of {@link INetworkElement} attached to the INetworkRelay
	 *
	 * @return The List of INetworkElement attached to the INetworkRelay
	 */
	List<INetworkElement> getElements();

	/**
	 * Adds a {@link INetworkElement} to the {@link List} of INetworkElement of the INetworkRelay
	 *
	 * @param element INetworkElement to add to the List of INetworkElement of the INetworkRelay
	 */
	void addElement(INetworkElement element);

	/**
	 * Called when should draw lines to add the {@link INetworkElement} attached to the INetorkRelay
	 */
	void drawLines();
}

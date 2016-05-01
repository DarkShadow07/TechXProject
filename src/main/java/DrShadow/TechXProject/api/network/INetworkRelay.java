package DrShadow.TechXProject.api.network;

import java.util.List;

public interface INetworkRelay
{
	List<INetworkElement> getElements();

	void addElement(INetworkElement element);

	void drawLines();
}

package DrShadow.TechXProject.api.network;

import java.util.List;

public interface INetworkRelay
{
	List<INetworkElement> getElements();

	void drawArea();

	void drawLines();
}

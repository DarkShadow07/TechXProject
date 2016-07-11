package DarkS.TechXProject.api.network;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class NodeNetwork
{
	private INetworkContainer controller = null;
	private List<INetworkElement> networkElements = new ArrayList<>();

	public NodeNetwork(INetworkContainer controller)
	{
		this.controller = controller;
	}

	public List<INetworkElement> getElements()
	{
		return networkElements;
	}

	public void addToNetwork(INetworkElement toAdd)
	{
		networkElements.add(toAdd);

		if (toAdd != null)
			toAdd.setNetwork(this);
	}

	public void removeFromNetwork(INetworkElement toRemove)
	{
		networkElements.remove(toRemove);

		if (toRemove != null)
			toRemove.setNetwork(null);
	}

	public boolean isInNetwork(INetworkElement is)
	{
		return is != null && networkElements.contains(is);

	}

	public void update()
	{

	}

	public void clear()
	{
		networkElements.clear();
	}

	@Nullable
	public List<INetworkElement> getInputContainers()
	{
		List<INetworkElement> input = new ArrayList<>();

		networkElements.forEach(container ->
		{
			if (container.isInput())
			{
				input.add(container);
			}
		});

		return input;
	}

	@Nullable
	public List<INetworkElement> getOutputContainers()
	{
		List<INetworkElement> output = new ArrayList<>();

		networkElements.forEach(container ->
		{
			if (container.isOutput())
			{
				output.add(container);
			}
		});

		return output;
	}

	public INetworkContainer getController()
	{
		return controller;
	}

	@Override
	public String toString()
	{
		return String.format("Conduit Network : %s", networkElements.toString() + " " + getController().getController().getPos());
	}
}

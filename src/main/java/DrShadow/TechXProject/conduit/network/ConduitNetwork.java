package DrShadow.TechXProject.conduit.network;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ConduitNetwork
{
	private INetworkContainer controller = null;
	private List<INetworkElement> networkElements = new ArrayList<INetworkElement>();

	public ConduitNetwork(INetworkContainer controller)
	{
		this.controller = controller;
	}

	@Nullable
	public List<INetworkElement> getElements()
	{
		return networkElements;
	}

	public void addToNetwork(INetworkElement toAdd)
	{
		networkElements.add(toAdd);
	}

	public void removeFromNetwork(INetworkElement toRemove)
	{
		networkElements.remove(toRemove);
	}

	public boolean isInNetwork(INetworkElement is)
	{
		return networkElements.contains(is);
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
		List<INetworkElement> input = new ArrayList<INetworkElement>();

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
		List<INetworkElement> output = new ArrayList<INetworkElement>();

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

package DarkS.TechXProject.configuration.elements;

import DarkS.TechXProject.configuration.box.IntBox;

public class ConfigurationInt extends ConfigurationElement
{
	private IntBox box;

	public ConfigurationInt(String name, String desc, int defaultValue, int minValue, int maxValue)
	{
		super(name, desc);

		box = new IntBox(defaultValue, minValue, maxValue);
	}

	@Override
	public void renderElement(int x, int y)
	{
		super.renderElement(x, y);

		box.render(w - 134, y + 1);
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton)
	{
		super.onMouseClick(mouseX, mouseY, mouseButton);

		box.onMouseClicked(mouseX, mouseY);
	}

	@Override
	public void reset()
	{
		box.currentValue = box.defaultValue;
	}

	@Override
	public Object getValue()
	{
		return box.getCurrentValue();
	}

	@Override
	public void setValue(String value)
	{
		box.setCurrentValue(Integer.valueOf(value));
	}

	@Override
	public Object getDefaultValue()
	{
		return box.defaultValue;
	}
}

package DrShadow.TechXProject.configuration.elements;

import DrShadow.TechXProject.configuration.box.FloatBox;

public class ConfigurationFloat extends ConfigurationElement
{
	private FloatBox box;

	public ConfigurationFloat(String name, String desc, float defaultValue, float minValue, float maxValue)
	{
		super(name, desc);

		box = new FloatBox(defaultValue, minValue, maxValue);
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
		box.setCurrentValue(Float.valueOf(value));
	}

	@Override
	public Object getDefaultValue()
	{
		return box.defaultValue;
	}
}
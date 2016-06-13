package DarkS.TechXProject.configuration.elements;

import DarkS.TechXProject.configuration.box.BooleanBox;

public class ConfigurationBoolean extends ConfigurationElement
{
	private BooleanBox box;

	public ConfigurationBoolean(String name, String desc, boolean defaultValue)
	{
		super(name, desc);

		box = new BooleanBox(defaultValue);
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
	public void update()
	{
		isDefault = box.currentValue == box.defaultValue ? true : false;
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
		box.setCurrentValue(Boolean.valueOf(value));
	}

	@Override
	public Object getDefaultValue()
	{
		return box.defaultValue;
	}
}

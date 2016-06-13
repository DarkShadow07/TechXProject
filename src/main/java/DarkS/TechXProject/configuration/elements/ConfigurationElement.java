package DarkS.TechXProject.configuration.elements;

import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.awt.*;
import java.util.ArrayList;

public class ConfigurationElement
{
	public int x, y, w, h;
	public boolean isDefault;
	protected ScaledResolution res;
	private String name, desc;
	private Object value;

	public ConfigurationElement(String name, String desc)
	{
		this.name = name;
		this.desc = desc;

		res = new ScaledResolution(Minecraft.getMinecraft());
	}

	public void onMouseClick(int mouseX, int mouseY, int mouseButton)
	{

	}

	public void onKeyTyped(char id, int keyCode)
	{

	}

	public void renderElement(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.h = 20;
		this.w = res.getScaledWidth() - 20;

		FontRenderer fontRenderer = Util.minecraft().fontRendererObj;

		if (isDefault)
		{
			fontRenderer.drawString(name, x, y + 5, Color.WHITE.hashCode(), true);
		} else fontRenderer.drawString(ChatFormatting.ITALIC + name, x, y + 5, Color.WHITE.hashCode(), true);

	}

	public void renderWithMousePos(int mouseX, int mouseY)
	{
		FontRenderer fontRenderer = Util.minecraft().fontRendererObj;
		Rectangle rect = new Rectangle(x, y, 250, h);

		ScaledResolution resolution = new ScaledResolution(Util.minecraft());

		if (rect.contains(mouseX, mouseY) && !desc.isEmpty())
		{
			java.util.List<String> info = new ArrayList<>();
			info.add(ChatFormatting.YELLOW + name + ": " + ChatFormatting.GRAY + "[Default Value: " + getDefaultValue() + "]");
			info.add(desc);
			GuiUtils.drawHoveringText(info, mouseX, mouseY, resolution.getScaledWidth(), resolution.getScaledHeight(), 256, fontRenderer);
		}
	}

	public void update()
	{

	}

	public void reset()
	{

	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public Object getDefaultValue()
	{
		return null;
	}

	public String getName()
	{
		return name;
	}
}

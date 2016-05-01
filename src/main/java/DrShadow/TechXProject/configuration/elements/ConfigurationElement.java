package DrShadow.TechXProject.configuration.elements;

import DrShadow.TechXProject.util.GuiUtil;
import DrShadow.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

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
		this.h = 12;
		this.w = res.getScaledWidth() - 20;

		GuiUtil overlayHelper = new GuiUtil();
		FontRenderer fontRenderer = Util.minecraft().fontRendererObj;

		overlayHelper.drawPlaneWithFullBorder(x, y, w, h, new Color(0, 0, 0, 0.3f).hashCode(), new Color(0, 0, 0, 0.7f).hashCode());

		if (isDefault)
		{
			fontRenderer.drawString(name, x, y + 2, Color.WHITE.hashCode(), true);
		} else fontRenderer.drawString(ChatFormatting.ITALIC + name, x, y + 2, Color.WHITE.hashCode(), true);

	}

	public void renderWithMousePos(int mouseX, int mouseY)
	{
		FontRenderer fontRenderer = Util.minecraft().fontRendererObj;
		GuiUtil overlayHelper = new GuiUtil();
		Rectangle rect = new Rectangle(x, y, w, h);

		if (rect.contains(mouseX, mouseY))
		{
			overlayHelper.drawPlaneWithFullBorder(mouseX + 20, mouseY, 256, fontRenderer.splitStringWidth(desc, 256), new Color(0, 0, 0, 0.55f).hashCode(), new Color(0, 0, 0, 0.7f).hashCode());

			fontRenderer.drawSplitString(desc, mouseX + 20, mouseY, 256, Color.WHITE.hashCode());
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

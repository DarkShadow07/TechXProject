package DrShadow.TechXProject.guide;

import DrShadow.TechXProject.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuideElementBase implements IGuideElement
{
	protected GuiGuide gui;

	protected Minecraft mc = Util.minecraft();

	protected FontRenderer font = mc.fontRendererObj;

	protected String name;

	public GuideElementBase(String name, GuiGuide gui)
	{
		this.name = name;
		this.gui = gui;
	}

	@Override
	public void init()
	{

	}

	@Override
	public void onKeyTyped(char typedChar, int keyCode)
	{

	}

	@Override
	public void onMouseClicked(int x, int y, int button)
	{

	}

	@Override
	public void actionPerformed(GuiButton button)
	{

	}

	@Override
	public void render()
	{

	}

	@Override
	public String getName()
	{
		return name;
	}
}

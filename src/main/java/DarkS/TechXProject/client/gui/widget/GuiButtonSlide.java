package DarkS.TechXProject.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonSlide extends GuiButton
{
	private final float minValue;
	private final float maxValue;
	private final String name;
	private float slideValue, realValue;
	private boolean dragging;

	public GuiButtonSlide(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, float minValue, float maxValue)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);

		name = buttonText;

		this.minValue = minValue;
		this.maxValue = maxValue;

		slideValue = 0.1f;
		realValue = 1.0f;

		displayString = name + ": " + realValue;
	}

	public float getValue()
	{
		return realValue;
	}

	@Override
	protected int getHoverState(boolean mouseOver)
	{
		return 0;
	}

	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
	{
		if (visible && dragging)
		{

			slideValue = (float) (mouseX - xPosition) / width;
			slideValue = MathHelper.clamp_float(slideValue, 0.0f, 1.0f);

			realValue = minValue + (maxValue - minValue) * slideValue;

			displayString = name + ": " + realValue;
		}
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if (super.mousePressed(mc, mouseX, mouseY))
		{

			slideValue = (float) (mouseX - xPosition) / width;
			slideValue = MathHelper.clamp_float(slideValue, 0.0f, 1.0f);

			realValue = minValue + (maxValue - minValue) * slideValue;

			displayString = name + ": " + realValue;

			dragging = true;

			return true;
		} else return false;
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY)
	{
		dragging = false;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		super.drawButton(mc, mouseX, mouseY);

		mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.drawTexturedModalRect(xPosition + (int) (slideValue * (width - 8)), yPosition, 0, 66, 4, height);
		this.drawTexturedModalRect(xPosition + (int) (slideValue * (width - 8)) + 4, yPosition, 196, 66, 4, height);
	}
}

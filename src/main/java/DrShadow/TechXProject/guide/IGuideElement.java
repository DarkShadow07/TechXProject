package DrShadow.TechXProject.guide;

import net.minecraft.client.gui.GuiButton;

public interface IGuideElement
{
	void init();

	void onKeyTyped(char typedChar, int keyCode);

	void onMouseClicked(int x, int y, int button);

	void actionPerformed(GuiButton button);

	void render();

	String getName();
}

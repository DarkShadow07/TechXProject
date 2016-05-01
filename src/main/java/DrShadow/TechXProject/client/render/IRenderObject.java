package DrShadow.TechXProject.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRenderObject
{
	void render();

	boolean isDead();

	void setDead();
}

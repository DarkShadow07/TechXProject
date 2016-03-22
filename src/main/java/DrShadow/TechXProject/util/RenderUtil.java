package DrShadow.TechXProject.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderUtil
{
	public static void bindTexture(String texture)
	{
		ResourceLocation res = new ResourceLocation(texture);

		Minecraft.getMinecraft().renderEngine.bindTexture(res);
	}
}

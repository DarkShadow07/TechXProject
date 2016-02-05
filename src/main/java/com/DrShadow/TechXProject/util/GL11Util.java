package com.DrShadow.TechXProject.util;

import static org.lwjgl.opengl.GL11.*;

public class GL11Util
{
	public static void startOpaqueRendering()
	{
		glDepthMask(false);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0);
		glDisable(GL_ALPHA_TEST);
	}

	public static void endOpaqueRendering()
	{
		glDisable(GL_BLEND);
		glEnable(GL_ALPHA_TEST);
		glDepthMask(true);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		glAlphaFunc(GL_GREATER, 0.1F);
	}

}

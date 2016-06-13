package DarkS.TechXProject.client;

import DarkS.TechXProject.client.render.IRenderObject;

import java.util.ArrayList;
import java.util.List;

public class EffectManager
{
	public static final EffectManager instance = new EffectManager();

	private static List<IRenderObject> renderObjects = new ArrayList<>();

	public static void addRenderObject(IRenderObject obj)
	{
		renderObjects.add(obj);
	}

	public static List<IRenderObject> getRenderObjects()
	{
		return renderObjects;
	}
}

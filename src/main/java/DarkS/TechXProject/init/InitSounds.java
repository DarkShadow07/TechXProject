package DarkS.TechXProject.init;

import DarkS.TechXProject.reference.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class InitSounds
{
	public static SoundEvent teleport;

	private static int size = 0;

	public static void init()
	{
		size = SoundEvent.REGISTRY.getKeys().size();

		teleport = register("teleport");
	}

	public static SoundEvent register(String name)
	{
		ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, name);
		SoundEvent e = new SoundEvent(loc);

		SoundEvent.REGISTRY.register(size, loc, e);
		size++;

		return e;
	}
}

package DrShadow.TechXProject.init;

import DrShadow.TechXProject.reference.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class InitSounds
{
	public static SoundEvent teleport;

	private static int size = 0;

	public static void init()
	{
		size = SoundEvent.soundEventRegistry.getKeys().size();

		teleport = register("teleport");
	}

	public static SoundEvent register(String name)
	{
		ResourceLocation loc = new ResourceLocation(Reference.MOD_ID + ":" + name);
		SoundEvent e = new SoundEvent(loc);

		SoundEvent.soundEventRegistry.register(size, loc, e);
		size++;

		return e;
	}
}

package DrShadow.TechXProject.events;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.util.UpdateChecker;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents
{
	@SubscribeEvent
	public void worldSaveEvent(WorldEvent.Save event)
	{
		TechXProject.configurationHandler.writeConfig();
	}

	@SubscribeEvent
	public void worldLoadEvent(WorldEvent.Load event)
	{
		TechXProject.configurationHandler.readConfig();

		if (UpdateChecker.show)
		{
			//Util.sendChatMessage(Reference.MOD_NAME + " is Outdated! " + ChatFormatting.RED + UpdateChecker.currentVersion + ChatFormatting.RESET + " || " + ChatFormatting.AQUA + UpdateChecker.newestVersion);
		}
	}
}

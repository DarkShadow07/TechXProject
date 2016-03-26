package DrShadow.TechXProject.events;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.ChatUtil;
import DrShadow.TechXProject.util.Logger;
import DrShadow.TechXProject.util.PartialTicksUtil;
import DrShadow.TechXProject.util.UpdateChecker;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
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
			ChatUtil.sendNoSpamClient(Reference.MOD_NAME + " is Outdated! " + ChatFormatting.RED + UpdateChecker.currentVersion + ChatFormatting.RESET + " || " + ChatFormatting.AQUA + UpdateChecker.newestVersion);
		}
	}

	@SubscribeEvent
	public void worldLastRender(RenderWorldLastEvent event)
	{
		PartialTicksUtil.partialTicks = event.partialTicks;
	}
}

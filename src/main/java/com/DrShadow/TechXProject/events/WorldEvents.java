package com.DrShadow.TechXProject.events;

import com.DrShadow.TechXProject.reference.Reference;
import com.DrShadow.TechXProject.util.Helper;
import com.DrShadow.TechXProject.util.LogHelper;
import com.DrShadow.TechXProject.util.UpdateChecker;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents
{
	@SubscribeEvent
	public void worldSaveEvent(WorldEvent.Save event)
	{
		LogHelper.info(Reference.MOD_NAME + " data is now Saved!");
	}

	@SubscribeEvent
	public void worldLoadEvent(WorldEvent.Load event)
	{
		if (UpdateChecker.show)
		{
			Helper.sendChatMessage(Reference.MOD_NAME + " is Outdated! " + ChatFormatting.RED + UpdateChecker.currentVersion + ChatFormatting.RESET +  " || " + ChatFormatting.AQUA + UpdateChecker.newestVersion);
		}
	}
}

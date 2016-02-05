package com.DrShadow.TechXProject.events;

import com.DrShadow.TechXProject.util.Helper;
import com.DrShadow.TechXProject.util.LogHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class UpdateEvents
{
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		handleChat();
	}

	private void handleChat()
	{
		if (ChatMessageEvent.isMsgNow)
		{
			if (ChatMessageEvent.ticksExisted < ChatMessageEvent.maxTicksExisted)
			{
				ChatMessageEvent.ticksExisted += 1;
			}

			if (ChatMessageEvent.ticksExisted >= ChatMessageEvent.maxTicksExisted && ChatMessageEvent.alpha > 0)
			{
				ChatMessageEvent.alpha -= 0.05F;
			}else if (ChatMessageEvent.alpha <= 0)
			{
				ChatMessageEvent.removeMessage();

				ChatMessageEvent.alpha = 1;
			}
		}

		LogHelper.info(ChatMessageEvent.ticksExisted + " " + ChatMessageEvent.maxTicksExisted + " " + ChatMessageEvent.alpha);
	}
}

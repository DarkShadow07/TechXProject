package DarkS.TechXProject.events;

import DarkS.TechXProject.util.PartialTicksUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickEvents
{
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		PartialTicksUtil.partialTicks = event.renderTickTime;
	}
}

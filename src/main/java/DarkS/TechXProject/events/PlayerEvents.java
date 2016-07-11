package DarkS.TechXProject.events;

import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
import DarkS.TechXProject.items.ItemPaintBrush;
import DarkS.TechXProject.util.PartialTicksUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerEvents
{
	@SubscribeEvent
	public void onBlockRightClickEvent(PlayerInteractEvent.RightClickBlock event)
	{
		World world = event.getWorld();
		BlockPos pos = event.getPos();

		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof IHighlightProvider)
		{
			IHighlightProvider provider = (IHighlightProvider) world.getTileEntity(pos);

			double blockReachDistance = event.getEntityPlayer() instanceof EntityPlayerMP ? ((EntityPlayerMP) event.getEntityPlayer()).interactionManager.getBlockReachDistance() : 4.5d;

			Vec3d start = event.getEntityPlayer().getPositionEyes(PartialTicksUtil.partialTicks);
			Vec3d look = event.getEntityPlayer().getLook(PartialTicksUtil.partialTicks);
			Vec3d end = start.addVector(look.xCoord * blockReachDistance, look.yCoord * blockReachDistance, look.zCoord * blockReachDistance);

			for (SelectionBox box : provider.getSelectedBoxes(pos, start, end))
				if (box != null)
					if (provider.onBoxClicked(box, event.getEntityPlayer()))
						event.setCanceled(true);
		}
	}
}

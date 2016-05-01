package DrShadow.TechXProject.events;

import DrShadow.TechXProject.client.keyBindings.KeyBindings;
import DrShadow.TechXProject.packets.PacketHandler;
import DrShadow.TechXProject.packets.PacketOpenGui;
import DrShadow.TechXProject.reference.Guis;
import DrShadow.TechXProject.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputEvent
{
	@SubscribeEvent
	public void handleKeyInput(InputEvent.KeyInputEvent event)
	{
		EntityPlayer player = Util.player();
		BlockPos pos = player.getPosition();

		if (KeyBindings.configuration.isPressed())
		{
			PacketHandler.sendToServer(new PacketOpenGui(Guis.CONFIGURATION));
		}
	}
}

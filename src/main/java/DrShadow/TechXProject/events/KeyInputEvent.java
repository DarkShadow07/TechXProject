package DrShadow.TechXProject.events;

import DrShadow.TechXProject.keyBindings.KeyBindings;
import DrShadow.TechXProject.lib.Guis;
import DrShadow.TechXProject.packets.PacketHandler;
import DrShadow.TechXProject.packets.PacketOpenGui;
import DrShadow.TechXProject.util.Helper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputEvent
{
	@SubscribeEvent
	public void handleKeyInput(InputEvent.KeyInputEvent event)
	{
		EntityPlayer player = Helper.player();
		BlockPos pos = player.getPosition();

		if (KeyBindings.configuration.isPressed())
		{
			PacketHandler.sendToServer(new PacketOpenGui(Guis.CONFIGURATION));
		}
	}
}

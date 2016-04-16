package DrShadow.TechXProject.packets;

import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.ChatUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Reference.CHANNEL_NAME);

	public static void init()
	{
		INSTANCE.registerMessage(ChatUtil.PacketNoSpamChat.Handler.class, ChatUtil.PacketNoSpamChat.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(PacketOpenGui.Handler.class, PacketOpenGui.class, 1, Side.SERVER);
		INSTANCE.registerMessage(PacketUpdateCondition.Handler.class, PacketUpdateCondition.class, 2, Side.CLIENT);
		INSTANCE.registerMessage(PacketUpdateEnergy.Handler.class, PacketUpdateEnergy.class, 3, Side.CLIENT);
		INSTANCE.registerMessage(PacketTeleportEntity.Handler.class, PacketTeleportEntity.class, 4, Side.SERVER);
		INSTANCE.registerMessage(PacketUpdateRecipe.Handler.class, PacketUpdateRecipe.class, 5, Side.SERVER);
	}

	public static void sendToAllAround(IMessage message, TileEntity te, int range)
	{
		INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(te.getWorld().provider.getDimension(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), range));
	}

	public static void sendToAllAround(IMessage message, TileEntity te)
	{
		sendToAllAround(message, te, 64);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player)
	{
		INSTANCE.sendTo(message, player);
	}

	public static void sendToServer(IMessage message)
	{
		INSTANCE.sendToServer(message);
	}
}

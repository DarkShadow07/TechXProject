package DarkS.TechXProject.packets;

import DarkS.TechXProject.capability.PacketEnergyCapability;
import DarkS.TechXProject.machines.energyMonitor.PacketUpdateMonitor;
import DarkS.TechXProject.machines.itemInterface.PacketExtractFromNetwork;
import DarkS.TechXProject.machines.itemInterface.PacketSendInventory;
import DarkS.TechXProject.machines.node.PacketUpdateIO;
import DarkS.TechXProject.machines.node.transport.PacketMove;
import DarkS.TechXProject.machines.node.transport.PacketSetName;
import DarkS.TechXProject.machines.recipeStamper.PacketUpdateRecipe;
import DarkS.TechXProject.machines.teleporter.PacketTeleportEntity;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.ChatUtil;
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
		INSTANCE.registerMessage(PacketUpdateEnergy.Handler.class, PacketUpdateEnergy.class, 2, Side.CLIENT);
		INSTANCE.registerMessage(PacketTeleportEntity.Handler.class, PacketTeleportEntity.class, 3, Side.SERVER);
		INSTANCE.registerMessage(PacketUpdateRecipe.Handler.class, PacketUpdateRecipe.class, 4, Side.SERVER);
		INSTANCE.registerMessage(PacketUpdateMonitor.Handler.class, PacketUpdateMonitor.class, 5, Side.SERVER);
		INSTANCE.registerMessage(PacketMove.Handler.class, PacketMove.class, 6, Side.SERVER);
		INSTANCE.registerMessage(PacketSetName.Handler.class, PacketSetName.class, 7, Side.SERVER);
		INSTANCE.registerMessage(PacketSendNetwork.Handler.class, PacketSendNetwork.class, 8, Side.CLIENT);
		INSTANCE.registerMessage(PacketEnergyCapability.Handler.class, PacketEnergyCapability.class, 9, Side.CLIENT);
		INSTANCE.registerMessage(PacketUpdateIO.Handler.class, PacketUpdateIO.class, 10, Side.SERVER);
		INSTANCE.registerMessage(PacketSendInventory.Handler.class, PacketSendInventory.class, 11, Side.CLIENT);
		INSTANCE.registerMessage(PacketExtractFromNetwork.Handler.class, PacketExtractFromNetwork.class, 12, Side.SERVER);
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

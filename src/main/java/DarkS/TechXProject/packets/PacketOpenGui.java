package DarkS.TechXProject.packets;

import DarkS.TechXProject.TechXProject;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenGui implements IMessage
{
	protected int id;

	public PacketOpenGui() {}

	public PacketOpenGui(int id)
	{
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<PacketOpenGui, IMessage>
	{
		@Override
		public IMessage onMessage(PacketOpenGui message, MessageContext ctx)
		{
			EntityPlayer player = ctx.getServerHandler().playerEntity;

			FMLNetworkHandler.openGui(player, TechXProject.instance, message.id, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
			return null;
		}
	}
}

package DarkS.TechXProject.machines.node.transport;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.packets.MessageTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSetName extends MessageTileEntity<TileTransportNode>
{
	protected String name;

	public PacketSetName()
	{

	}

	public PacketSetName(TileTransportNode node)
	{
		super(node);

		this.name = node.getName();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		name = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		ByteBufUtils.writeUTF8String(buf, name);
	}

	public static class Handler implements IMessageHandler<PacketSetName, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSetName message, MessageContext ctx)
		{
			TileTransportNode node = message.getTileEntity(TechXProject.proxy.getMinecraftServer().getEntityWorld());

			node.setName(message.name);

			return null;
		}
	}
}

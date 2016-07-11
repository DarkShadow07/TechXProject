package DarkS.TechXProject.packets;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.node.transport.TileTransportNode;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMove extends MessageTileEntity<TileTransportNode>
{
	protected int entity;
	protected BlockPos pos;

	public PacketMove()
	{

	}

	public PacketMove(TileTransportNode node, Entity entity, BlockPos pos)
	{
		super(node);

		this.entity = entity.getEntityId();
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		entity = buf.readInt();

		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		buf.writeInt(entity);

		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	public static class Handler implements IMessageHandler<PacketMove, IMessage>
	{
		@Override
		public IMessage onMessage(PacketMove message, MessageContext ctx)
		{
			TileTransportNode node = message.getTileEntity(TechXProject.proxy.getMinecraftServer().getEntityWorld());

			node.setMovePos(message.pos);
			node.moveEntity(TechXProject.proxy.getMinecraftServer().getEntityWorld().getEntityByID(message.entity));

			return null;
		}
	}
}

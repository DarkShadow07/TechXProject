package DrShadow.TechXProject.packets;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.util.Teleporter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketTeleportEntity implements IMessage
{
	protected UUID uuid;
	protected BlockPos pos;
	protected int dim;

	public PacketTeleportEntity()
	{

	}

	public PacketTeleportEntity(EntityLivingBase entity, BlockPos pos, int dim)
	{
		uuid = entity.getUniqueID();
		this.pos = pos;
		this.dim = dim;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		dim = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());

		ByteBufUtils.writeUTF8String(buf, uuid.toString());

		buf.writeInt(dim);
	}

	public static class Handler implements IMessageHandler<PacketTeleportEntity, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTeleportEntity message, MessageContext ctx)
		{
			Teleporter.TeleportLocation location = new Teleporter.TeleportLocation(message.pos.getX(), message.pos.getY(), message.pos.getZ(), message.dim);

			EntityLivingBase entity = (EntityLivingBase) TechXProject.proxy.getMinecraftServer().getEntityFromUuid(message.uuid);

			location.sendEntityToCoords(entity);

			return null;
		}
	}
}

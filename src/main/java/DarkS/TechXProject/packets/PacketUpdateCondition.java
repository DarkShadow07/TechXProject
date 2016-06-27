package DarkS.TechXProject.packets;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.node.logic.TileLogicConduit;
import DarkS.TechXProject.node.logic.condition.EnumConditionType;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateCondition extends MessageTileEntity<TileLogicConduit>
{
	protected int id;
	protected TileLogicConduit tile;

	public PacketUpdateCondition() {}

	public PacketUpdateCondition(int id, TileLogicConduit tile)
	{
		super(tile);

		this.id = id;
		this.tile = tile;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<PacketUpdateCondition, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdateCondition message, MessageContext ctx)
		{
			TileEntity tile = message.getTileEntity(TechXProject.proxy.getClientWorld());

			EnumConditionType[] values = EnumConditionType.values();

			((TileLogicConduit) tile).setCondition(values[message.id]);

			return null;
		}
	}
}

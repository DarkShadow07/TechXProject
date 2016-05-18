package DrShadow.TechXProject.packets;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.machines.energyMonitor.TileEnergyMonitor;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateMonitor extends MessageTileEntity<TileEnergyMonitor>
{
	protected int min, max;

	public PacketUpdateMonitor()
	{

	}

	public PacketUpdateMonitor(TileEnergyMonitor tile, int min, int max)
	{
		super(tile);

		this.min = min;
		this.max = max;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		min = buf.readInt();
		max = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		buf.writeInt(min);
		buf.writeInt(max);
	}

	public static class Handler implements IMessageHandler<PacketUpdateMonitor, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdateMonitor message, MessageContext ctx)
		{
			TileEntity tile = message.getTileEntity(TechXProject.proxy.getMinecraftServer().getEntityWorld());

			if (tile != null)
			{
				((TileEnergyMonitor) tile).minE = message.min;
				((TileEnergyMonitor) tile).maxE = message.max;
			}

			return null;
		}
	}
}

package DarkS.TechXProject.machines.node;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.packets.MessageTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateIO extends MessageTileEntity<TileBase>
{
	private boolean input, output;

	public PacketUpdateIO()
	{

	}

	public PacketUpdateIO(INetworkElement element)
	{
		input = element.isInput();
		output = element.isOutput();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		buf.writeBoolean(input);
		buf.writeBoolean(output);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		input = buf.readBoolean();
		output = buf.readBoolean();
	}

	public static class Handler implements IMessageHandler<PacketUpdateIO, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdateIO message, MessageContext ctx)
		{
			TileBase tile = message.getTileEntity(TechXProject.proxy.getMinecraftServer().getEntityWorld());

			if (tile != null && tile instanceof INetworkElement)
			{
				INetworkElement element = (INetworkElement) tile;

				element.setInput(message.input);
				element.setOutput(message.output);
			}

			return null;
		}
	}
}

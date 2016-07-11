package DarkS.TechXProject.packets;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.node.network.NetworkUtil;
import DarkS.TechXProject.node.transport.TileTransportNode;
import DarkS.TechXProject.util.Util;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendNetwork extends MessageTileEntity<TileBase>
{
	protected NodeNetwork network;

	public PacketSendNetwork()
	{

	}

	public PacketSendNetwork(INetworkElement node)
	{
		super((TileBase) node.getTile());

		this.network = node.getNetwork();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		network = NetworkUtil.readNetwork(Util.world(), NetworkUtil.readNetworkNBT(ByteBufUtils.readTag(buf)), null);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		NBTTagCompound tag = new NBTTagCompound();
		NetworkUtil.writeNetworkNBT(tag, network);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler implements IMessageHandler<PacketSendNetwork, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSendNetwork message, MessageContext ctx)
		{
			INetworkElement element = (INetworkElement) message.getTileEntity(TechXProject.proxy.getClientWorld());

			if (element != null)
				element.setNetwork(message.network);

			return null;
		}
	}
}
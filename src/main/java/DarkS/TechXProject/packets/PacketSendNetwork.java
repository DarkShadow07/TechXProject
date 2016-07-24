package DarkS.TechXProject.packets;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.machines.node.network.NetworkUtil;
import DarkS.TechXProject.util.Util;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendNetwork extends MessageTileEntity<TileBase>
{
	protected NBTTagCompound network;

	public PacketSendNetwork()
	{

	}

	public PacketSendNetwork(TileBase node)
	{
		super(node);

		network = new NBTTagCompound();

		if (node instanceof INetworkElement)
			NetworkUtil.writeNetworkNBT(network, ((INetworkElement) node).getNetwork());
		else if (node instanceof INetworkContainer)
			NetworkUtil.writeNetworkNBT(network, ((INetworkContainer) node).getNetwork());
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		network = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		ByteBufUtils.writeTag(buf, network);
	}

	public static class Handler implements IMessageHandler<PacketSendNetwork, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSendNetwork message, MessageContext ctx)
		{
			TileBase element = message.getTileEntity(TechXProject.proxy.getClientWorld());

			if (element instanceof INetworkElement)
				((INetworkElement) element).setNetwork(NetworkUtil.readNetwork(TechXProject.proxy.getClientWorld(), NetworkUtil.readNetworkNBT(message.network), null));
			else if (element instanceof INetworkContainer)
				((INetworkContainer) element).setNetwork(NetworkUtil.readNetwork(TechXProject.proxy.getClientWorld(), NetworkUtil.readNetworkNBT(message.network), null));

			return null;
		}
	}
}
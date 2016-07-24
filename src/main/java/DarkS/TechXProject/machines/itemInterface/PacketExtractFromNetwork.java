package DarkS.TechXProject.machines.itemInterface;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.packets.MessageTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketExtractFromNetwork extends MessageTileEntity<TileItemInterface>
{
	private ItemStack stack;

	public PacketExtractFromNetwork()
	{

	}

	public PacketExtractFromNetwork(TileItemInterface tile, ItemStack stack)
	{
		super(tile);

		this.stack = stack;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		ByteBufUtils.writeItemStack(buf, stack);
	}

	public static class Handler implements IMessageHandler<PacketExtractFromNetwork, IMessage>
	{
		@Override
		public IMessage onMessage(PacketExtractFromNetwork message, MessageContext ctx)
		{
			message.getTileEntity(TechXProject.proxy.getMinecraftServer().getEntityWorld()).extractItemFronNetwork(message.stack);

			return null;
		}
	}
}

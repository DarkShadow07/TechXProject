package DarkS.TechXProject.machines.itemInterface;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.packets.MessageTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

public class PacketSendInventory extends MessageTileEntity<TileItemInterface>
{
	private List<ItemStack> stacks;

	public PacketSendInventory()
	{

	}

	public PacketSendInventory(TileItemInterface tile)
	{
		super(tile);

		this.stacks = tile.stacks;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		stacks = new ArrayList<>();

		int size = buf.readInt();

		for (int i = 0; i < size; i++)
			stacks.add(ByteBufUtils.readItemStack(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		buf.writeInt(stacks.size());

		for (ItemStack stack : stacks)
			ByteBufUtils.writeItemStack(buf, stack);
	}

	public static class Handler implements IMessageHandler<PacketSendInventory, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSendInventory message, MessageContext ctx)
		{
			TileItemInterface tile = message.getTileEntity(TechXProject.proxy.getClientWorld());

			if (tile != null)
			{
				tile.stacks = message.stacks;
			}

			return null;
		}
	}
}

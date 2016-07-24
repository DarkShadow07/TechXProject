package DarkS.TechXProject.machines.recipeStamper;

import DarkS.TechXProject.items.ItemMachineRecipe;
import DarkS.TechXProject.packets.MessageTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateRecipe extends MessageTileEntity<TileRecipeStamper>
{
	protected int type;
	protected TileRecipeStamper tile;

	public PacketUpdateRecipe() {}

	public PacketUpdateRecipe(int type, TileRecipeStamper tile)
	{
		super(tile);

		this.type = type;
		this.tile = tile;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<PacketUpdateRecipe, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdateRecipe message, MessageContext ctx)
		{
			TileRecipeStamper tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);

			if (tile.getStackInSlot(0) != null)
			{
				ItemMachineRecipe item = (ItemMachineRecipe) tile.getStackInSlot(0).getItem();
				item.setType(tile.getStackInSlot(0), message.type);
			}

			return null;
		}
	}
}

package DrShadow.TechXProject.packets;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.tileEntities.TileEnergyContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateEnergy extends MessageTileEntity<TileEnergyContainer>
{
	protected int energy;

	public PacketUpdateEnergy() {}

	public PacketUpdateEnergy(int energy, TileEnergyContainer tile)
	{
		super(tile);

		this.energy = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		this.energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		buf.writeInt(energy);
	}

	public static class Handler implements IMessageHandler<PacketUpdateEnergy, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdateEnergy message, MessageContext ctx)
		{
			TileEntity tile = message.getTileEntity(TechXProject.proxy.getClientWorld());

			if (tile != null)
			{
				((TileEnergyContainer) tile).setEnergy(message.energy);
			}

			return null;
		}
	}
}

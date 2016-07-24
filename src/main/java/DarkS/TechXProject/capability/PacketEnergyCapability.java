package DarkS.TechXProject.capability;

import DarkS.TechXProject.util.Util;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergyCapability implements IMessage
{
	private NBTTagCompound tag;

	public PacketEnergyCapability()
	{

	}

	public PacketEnergyCapability(NBTTagCompound tag)
	{
		this.tag = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler implements IMessageHandler<PacketEnergyCapability, IMessage>
	{
		@Override
		public IMessage onMessage(PacketEnergyCapability message, MessageContext ctx)
		{
			if (Util.player() != null && Util.player().hasCapability(CustomCapabilities.ENERGY_CAPABILITY, null))
			{
				((CustomCapabilities.EnergyData) Util.player().getCapability(CustomCapabilities.ENERGY_CAPABILITY, null)).energy = message.tag.getInteger("energy");
				((CustomCapabilities.EnergyData) Util.player().getCapability(CustomCapabilities.ENERGY_CAPABILITY, null)).maxEnergy = message.tag.getInteger("maxEnergy");
			}

			return null;
		}
	}
}

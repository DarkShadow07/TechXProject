package DrShadow.TechXProject.util;

import DrShadow.TechXProject.packets.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ChatUtil
{
	private static final int DELETION_ID = 8675309;
	private static int lastAdded;

	private static void sendNoSpamMessages(IChatComponent[] messages)
	{
		GuiNewChat chat = Helper.minecraft().ingameGUI.getChatGUI();
		for (int i = DELETION_ID + messages.length - 1; i <= lastAdded; i++)
		{
			chat.deleteChatLine(i);
		}
		for (int i = 0; i < messages.length; i++)
		{
			chat.printChatMessageWithOptionalDeletion(messages[i], DELETION_ID + i);
		}
		lastAdded = DELETION_ID + messages.length - 1;
	}

	public static IChatComponent wrap(String s)
	{
		return new ChatComponentText(s);
	}

	public static IChatComponent[] wrap(String... s)
	{
		IChatComponent[] ret = new IChatComponent[s.length];
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = wrap(s[i]);
		}
		return ret;
	}

	public static IChatComponent wrapFormatted(String s, Object... args)
	{
		return new ChatComponentTranslation(s, args);
	}

	public static void sendChat(EntityPlayer player, String... lines)
	{
		sendChat(player, wrap(lines));
	}

	public static void sendChat(EntityPlayer player, IChatComponent... lines)
	{
		for (IChatComponent c : lines)
		{
			player.addChatComponentMessage(c);
		}
	}

	public static void sendNoSpamClient(String... lines)
	{
		sendNoSpamClient(wrap(lines));
	}

	public static void sendNoSpamClient(IChatComponent... lines)
	{
		sendNoSpamMessages(lines);
	}

	public static void sendNoSpam(EntityPlayer player, String... lines)
	{
		sendNoSpam(player, wrap(lines));
	}

	public static void sendNoSpam(EntityPlayer player, IChatComponent... lines)
	{
		if (player instanceof EntityPlayerMP)
		{
			sendNoSpam((EntityPlayerMP) player, lines);
		}
	}

	public static void sendNoSpam(EntityPlayerMP player, String... lines)
	{
		sendNoSpam(player, wrap(lines));
	}

	public static void sendNoSpam(EntityPlayerMP player, IChatComponent... lines)
	{
		if (lines.length > 0)
		{
			PacketHandler.sendTo(new PacketNoSpamChat(lines), player);
		}
	}

	public static class PacketNoSpamChat implements IMessage
	{
		private IChatComponent[] chatLines;

		public PacketNoSpamChat()
		{
			chatLines = new IChatComponent[0];
		}

		private PacketNoSpamChat(IChatComponent... lines)
		{
			this.chatLines = lines;
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(chatLines.length);
			for (IChatComponent c : chatLines)
			{
				ByteBufUtils.writeUTF8String(buf, IChatComponent.Serializer.componentToJson(c));
			}
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			chatLines = new IChatComponent[buf.readInt()];
			for (int i = 0; i < chatLines.length; i++)
			{
				chatLines[i] = IChatComponent.Serializer.jsonToComponent(ByteBufUtils.readUTF8String(buf));
			}
		}

		public static class Handler implements IMessageHandler<PacketNoSpamChat, IMessage>
		{

			@Override
			public IMessage onMessage(PacketNoSpamChat message, MessageContext ctx)
			{
				sendNoSpamMessages(message.chatLines);
				return null;
			}
		}
	}
}

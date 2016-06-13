package DarkS.TechXProject.util;

import DarkS.TechXProject.packets.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ChatUtil
{
	private static final int DELETION_ID = 8675310;
	private static int lastAdded;

	private static void sendNoSpamMessages(ITextComponent[] messages)
	{
		GuiNewChat chat = Util.minecraft().ingameGUI.getChatGUI();
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

	public static ITextComponent wrap(String s)
	{
		return new TextComponentString(s);
	}

	public static ITextComponent[] wrap(String... s)
	{
		ITextComponent[] ret = new ITextComponent[s.length];
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = wrap(s[i]);
		}
		return ret;
	}

	public static ITextComponent wrapFormatted(String s, Object... args)
	{
		return new TextComponentTranslation(s, args);
	}

	public static void sendChat(EntityPlayer player, String... lines)
	{
		sendChat(player, wrap(lines));
	}

	public static void sendChat(EntityPlayer player, ITextComponent... lines)
	{
		for (ITextComponent c : lines)
		{
			player.addChatComponentMessage(c);
		}
	}

	public static void sendNoSpamClient(String... lines)
	{
		sendNoSpamClient(wrap(lines));
	}

	public static void sendNoSpamClient(ITextComponent... lines)
	{
		sendNoSpamMessages(lines);
	}

	public static void sendNoSpam(EntityPlayer player, String... lines)
	{
		sendNoSpam(player, wrap(lines));
	}

	public static void sendNoSpam(EntityPlayer player, ITextComponent... lines)
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

	public static void sendNoSpam(EntityPlayerMP player, ITextComponent... lines)
	{
		if (lines.length > 0)
		{
			PacketHandler.sendTo(new PacketNoSpamChat(lines), player);
		}
	}

	public static class PacketNoSpamChat implements IMessage
	{
		private ITextComponent[] chatLines;

		public PacketNoSpamChat()
		{
			chatLines = new ITextComponent[0];
		}

		private PacketNoSpamChat(ITextComponent... lines)
		{
			this.chatLines = lines;
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(chatLines.length);
			for (ITextComponent c : chatLines)
			{
				ByteBufUtils.writeUTF8String(buf, ITextComponent.Serializer.componentToJson(c));
			}
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			chatLines = new ITextComponent[buf.readInt()];
			for (int i = 0; i < chatLines.length; i++)
			{
				chatLines[i] = ITextComponent.Serializer.jsonToComponent(ByteBufUtils.readUTF8String(buf));
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

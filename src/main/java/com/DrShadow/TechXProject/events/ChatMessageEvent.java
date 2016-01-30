package com.DrShadow.TechXProject.events;

public class ChatMessageEvent
{
	public static String message = "";

	public static int ticksExisted = 128;

	static boolean isMsgNow;

	public static void sendMessage(String msg)
	{
		removeMessage();
		message = msg;
		isMsgNow = true;
		ticksExisted = 128;
	}

	public static void removeMessage()
	{
		isMsgNow = false;
		message = "";
	}

	public static String getMessage()
	{
		return message;
	}
}

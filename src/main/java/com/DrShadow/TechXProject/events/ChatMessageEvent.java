package com.DrShadow.TechXProject.events;

import com.DrShadow.TechXProject.reference.Reference;
import com.DrShadow.TechXProject.util.LogHelper;

public class ChatMessageEvent
{
	public static String message = "";

	public static int ticksExisted = 0;
	public static int maxTicksExisted = 256;

	static boolean isMsgNow;

	public static float alpha = 1;

	public static void sendMessage(String msg)
	{
		removeMessage();

		LogHelper.info("[" + Reference.MOD_NAME + "] Chat Message: " + msg);

		message = msg;
		isMsgNow = true;
		ticksExisted = 0;
	}

	public static void removeMessage()
	{
		ticksExisted = 0;
		isMsgNow = false;
		message = "";
	}

	public static String getMessage()
	{
		return message;
	}
}

package com.DrShadow.TechXProject.util;

import com.DrShadow.TechXProject.events.ChatMessageEvent;
import com.DrShadow.TechXProject.events.RenderEvents;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;
import java.util.Random;

public class Helper
{
	static Random rand = new Random();

	public static final PropertyInteger meta = PropertyInteger.create("meta", 0, 15);


	public static void sendChatMessage(String message)
	{
		ChatMessageEvent.sendMessage(message);
	}

	public static AxisAlignedBB fromBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		return AxisAlignedBB.fromBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public static Entity spawnEntity(Entity entity)
	{
		if (isRemote(entity)) return null;
		entity.worldObj.spawnEntityInWorld(entity);
		entity.forceSpawn = true;
		return entity;
	}

	public static void equalize(float start, float max, float speed)
	{
		while ((start + speed) <= max)
		{
			start += speed;
		}
	}

	public static boolean contains(List list, String string)
	{
		return list.contains(string);
	}

	public static double slowlyEqualize(double variable, double goal, double speed)
	{
		return slowlyEqualize((float) variable, (float) goal, (float) speed);
	}

	public static float slowlyEqualize(float variable, float goal, float speed)
	{
		if (speed == 0) return variable;
		speed = Math.abs(speed);
		if (variable + speed > goal && (Math.abs((variable + speed) - goal) < speed * 1.001)) return goal;
		if (variable - speed < goal && (Math.abs((variable - speed) - goal) < speed * 1.001)) return goal;

		if (variable < goal) variable += speed;
		else if (variable > goal) variable -= speed;
		return variable;
	}

	public static void setBlock(World world, BlockPos pos, Block block)
	{
		Block prevBlock = world.getBlockState(pos).getBlock();

		world.playAuxSFX(2001, pos, Block.getIdFromBlock(prevBlock) + (prevBlock.getMetaFromState(prevBlock.getBlockState().getBaseState()) << 12));
		world.playAuxSFX(2001, pos, prevBlock.getMetaFromState(prevBlock.getDefaultState()));
		world.setBlockState(pos, block.getDefaultState(), 1 | 2);
	}

	public static boolean isInteger(String str)
	{
		if (str == null) return false;
		int length = str.length();
		if (length == 0) return false;
		int i = 0;
		if (str.charAt(0) == '-')
		{
			if (length == 1) return false;
			i = 1;
		}
		for (; i < length; i++)
		{
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') return false;
		}
		return true;
	}

	public static boolean isBoolean(String str)
	{
		if (str == null) return false;
		return str.equals("true") || str.equals("false");
	}

	public static boolean isNull(Object... objects)
	{
		boolean result = false;
		for (int a = 0; a < objects.length; a++)
		{
			if (objects[a] == null)
			{
				result = true;
				continue;
			}
		}
		return result;
	}


	public static void spawnEntityFX(EntityFX particleFX)
	{
		if (particleFX.worldObj.isRemote)
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null)
			{
				int i = mc.gameSettings.particleSetting;
				double d6 = mc.getRenderViewEntity().posX - particleFX.posX, d7 = mc.getRenderViewEntity().posY - particleFX.posY, d8 = mc.getRenderViewEntity().posZ - particleFX.posZ, d9 = Math.sqrt(mc.gameSettings.renderDistanceChunks) * 45;
				if (i > 1) ;
				else
				{
					if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9) ;
					else Minecraft.getMinecraft().effectRenderer.addEffect(particleFX);
				}
			}
		}
	}

	public static Vector3f generateVectorWall(int minX, int minZ, int maxX, int maxZ, int y, int hight)
	{
		Vector3f result = new Vector3f();
		result.y = Helper.evenRandomFloat(hight) + y + 0.5F;
		switch (Helper.randomInt(4))
		{
			case 0:
			{
				result.x = minX;
				result.z = minZ - Helper.randomFloat(minZ - maxZ);
			}
			break;
			case 1:
			{
				result.x = maxX;
				result.z = maxZ + Helper.randomFloat(minZ - maxZ);
			}
			break;
			case 2:
			{
				result.x = minX - Helper.randomFloat(minX - maxX);
				result.z = minZ;
			}
			break;
			case 3:
			{
				result.x = maxX + Helper.randomFloat(minX - maxX);
				result.z = maxZ;
			}
			break;
		}
		return result;
	}

	public static boolean AxisAlignedBBEqual(AxisAlignedBB box1, AxisAlignedBB box2)
	{
		if (box1 == box2) return true;
		if (isNull(box1, box2)) return false;
		return box1.minX == box2.minX && box1.minY == box2.minY && box1.minZ == box2.minZ && box1.maxX == box2.maxX && box1.maxY == box2.maxY && box1.maxZ == box2.maxZ;
	}

	public static double keepValueInBounds(double value, double min, double max)
	{
		if (min >= max) return value;
		if (value < min) value = min;
		if (value > max) value = max;
		return value;
	}

	public static float keepValueInBounds(float value, float min, float max)
	{
		return (float) keepAValueInBounds((double) value, (double) min, (double) max);
	}

	public static int keepValueInBounds(int value, int min, int max)
	{
		return (int) keepValueInBounds((double) value, (double) min, (double) max);
	}

	public static void printStackTrace()
	{
		println(getStackTrace());
	}

	public static String getStackTrace()
	{
		String Return = "";

		StackTraceElement[] a1 = Thread.currentThread().getStackTrace();
		int lenght = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Return += "Invoke time: " + dateFormat.format(cal.getTime()) + "\n";
		for (int i = 3; i < a1.length; i++)
		{
			StackTraceElement a = a1[i];
			String s = a.toString();
			Return += s + "\n";
			lenght = Math.max(s.length(), lenght);
		}
		for (int b = 0; b < lenght; b++) Return += "-";
		Return += "\n";

		return Return;
	}

	private static void println(Object obj)
	{
		System.out.print(obj + "\n");
	}

	public static void println(Object... objs)
	{
		for (Object a : objs) println(a + " ");
	}

	public static void printInln(Object... objs)
	{
		for (Object a : objs) System.out.print(a + " ");
		System.out.print("\n");
	}

	public static double keepAValueInBounds(double value, double min, double max)
	{
		if (min >= max) return value;
		if (value < min) value = min;
		if (value > max) value = max;
		return value;
	}

	public static float keepAValueInBounds(float value, float min, float max)
	{
		return (float) keepAValueInBounds((double) value, (double) min, (double) max);
	}

	public static boolean isRemote(Object object)
	{
		if (object instanceof Entity) return ((Entity) object).worldObj.isRemote;
		if (object instanceof World) return ((World) object).isRemote;
		if (object instanceof TileEntity) return ((TileEntity) object).getWorld().isRemote;
		println("Given object has no data reference to world!");
		return false;
	}

	public static int randomInt(int scale)
	{
		return rand.nextInt(scale);
	}

	public static float randomFloat(double scale)
	{
		return (float) (rand.nextFloat() * scale);
	}

	public static int evenRandomInt(int scale)
	{
		return (scale - rand.nextInt(scale * 2));
	}

	public static float randomFloat()
	{
		return rand.nextFloat();
	}

	public static float evenRandomFloat(double scale)
	{
		return (float) ((0.5 - rand.nextFloat()) * scale);
	}

	public static boolean Instanceof(Object tester, Object instace)
	{
		return Instanceof(tester.getClass(), instace.getClass());
	}

	public static boolean Instanceof(Object tester, Class instace)
	{
		return Instanceof(tester.getClass(), instace);
	}

	public static boolean Instanceof(Class tester, Class instace)
	{
		try
		{
			tester.asSubclass(instace);
			return true;
		} catch (Exception e)
		{
		}
		return false;
	}

	public static boolean isAnArray(Object object)
	{
		if (object instanceof Object[]) return true;
		return false;
	}

	public static <T> boolean isInArray(T object, T[] array)
	{
		return getPosInArray(object, array) >= 0;
	}

	public static <T> int getPosInArray(T object, T[] array)
	{
		if (isNull(object, array)) return -1;
		if (array.length == 0 || isAnArray(object)) return -1;
		int pos = -2;
		for (int a = 0; a < array.length; a++)
			if (array[a] == object)
			{
				pos = a;
				a = array.length;
			}
		return pos;
	}

	public static Minecraft minecraft() {return Minecraft.getMinecraft();}

	public static World getTheWorld() {return Minecraft.getMinecraft().theWorld;}

	public static EntityPlayer getThePlayer() {return Minecraft.getMinecraft().thePlayer;}

	public static boolean randomBool()
	{
		return rand.nextBoolean();
	}

	public static float calculatePos(final double prevPos, final double pos)
	{
		return (float) (prevPos + (pos - prevPos) * RenderEvents.partialTicks);
	}

	public static double snap(double value, double min, double max)
	{
		if (min >= max) return value;
		if (value < min) value = min;
		if (value > max) value = max;
		return value;
	}

	public static float snap(float value, float min, float max)
	{
		return (float) snap((double) value, (double) min, (double) max);
	}

	public static int snap(int value, int min, int max)
	{
		return (int) snap((double) value, (double) min, (double) max);
	}
}


package DrShadow.TechXProject.util;

import DrShadow.TechXProject.client.fx.EntityReddustFXT;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util
{
	public static final PropertyInteger meta = PropertyInteger.create("meta", 0, 15);
	static Random rand = new Random();

	public static boolean inventoryFull(IInventory inventory)
	{
		List<ItemStack> result = new ArrayList<>();
		int size = inventory.getSizeInventory();
		for (int k = 0; k < size; k++)
		{
			result.add(inventory.getStackInSlot(k));
		}

		return result.stream().allMatch(stack -> stack != null && stack.stackSize > 0);
	}

	public static boolean isAnyNull(ItemStack... stacks)
	{
		for (ItemStack stack : stacks)
		{
			if (stack == null) return true;
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	public static void spawnEntityFX(EntityFX particleFX)
	{
		if (world().isRemote)
		{
			Minecraft mc = minecraft();
			Entity ent = mc.getRenderViewEntity();
			if (ent != null && mc.effectRenderer != null)
			{
				int i = mc.gameSettings.particleSetting;
				if (!(i > 1)) mc.effectRenderer.addEffect(particleFX);
			}
		}
	}

	public static void spawnParticlesOnBorder(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, World worldIn, float r, float g, float b)
	{
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, maxY, minZ), new Vec3d(minX, minY, minZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, maxY, minZ), new Vec3d(minX, maxY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, maxY, minZ), new Vec3d(maxX, maxY, minZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, minY, minZ), new Vec3d(minX, minY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, minY, minZ), new Vec3d(maxX, minY, minZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(maxX, maxY, minZ), new Vec3d(maxX, minY, minZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, maxY, maxZ), new Vec3d(minX, minY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(maxX, minY, maxZ), new Vec3d(maxX, maxY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, minY, maxZ), new Vec3d(maxX, minY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(maxX, minY, minZ), new Vec3d(maxX, minY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(minX, maxY, maxZ), new Vec3d(maxX, maxY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
		for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(maxX, maxY, minZ), new Vec3d(maxX, maxY, maxZ), 0.3f))
			spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, r, g, b));
	}

	public static void giveExperience(EntityPlayer thePlayer, float experience)
	{
		int intExp = (int) experience;
		float fractional = experience - intExp;
		if (fractional > 0.0F)
		{
			if ((float) Math.random() < fractional)
			{
				++intExp;
			}
		}
		while (intExp > 0)
		{
			int j = EntityXPOrb.getXPSplit(intExp);
			intExp -= j;
			thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(thePlayer.worldObj, thePlayer.posX, thePlayer.posY + 0.5D, thePlayer.posZ + 0.5D, j));
		}
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

	public static ItemStack[] getStackArrayNoNull(ItemStack[] array)
	{
		if (array != null && array.length > 0)
		{
			List<ItemStack> result = new ArrayList<>();

			for (ItemStack stack : array)
			{
				if (!isNull(stack)) result.add(stack);
			}

			return result.toArray(new ItemStack[result.size()]);
		}

		return null;
	}

	public static Object[] getArrayNoNull(Object[] array)
	{
		if (array != null && array.length > 0)
		{
			List<Object> result = new ArrayList<>();

			for (Object obj : array)
			{
				if (!isNull(obj)) result.add(obj);
			}

			return result.toArray();
		}

		return null;
	}

	public static boolean isStackArrayEqual(ItemStack[] stack, ItemStack[] toCompare)
	{
		boolean ret = false;

		ItemStack[] newStack = getStackArrayNoNull(stack);
		ItemStack[] newCompare = getStackArrayNoNull(toCompare);

		if (newStack.length <= 0 || newCompare.length <= 0) return false;
		if (newStack.length != newCompare.length) return false;

		for (int i = 0; i < newStack.length; i++)
		{
			if (!OreDictionary.itemMatches(newStack[i], newCompare[i], true))
			{
				return false;
			} else ret = true;
		}

		return ret;
	}

	public static boolean isStackArrayExactEqual(ItemStack[] stack, ItemStack[] toCompare)
	{
		boolean ret = false;

		ItemStack[] newStack = getStackArrayNoNull(stack);
		ItemStack[] newCompare = getStackArrayNoNull(toCompare);

		if (newStack.length <= 0 || newCompare.length <= 0) return false;
		if (newStack.length != newCompare.length) return false;

		for (int i = 0; i < newStack.length; i++)
		{
			if (!OreDictionary.itemMatches(newStack[i], newCompare[i], true) || newStack[i].stackSize < newCompare[i].stackSize)
			{
				return false;
			} else ret = true;
		}

		return ret;
	}

	public static boolean isArrayEqual(Object[] array, Object[] toCompare)
	{
		if (array.length <= 0 || toCompare.length <= 0) return false;
		if (array.length != toCompare.length) return false;

		for (int i = 0; i < array.length; i++)
		{
			for (int k = 0; k < toCompare.length; k++)
			{
				if (array[i].equals(toCompare[k])) return true;
			}
		}

		return false;
	}

	public static int cycleArray(Object currentObj, Object[] array)
	{
		int pos = getPosInArray(currentObj, array);

		pos += 1;

		if (pos < array.length)
		{
			return pos;
		} else if (pos > array.length) return 0;

		return 0;
	}

	public static int cycleArrayReverse(Object currentObj, Object[] array)
	{
		int pos = getPosInArray(currentObj, array);

		pos -= 1;

		if (pos > -1)
		{
			return pos;
		} else if (pos < 0) return array.length - 1;

		return 0;
	}

	public static int getPosInArray(Object object, Object[] array)
	{
		if (isNull(object, array)) return -1;

		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == object)
			{
				return i;
			}
		}

		return -2;
	}

	public static boolean instanceOf(Class tester, Class instance)
	{
		try
		{
			tester.asSubclass(instance);
			return true;
		} catch (Exception ignored)
		{
		}
		return false;
	}

	public static int booleanToInt(boolean bool)
	{
		if (bool) return 1;
		return 0;
	}

	public static boolean intToBoolean(int integer)
	{
		return integer > 0;
	}

	public static boolean equals(Object obj1, Object obj2)
	{
		return obj1.equals(obj2);
	}

	public static int keepInBounds(int value, int min, int max)
	{
		if (value >= min && value <= max) return value;
		if (value < min) return min;
		if (value > max) return max;

		return value;
	}

	public static float keepInBounds(float value, float min, float max)
	{
		if (value >= min && value <= max) return value;
		if (value < min) return min;
		if (value > max) return max;

		return value;
	}

	public static double keepInBounds(double value, double min, double max)
	{
		if (value >= min && value <= max) return value;
		if (value < min) return min;
		if (value > max) return max;

		return value;
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

	public static boolean isNull(Object... objects)
	{
		boolean result = false;
		for (int i = 0; i < objects.length; i++)
		{
			if (objects[i] == null)
			{
				result = true;
				continue;
			}
		}
		return result;
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

	public static boolean randomBool()
	{
		return rand.nextBoolean();
	}

	public static Minecraft minecraft() {return Minecraft.getMinecraft();}

	public static World world() {return Minecraft.getMinecraft().theWorld;}

	public static EntityPlayer player() {return Minecraft.getMinecraft().thePlayer;}

	public static class ItemStackUtil
	{
		public static ItemStack stack(ItemStack in, ItemStack toStack)
		{
			ItemStack result;

			if (in != null)
			{
				result = in.copy();
				result.stackSize += toStack.stackSize;
			} else result = toStack.copy();

			return result;
		}
	}

	public static class GL
	{
		public static void startOpaqueRendering()
		{
			GlStateManager.depthMask(false);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
		}

		public static void endOpaqueRendering()
		{
			GlStateManager.disableBlend();
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GlStateManager.depthMask(true);
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		}
	}
}


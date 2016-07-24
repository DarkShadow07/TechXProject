package DarkS.TechXProject.util;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.annotation.Nonnull;
import java.awt.*;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Util
{
	static Random rand = new Random();

	public static List<ItemStack> mergeSameItem(@Nonnull List<ItemStack> stacks)
	{
		List<ItemStack> result = new ArrayList<>();

		for (ItemStack stack : stacks)
			if (!containsItemStack(result, stack))
			{
				int count = stack.stackSize;

				for (ItemStack stack1 : stacks)
					if (stack1 != stack && OreDictionary.itemMatches(stack, stack1, true))
						count += stack1.stackSize;

				ItemStack resultStack = stack.copy();
				resultStack.stackSize = count;

				result.add(resultStack);
			}


		return result;
	}

	public static boolean containsItemStack(List<ItemStack> stacks, ItemStack stack)
	{
		for (ItemStack found : stacks)
			if (OreDictionary.itemMatches(found, stack, true)) return true;

		return false;
	}

	public static String getMod(ItemStack stack)
	{
		String modID = Item.REGISTRY.getNameForObject(stack.getItem()).getResourceDomain();

		for (ModContainer mod : Loader.instance().getModList())
			if (mod.getModId().equalsIgnoreCase(modID))
				return mod.getName();

		return "Minecraft";
	}

	public static void stackTrace()
	{
		StringBuilder Return = new StringBuilder();

		StackTraceElement[] a1 = Thread.currentThread().getStackTrace();
		int length = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Return.append("Invoke time: ").append(dateFormat.format(cal.getTime())).append("\n");
		for (int i = 2; i < a1.length; i++)
		{
			StackTraceElement a = a1[i];
			String s = a.toString();
			Return.append(s).append("\n");
			length = Math.max(s.length(), length);
		}
		for (int b = 0; b < length / 4; b++) Return.append("_/\\_");

		Logger.info(Return.toString());
	}

	public static NBTTagCompound blocksToNBT(World world, List<BlockPos> positions, List<IBlockState> states, NBTTagCompound tag)
	{
		NBTTagList list = new NBTTagList();

		for (BlockPos pos : positions)
			for (IBlockState state : states)
			{
				NBTTagCompound blockTag = new NBTTagCompound();

				blockTag.setInteger("meta", state.getBlock().getMetaFromState(state));

				blockTag.setInteger("block", Block.REGISTRY.getIDForObject(state.getBlock()));

				if (state.getBlock() instanceof ITileEntityProvider && world.getTileEntity(pos) != null)
					blockTag.setTag("tileTag", world.getTileEntity(pos).getTileData());

				blockTag.setTag("blockPos", net.minecraft.nbt.NBTUtil.createPosTag(pos));

				list.appendTag(blockTag);
			}

		tag.setTag("blockList", list);

		return tag;
	}

	public static List<Pair<BlockPos, IBlockState>> blocksFromNBT(World world, NBTTagCompound tag)
	{
		List<Pair<BlockPos, IBlockState>> blocks = new ArrayList<>();

		NBTTagList list = tag.getTagList("blockList", 10);

		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound blockTag = list.getCompoundTagAt(i);

			int meta = blockTag.getInteger("meta");

			int block = blockTag.getInteger("block");

			BlockPos pos = net.minecraft.nbt.NBTUtil.getPosFromTag(blockTag.getCompoundTag("blockPos"));

			Block b = Block.REGISTRY.getObjectById(block);
			IBlockState state = b.getStateFromMeta(meta);

			blocks.add(new ImmutablePair<>(pos, state));
		}

		return blocks;
	}

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
			return pos;
		else if (pos > array.length) return 0;

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

	public static float fluctuate(double speed, double offset)
	{
		long wtt = (long) (world().getTotalWorldTime() + offset);
		double helper = (wtt % speed) / (speed / 2F);
		return (float) (helper > 1 ? 2 - helper : helper);
	}

	public static float fluctuateSmooth(double speed, double offset)
	{
		float fluctuate = fluctuate(speed, offset), prevFluctuate = fluctuate(speed, offset - 1);
		return PartialTicksUtil.calculatePos(prevFluctuate, fluctuate);
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
		public static Color getScreenColor(int x, int y)
		{
			IntBuffer pixels = BufferUtils.createIntBuffer(1);

			GL11.glReadPixels(x, y, 1, 1, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixels);

			Color inverse = new Color(pixels.get());

			return new Color(255 - inverse.getRed(), 255 - inverse.getGreen(), 255 - inverse.getBlue());
		}

		public static void startOpaqueRendering()
		{
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
		}

		public static void endOpaqueRendering()
		{
			GlStateManager.disableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 1.0F);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
		}
	}
}


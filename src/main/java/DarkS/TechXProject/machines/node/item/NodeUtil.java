package DarkS.TechXProject.machines.node.item;

import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.NodeNetwork;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.*;

public class NodeUtil
{
	public static List<String> getNetworkInfo(@Nonnull NodeNetwork network)
	{
		BlockPos controllerPos = network.getController().getController().getPos();

		List<String> info = new ArrayList<>();

		info.add(ChatFormatting.GRAY + "---- " + ChatFormatting.WHITE + "Node Network Info" + ChatFormatting.GRAY + " ----");
		info.add(String.format("Controller: x:%s y:%s z:%s, Using %sTF/t", controllerPos.getX(), controllerPos.getY(), controllerPos.getZ(), 8 + network.getElements().size() * 4));
		info.add("");
		info.add(" Elements:");

		for (INetworkElement element : network.getElements())
		{
			BlockPos pos = element.getTile().getPos();

			info.add(" " + element.getTile().getBlockType().getLocalizedName() + String.format(" (%s, %s, %s", pos.getX(), pos.getY(), pos.getZ()) + ") - " + (element.isInput() ? ChatFormatting.GREEN + "Input" : ChatFormatting.AQUA + "Output") + ChatFormatting.RESET + (element.getAttachedTile() == null ? "" : " (" + element.getAttachedTile().getBlockType().getLocalizedName() + ")"));
		}

		info.add("");
		info.add(ChatFormatting.GRAY + "---- " + ChatFormatting.WHITE + "Node Network Info" + ChatFormatting.GRAY + " ----");

		return info;
	}

	public static List<INetworkElement> sortElements(INetworkElement element)
	{
		if (!element.hasNetwork()) return null;

		List<INetworkElement> finalList = new ArrayList<>();

		NodeNetwork network = element.getNetwork();

		Map<Integer, INetworkElement> elementDistances = new HashMap<>();
		List<INetworkElement> elements = network.getElements();
		elements.remove(element);

		for (INetworkElement networkElement : elements)
		{
			double d = element.getTile().getPos().getDistance(networkElement.getTile().getPos().getX(), networkElement.getTile().getPos().getY(), networkElement.getTile().getPos().getZ());

			elementDistances.put((int) d, networkElement);
		}

		List<Integer> distances = new ArrayList<>();
		distances.addAll(elementDistances.keySet());

		Collections.sort(distances);

		for (int distance : distances)
		{
			finalList.add(elementDistances.get(distance));
		}

		return finalList;
	}

	public static boolean canStack(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null)
			return false;

		if (stack2 == null)
			return true;

		if (stack1.isItemStackDamageable() || stack2.isItemStackDamageable())
			return false;

		return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage() && ItemStack.areItemStackTagsEqual(stack1, stack2) && stack1.stackSize + stack2.stackSize <= stack2.getMaxStackSize();
	}

	public static ItemStack[] stack(ItemStack stack1, ItemStack stack2)
	{
		ItemStack[] returned = new ItemStack[2];

		if (canStack(stack1, stack2))
		{
			int transferedAmount = stack2 == null ? stack1.stackSize : Math.min(stack2.getMaxStackSize() - stack2.stackSize, stack1.stackSize);
			if (transferedAmount > 0)
			{
				ItemStack copyStack = stack1.splitStack(transferedAmount);
				if (stack2 == null)
				{
					stack2 = copyStack;
				} else
				{
					stack2.stackSize += transferedAmount;
				}
			}
		}

		returned[0] = stack1;
		returned[1] = stack2;

		return returned;
	}

	public static ItemStack transferStack(ItemStack stack, IInventory inventory, EnumFacing dir)
	{
		if (stack == null || inventory == null)
		{
			return null;
		}

		boolean[] insertSlots = new boolean[inventory.getSizeInventory()];

		if (inventory instanceof ISidedInventory)
		{
			int[] array = ((ISidedInventory) inventory).getSlotsForFace(dir);
			for (int in : array)
			{
				insertSlots[in] = inventory.isItemValidForSlot(in, stack) && ((ISidedInventory) inventory).canInsertItem(in, stack, dir);
			}
		} else
		{
			for (int i = 0; i < insertSlots.length; i++)
			{
				insertSlots[i] = inventory.isItemValidForSlot(i, stack);
			}
		}

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if (!insertSlots[i])
			{
				continue;
			}

			ItemStack[] combinedStacks = stack(stack, inventory.getStackInSlot(i));
			stack = combinedStacks[0];
			inventory.setInventorySlotContents(i, combinedStacks[1]);

			if (stack.stackSize <= 0)
			{
				return stack;
			}
		}

		return stack;
	}

	public static List<INetworkElement> getOutputs(List<INetworkElement> elements)
	{
		elements.removeIf(iNetworkElement -> !iNetworkElement.isOutput() || !iNetworkElement.isAttached());

		return elements;
	}

	public static List<INetworkElement> getInputs(List<INetworkElement> elements)
	{
		elements.removeIf(iNetworkElement -> !iNetworkElement.isInput());

		return elements;
	}

	public static int getStackSlot(IInventory inventory, ItemStack stack)
	{
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if (!OreDictionary.itemMatches(stack, inventory.getStackInSlot(i), false)) continue;

			return i;
		}

		return -1;
	}

	public static boolean canTransferWithFilter(INetworkElement from, INetworkElement to, ItemStack transfer)
	{
		if (from == null || to == null) return false;

		TileItemNode tileFrom = (TileItemNode) from.getTile();
		TileItemNode tileTo = (TileItemNode) to.getTile();

		return tileFrom.isInFilter(transfer) && tileTo.isInFilter(transfer) && transfer != null;
	}

	public static boolean isSameMod(ItemStack filterStack, ItemStack testStack)
	{
		if (filterStack != null && testStack != null)
		{
			String keyId = getModID(filterStack.getItem());
			String checkedId = getModID(testStack.getItem());
			return keyId.equals(checkedId);
		}

		return false;
	}

	public static String getModID(Item item)
	{
		ResourceLocation resource = GameData.getItemRegistry().getNameForObject(item);
		return resource.getResourceDomain();
	}
}

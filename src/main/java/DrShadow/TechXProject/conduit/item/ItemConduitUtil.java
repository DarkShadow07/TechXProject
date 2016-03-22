package DrShadow.TechXProject.conduit.item;

import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import DrShadow.TechXProject.conduit.network.INetworkElement;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

public class ItemConduitUtil
{
	public static List<INetworkElement> sortElements(INetworkElement element)
	{
		if (!element.hasNetwork()) return null;

		List<INetworkElement> finalList = new ArrayList<>();

		ConduitNetwork network = element.getNetwork();

		Map<Integer, INetworkElement> elementDistances = new HashMap<>();
		List<INetworkElement> elements = network.getElements();
		elements.remove(element);

		for (INetworkElement networkElement : elements)
		{
			elementDistances.put(element.distanceTo(networkElement.getTile()), networkElement);
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

	public static List<INetworkElement> sortElementsInverse(INetworkElement element)
	{
		if (!element.hasNetwork()) return null;

		List<INetworkElement> finalList = new ArrayList<>();

		ConduitNetwork network = element.getNetwork();

		Map<Integer, INetworkElement> elementDistances = new HashMap<>();
		List<INetworkElement> elements = network.getElements();
		elements.remove(element);

		for (INetworkElement networkElement : elements)
		{
			elementDistances.put(element.distanceTo(networkElement.getTile()), networkElement);
		}

		List<Integer> distances = new ArrayList<>();
		distances.addAll(elementDistances.keySet());

		Collections.sort(distances);
		Collections.reverse(distances);

		for (int distance : distances)
		{
			finalList.add(elementDistances.get(distance));
		}

		return finalList;
	}

	public static boolean canStack(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null)
		{
			return false;
		}

		if (stack2 == null)
		{
			return true;
		}

		if (stack1.isItemStackDamageable() ^ stack2.isItemStackDamageable())
		{
			return false;
		}

		return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage() && ItemStack.areItemStackTagsEqual(stack1, stack2);
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
		List<INetworkElement> result = new ArrayList<>();

		for (INetworkElement element : elements)
		{
			if (element.isOutput()) result.add(element);
		}

		return result;
	}

	public static List<INetworkElement> getInputs(List<INetworkElement> elements)
	{
		List<INetworkElement> result = new ArrayList<>();

		for (INetworkElement element : elements)
		{
			if (element.isInput()) result.add(element);
		}

		return result;
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

		TileConduitItem tileFrom = (TileConduitItem) from.getTile();
		TileConduitItem tileTo = (TileConduitItem) to.getTile();

		return tileFrom.isInFilter(transfer) && tileTo.isInFilter(transfer) && transfer != null;
	}

	public static boolean isSameMod(ItemStack filterStack, ItemStack testStack)
	{
		if (filterStack != null && testStack != null && filterStack.getItem() != null && testStack.getItem() != null)
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

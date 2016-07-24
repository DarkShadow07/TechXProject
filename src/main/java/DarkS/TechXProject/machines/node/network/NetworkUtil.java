package DarkS.TechXProject.machines.node.network;

import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.INetworkRelay;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.machines.node.item.TileItemNode;
import DarkS.TechXProject.machines.node.network.controller.TileNetworkController;
import DarkS.TechXProject.util.ChatUtil;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkUtil
{
	/**
	 * DO NOT REQUEST AN ITEMSTACK WITH A BIGGER STACKSIZE THAN THE TOTAL STACKSIZE
	 */
	public static ItemStack getFromNetwork(ItemStack stack, NodeNetwork network)
	{
		ItemStack finalStack = null;
		List<Pair<ItemStack, Pair<IInventory, Integer>>> stacks = new ArrayList<>();

		for (INetworkElement element : network.getElements())
			if (element != null && element instanceof TileItemNode && element.isAttached() && element.getAttachedTile() instanceof IInventory)
			{
				IInventory inv = (IInventory) element.getAttachedTile();

				for (int i = 0; i < inv.getSizeInventory(); i++)
					if (inv.getStackInSlot(i) != null)
						stacks.add(Pair.of(inv.getStackInSlot(i), Pair.of(inv, i)));
			}

		for (Pair<ItemStack, Pair<IInventory, Integer>> pair : stacks)
			if (OreDictionary.itemMatches(stack, pair.getLeft(), true))
				while (finalStack == null || finalStack.stackSize < stack.stackSize)
				{
					if (finalStack == null)
					{
						finalStack = pair.getLeft().copy();
						finalStack.stackSize = pair.getLeft().stackSize;
					} else
						finalStack.stackSize += Math.min(stack.stackSize - finalStack.stackSize, pair.getLeft().stackSize);

					pair.getRight().getLeft().decrStackSize(pair.getRight().getRight(), finalStack.stackSize);
				}

		return finalStack;
	}

	public static NodeNetwork readNetwork(World world, List<BlockPos> positions, INetworkContainer container)
	{
		if (container == null) container = new TileNetworkController();

		NodeNetwork result = new NodeNetwork(container);

		for (BlockPos pos : positions)
			result.addToNetwork((INetworkElement) world.getTileEntity(pos));

		return result;
	}

	public static void writeNetworkNBT(NBTTagCompound tag, NodeNetwork network)
	{
		if (network.getElements().isEmpty()) return;

		List<BlockPos> positions = network.getElements().stream().map(element -> element.getTile().getPos()).collect(Collectors.toList());

		NBTTagList tagList = new NBTTagList();

		for (BlockPos pos : positions)
		{
			NBTTagCompound posTag = new NBTTagCompound();

			posTag.setInteger("xPos", pos.getX());
			posTag.setInteger("yPos", pos.getY());
			posTag.setInteger("zPos", pos.getZ());

			tagList.appendTag(posTag);
		}

		tag.setTag("elementPos", tagList);
	}

	public static List<BlockPos> readNetworkNBT(NBTTagCompound tag)
	{
		List<BlockPos> positions = new ArrayList<>();

		NBTTagList tagList = tag.getTagList("elementPos", 10);

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound posTag = tagList.getCompoundTagAt(i);

			positions.add(new BlockPos(posTag.getInteger("xPos"), posTag.getInteger("yPos"), posTag.getInteger("zPos")));
		}

		return positions;
	}

	public static void link(Object from, Object to)
	{
		if (from == null || to == null)
		{
			ChatUtil.sendNoSpam(Util.player(), ChatFormatting.RED + "Connection Failed!");

			return;
		}

		if (from instanceof INetworkElement && to instanceof INetworkRelay)
		{
			link((INetworkElement) from, (INetworkRelay) to);
		} else if (from instanceof INetworkRelay && to instanceof INetworkElement)
		{
			link((INetworkRelay) from, (INetworkElement) to);
		} else ChatUtil.sendNoSpam(Util.player(), ChatFormatting.RED + "Connection Failed!");
	}

	private static void link(INetworkElement from, INetworkRelay to)
	{
		from.setNetwork(((INetworkElement) to).getNetwork());

		to.addElement(from);

		from.setNetwork(((INetworkElement) to).getNetwork());
	}

	private static void link(INetworkRelay from, INetworkElement to)
	{
		to.setNetwork(((INetworkElement) from).getNetwork());

		from.addElement(to);

		to.setNetwork(((INetworkElement) from).getNetwork());
	}
}

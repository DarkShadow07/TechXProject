package DrShadow.TechXProject.conduit.network;

import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.api.network.INetworkRelay;
import DrShadow.TechXProject.util.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtil
{
	public static ConduitNetwork networkFromPosList(World world, List<BlockPos> positions, INetworkContainer container)
	{
		ConduitNetwork result = new ConduitNetwork(container);

		for (BlockPos pos : positions)
		{
			if (pos != null && world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof INetworkElement)
			{
				result.addToNetwork((INetworkElement) world.getTileEntity(pos));
				((INetworkElement) world.getTileEntity(pos)).setNetwork(container.getNetwork());
			}
		}

		return result;
	}

	public static void writeNetworkNBT(NBTTagCompound tag, ConduitNetwork network)
	{
		List<BlockPos> positions = new ArrayList<>();

		for (INetworkElement element : network.getElements())
		{
			if (element != null && element.getTile() != null) positions.add(element.getTile().getPos());
		}

		NBTTagList tagList = new NBTTagList();

		for (BlockPos pos : positions)
		{
			if (pos != null)
			{
				NBTTagCompound posTag = new NBTTagCompound();

				posTag.setInteger("x", pos.getX());
				posTag.setInteger("y", pos.getY());
				posTag.setInteger("z", pos.getZ());

				tagList.appendTag(posTag);
			}
		}

		tag.setTag("positions", tagList);
	}

	public static List<BlockPos> readNetworkNBT(NBTTagCompound tag)
	{
		List<BlockPos> positions = new ArrayList<>();

		NBTTagList tagList = tag.getTagList("positions", 10);

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound posTag = tagList.getCompoundTagAt(i);

			positions.add(new BlockPos(posTag.getInteger("x"), posTag.getInteger("y"), posTag.getInteger("z")));
		}

		return positions;
	}

	public static void link(Object from, Object to)
	{
		if (from == null || to == null)
		{
			ChatUtil.sendNoSpamClient(ChatFormatting.RED + "Connection Failed!");

			return;
		}

		if (from instanceof INetworkElement && to instanceof INetworkRelay)
		{
			link((INetworkElement) from, (INetworkRelay) to);
		} else if (from instanceof INetworkRelay && to instanceof INetworkElement)
		{
			link((INetworkRelay) from, (INetworkElement) to);
		} else ChatUtil.sendNoSpamClient(ChatFormatting.RED + "Connection Failed!");
	}

	public static void link(INetworkElement from, INetworkRelay to)
	{
		from.setNetwork(((INetworkElement) to).getNetwork());

		to.addElement(from);

		from.setNetwork(((INetworkElement) to).getNetwork());
	}

	public static void link(INetworkRelay from, INetworkElement to)
	{
		to.setNetwork(((INetworkElement) from).getNetwork());

		from.addElement(to);

		to.setNetwork(((INetworkElement) from).getNetwork());
	}
}

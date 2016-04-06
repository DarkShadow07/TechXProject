package DrShadow.TechXProject.conduit.network.relay;

import DrShadow.TechXProject.api.network.INetworkContainer;
import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.api.network.INetworkRelay;
import DrShadow.TechXProject.conduit.network.ConduitNetwork;
import DrShadow.TechXProject.fx.EntityReddustFXT;
import DrShadow.TechXProject.tileEntities.ModTileEntity;
import DrShadow.TechXProject.util.Util;
import DrShadow.TechXProject.util.VectorUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class TileNetworkRelay extends ModTileEntity implements INetworkRelay, INetworkElement
{
	private ArrayList<BlockPos> elementsPos = new ArrayList<>();

	public TileNetworkRelay()
	{

	}

	@Override
	public void update()
	{
		EnumFacing facing = EnumFacing.getFront(getBlockMetadata());

		TileEntity tile = worldObj.getTileEntity(pos.offset(facing));

		if (tile != null && tile instanceof INetworkContainer)
		{
			INetworkContainer container = (INetworkContainer) tile;

			for (INetworkElement element : getElements())
			{
				setNetwork(container.addToNetwork(element));
			}
		}
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		NBTTagList elements = new NBTTagList();

		for (BlockPos pos : elementsPos)
		{
			if (pos != null)
			{
				NBTTagCompound elementPos = new NBTTagCompound();
				elementPos.setInteger("x", pos.getX());
				elementPos.setInteger("y", pos.getY());
				elementPos.setInteger("z", pos.getZ());

				elements.appendTag(elementPos);
			}
		}

		tag.setTag("elements", elements);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		NBTTagList elements = tag.getTagList("elements", 10);

		elementsPos.clear();

		for (int i = 0; i < elements.tagCount(); i++)
		{
			int x = elements.getCompoundTagAt(i).getInteger("x");
			int y = elements.getCompoundTagAt(i).getInteger("y");
			int z = elements.getCompoundTagAt(i).getInteger("z");

			BlockPos elementPos = new BlockPos(x, y, z);

			elementsPos.add(elementPos);
		}
	}

	@Override
	public List<INetworkElement> getElements()
	{
		List<INetworkElement> elements = new ArrayList<>();

		for (BlockPos pos : elementsPos)
		{
			if (pos != null && worldObj.getTileEntity(pos) != null && worldObj.getTileEntity(pos) instanceof INetworkElement)
			{
				elements.add((INetworkElement) worldObj.getTileEntity(pos));
			}
		}

		return elements;
	}

	@Override
	public void addElement(INetworkElement element)
	{
		if (!elementsPos.contains(element.getTile().getPos()))
		{
			elementsPos.add(element.getTile().getPos());

			markDirty();
		}
	}

	@Override
	public void drawLines()
	{
		for (INetworkElement element : getElements())
		{
			for (Vec3d vec : VectorUtil.dotsOnRay(new Vec3d(pos).addVector(0.5, 0.5, 0.5), new Vec3d(element.getTile().getPos()).addVector(0.5, 0.5, 0.5), 0.1f))
			{
				Util.spawnEntityFX(new EntityReddustFXT(worldObj, vec.xCoord, vec.yCoord, vec.zCoord, 0.01f, 0.45f, 0.55f));
			}
		}
	}

	@Override
	public ConduitNetwork getNetwork()
	{
		EnumFacing facing = EnumFacing.getFront(getBlockMetadata());

		TileEntity tile = worldObj.getTileEntity(pos.offset(facing));

		if (tile == null || !(tile instanceof INetworkContainer)) return null;

		INetworkContainer container = (INetworkContainer) tile;

		return container.getNetwork();
	}

	@Override
	public void setNetwork(ConduitNetwork network)
	{

	}

	@Override
	public ConduitNetwork addToNetwork(INetworkElement toAdd)
	{
		return null;
	}

	@Override
	public ConduitNetwork removeFromNetwork(INetworkElement toRemove)
	{
		return null;
	}

	@Override
	public TileEntity getTile()
	{
		return this;
	}

	@Override
	public IInventory getInventory()
	{
		return null;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return null;
	}

	@Override
	public boolean hasInventory()
	{
		return false;
	}

	@Override
	public int distanceTo(TileEntity to)
	{
		return 0;
	}

	@Override
	public INetworkContainer getController()
	{
		return null;
	}

	@Override
	public boolean isInput()
	{
		return false;
	}

	@Override
	public boolean isOutput()
	{
		return false;
	}

	@Override
	public boolean isActive()
	{
		return false;
	}

	@Override
	public void setActive(boolean act)
	{

	}

	@Override
	public boolean hasNetwork()
	{
		return false;
	}
}

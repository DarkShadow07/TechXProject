package DarkS.TechXProject.machines.node.network.relay;

import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.INetworkRelay;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.blocks.tile.highlight.IHighlightProvider;
import DarkS.TechXProject.blocks.tile.highlight.SelectionBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class TileNetworkRelay extends TileBase implements INetworkRelay, INetworkElement<TileNetworkRelay>, IHighlightProvider
{
	private ArrayList<BlockPos> elementsPos = new ArrayList<>();

	public TileNetworkRelay()
	{

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		NBTTagList elements = new NBTTagList();

		for (BlockPos pos : elementsPos)
			if (pos != null)
			{
				NBTTagCompound elementPos = new NBTTagCompound();
				elementPos.setInteger("xPos", pos.getX());
				elementPos.setInteger("yPos", pos.getY());
				elementPos.setInteger("zPos", pos.getZ());

				elements.appendTag(elementPos);
			}

		tag.setTag("elements", elements);

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList elements = tag.getTagList("elements", 10);

		elementsPos.clear();

		for (int i = 0; i < elements.tagCount(); i++)
		{
			int x = elements.getCompoundTagAt(i).getInteger("xPos");
			int y = elements.getCompoundTagAt(i).getInteger("yPos");
			int z = elements.getCompoundTagAt(i).getInteger("zPos");

			BlockPos elementPos = new BlockPos(x, y, z);

			elementsPos.add(elementPos);
		}

		super.readFromNBT(tag);
	}

	@Override
	public List<INetworkElement> getElements()
	{
		List<INetworkElement> elements = new ArrayList<>();

		for (BlockPos pos : elementsPos)
			if (pos != null && worldObj.getTileEntity(pos) != null && worldObj.getTileEntity(pos) instanceof INetworkElement)
				elements.add((INetworkElement) worldObj.getTileEntity(pos));

		return elements;
	}

	@Override
	public void addElement(INetworkElement element)
	{
		if (!elementsPos.contains(element.getTile().getPos()))
			elementsPos.add(element.getTile().getPos());
	}

	@Override
	public void drawLines()
	{

	}

	@Override
	public NodeNetwork getNetwork()
	{
		EnumFacing facing = EnumFacing.getFront(getBlockMetadata());

		TileEntity tile = worldObj.getTileEntity(pos.offset(facing));

		if (tile == null || !(tile instanceof INetworkContainer)) return null;

		INetworkContainer container = (INetworkContainer) tile;

		return container.getNetwork();
	}

	@Override
	public void setNetwork(NodeNetwork network)
	{

	}

	@Override
	public TileNetworkRelay getTile()
	{
		return this;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return null;
	}

	@Override
	public boolean isAttached()
	{
		return false;
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
	public void setInput(boolean input)
	{

	}

	@Override
	public boolean isOutput()
	{
		return false;
	}

	@Override
	public void setOutput(boolean output)
	{

	}

	@Override
	public boolean isActive()
	{
		return true;
	}

	@Override
	public boolean hasNetwork()
	{
		return false;
	}

	@Override
	public SelectionBox[] getBoxes()
	{
		return new SelectionBox[]{new SelectionBox(0, new AxisAlignedBB(0.1875, 0, 0.1875, 0.8125, 0.125, 0.8125), new AxisAlignedBB(0.25, 0.125, 0.25, 0.75, 0.1875, 0.75)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite())};
	}

	@Override
	public SelectionBox[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end)
	{
		return getBoxes();
	}

	@Override
	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end)
	{
		return SelectionBox.rayTraceAll(getBoxes(), pos, start, end);
	}

	@Override
	public boolean onBoxClicked(SelectionBox box, EntityPlayer clicker)
	{
		return false;
	}
}

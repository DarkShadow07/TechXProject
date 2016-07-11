package DarkS.TechXProject.node.redstone;

import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
import DarkS.TechXProject.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TileRedstoneNode extends TileBase implements INetworkElement<TileRedstoneNode>, IHighlightProvider, ITickable
{
	private NodeNetwork network;

	private boolean isInput = true, isOutput = false, strong = false;

	private int power = 0;

	private int[] channel = new int[3];

	public TileRedstoneNode()
	{
		channel[0] = ItemDye.DYE_COLORS[0];
		channel[1] = ItemDye.DYE_COLORS[0];
		channel[2] = ItemDye.DYE_COLORS[0];
	}

	@Override
	public void update()
	{
		if (!isActive()) return;

		if (isOutput())
			findPower();

		if (isInput())
			power = worldObj.isBlockIndirectlyGettingPowered(pos);
	}

	private void findPower()
	{
		if (!hasNetwork())
			return;

		List<INetworkElement> elements = network.getElements().stream().filter(element -> element instanceof TileRedstoneNode && element.isInput()).collect(Collectors.toList());

		for (INetworkElement element : elements)
		{
			TileRedstoneNode node = (TileRedstoneNode) element;

			if (Arrays.equals(node.getChannel(), channel))
			{
				setPower(node.getPower());

				return;
			}
		}

		setPower(0);
	}

	public int[] getChannel()
	{
		return channel;
	}

	public void setPower(int power)
	{
		this.power = power;

		worldObj.notifyNeighborsOfStateChange(pos, getBlockType());
	}

	public int getPower()
	{
		return power;
	}

	public void setStrong(boolean strong)
	{
		this.strong = strong;
	}

	public boolean isStrong()
	{
		return strong;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("channel_0", channel[0]);
		tag.setInteger("channel_1", channel[1]);
		tag.setInteger("channel_2", channel[2]);

		tag.setBoolean("isInput", isInput);

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		channel[0] = tag.getInteger("channel_0");
		channel[1] = tag.getInteger("channel_1");
		channel[2] = tag.getInteger("channel_2");

		isInput = tag.getBoolean("isInput");
		isOutput = !isInput;

		super.readFromNBT(tag);
	}

	@Override
	public NodeNetwork getNetwork()
	{
		return network.getController().getNetwork();
	}

	@Override
	public void setNetwork(NodeNetwork network)
	{
		this.network = network;
	}

	@Override
	public TileRedstoneNode getTile()
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
		return network.getController();
	}

	@Override
	public boolean isInput()
	{
		return isInput;
	}

	@Override
	public boolean isOutput()
	{
		return isOutput;
	}

	@Override
	public boolean isActive()
	{
		return hasNetwork() && network.getController().isActive();
	}

	@Override
	public boolean hasNetwork()
	{
		return network != null;
	}

	@Override
	public SelectionBox[] getBoxes()
	{
		return new SelectionBox[]{
				new SelectionBox(0, new AxisAlignedBB(0.125, 0.125f, 0.25, 0.1875, 0.15625, 0.375)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite()),
				new SelectionBox(1, new AxisAlignedBB(0.125, 0.125f, 0.4375, 0.1875, 0.15625, 0.5625)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite()),
				new SelectionBox(2, new AxisAlignedBB(0.125, 0.125f, 0.625, 0.1875, 0.15625, 0.75)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite()),
				new SelectionBox(3, new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375), new AxisAlignedBB(0.1875, 0.125, 0.1875, 0.8125, 0.1875, 0.8125)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite())
		};
	}

	@Override
	public SelectionBox[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end)
	{
		for (SelectionBox box : getBoxes())
			if (box.rayTrace(pos, start, end) != null)
				return new SelectionBox[]{box};

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
		if (box.getBoxId() < 3 && clicker.getHeldItemMainhand() != null && clicker.getHeldItemMainhand().getItem() instanceof ItemDye)
		{
			channel[box.getBoxId()] = ItemDye.DYE_COLORS[clicker.getHeldItemMainhand().getItemDamage()];

			if (!clicker.capabilities.isCreativeMode)
				clicker.getHeldItemMainhand().stackSize -= 1;

			return true;
		} else if (clicker.getHeldItemMainhand() == null)
		{
			isInput = false;
			isOutput = true;

			return true;
		}

		return false;
	}
}

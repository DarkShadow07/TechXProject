package DarkS.TechXProject.node.energy;

import DarkS.TechXProject.api.IWrench;
import DarkS.TechXProject.api.energy.IEnergyContainer;
import DarkS.TechXProject.api.network.ConduitNetwork;
import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
import DarkS.TechXProject.node.item.NodeUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;

public class TileEnergyNode extends TileEnergyContainer implements INetworkElement<TileEnergyNode>, IHighlightProvider
{
	public ConduitNetwork network;

	private IEnergyContainer energyContainer;

	private boolean active = true, isInput = false, isOutput = false;

	public TileEnergyNode()
	{
		super(32768, 1024);
	}

	@Override
	public void update()
	{
		updateEnergyContainer();

		doTransfer();

		if (Util.player() != null && Util.player().getHeldItemMainhand() != null && Util.player().getHeldItemMainhand().getItem() instanceof IWrench)
		{
			Random r = new Random();

			float x = pos.getX() + r.nextFloat();
			float y = pos.getY() + r.nextFloat();
			float z = pos.getZ() + r.nextFloat();

			if (isInput())
				worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, x, y, z, -1, 1, 0, 0);
			else
				worldObj.spawnParticle(EnumParticleTypes.REDSTONE, true, x, y, z, -1, -1, 1, 0);

		}
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		super.toNBT(tag);

		NBTTagCompound booleans = new NBTTagCompound();

		booleans.setBoolean("input", isInput);
		booleans.setBoolean("output", isOutput);

		tag.setTag("booleans", booleans);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		super.fromNBT(tag);

		NBTTagCompound booleans = tag.getCompoundTag("booleans");

		isInput = booleans.getBoolean("input");
		isOutput = booleans.getBoolean("output");
	}

	public void doTransfer()
	{
		if (!hasNetwork()) return;

		if (isAttached())
		{
			if (isInput())
			{
				transferFromContainer();

				transferForOthers();
			} else if (isOutput())
			{
				transferToContainer();
			}
		}
	}

	public void transferToContainer()
	{
		if (energyContainer.canInsert(EnumFacing.getFront(getBlockMetadata())))
		{
			int transfer = Math.min(getMaxTransfer(), energyContainer.getMaxTransfer());

			if (subtractEnergy(energyContainer.addEnergy(transfer, true), true))
			{
				subtractEnergy(energyContainer.addEnergy(transfer, false), false);
			}
		}
	}

	public void transferFromContainer()
	{
		if (energyContainer.canExtract(EnumFacing.getFront(getBlockMetadata()).getOpposite()))
		{
			int transfer = Math.min(getMaxTransfer(), energyContainer.getMaxTransfer());

			if (energyContainer.subtractEnergy(addEnergy(transfer, true), true))
			{
				energyContainer.subtractEnergy(addEnergy(transfer, false), false);
			}
		}
	}

	public void transferForOthers()
	{
		List<INetworkElement> elements = NodeUtil.sortElements(this);

		List<INetworkElement> outputs = NodeUtil.getOutputs(elements);

		if (!elements.isEmpty() && !outputs.isEmpty())
		{
			for (INetworkElement output : outputs)
			{
				if (output instanceof IEnergyContainer && output.isActive() && isActive())
				{
					IEnergyContainer outputEnergy = (IEnergyContainer) output;

					int transfer = Math.min(getMaxTransfer(), ((IEnergyContainer) output).getMaxTransfer());

					if (subtractEnergy(outputEnergy.addEnergy(transfer, true), true))
					{
						subtractEnergy(outputEnergy.addEnergy(transfer, false), false);
					}
				}
			}
		}
	}

	public void updateEnergyContainer()
	{
		TileEntity tile = worldObj.getTileEntity(pos.offset(EnumFacing.getFront(getBlockMetadata())));

		if (tile != null && tile instanceof IEnergyContainer)
		{
			energyContainer = (IEnergyContainer) tile;
		} else energyContainer = null;
	}

	@Override
	public ConduitNetwork getNetwork()
	{
		return network;
	}

	@Override
	public void setNetwork(ConduitNetwork network)
	{
		this.network = network;
	}

	@Override
	public ConduitNetwork addToNetwork(INetworkElement toAdd)
	{
		network.addToNetwork(toAdd);

		return network;
	}

	@Override
	public ConduitNetwork removeFromNetwork(INetworkElement toRemove)
	{
		network.removeFromNetwork(toRemove);

		return network;
	}

	@Override
	public TileEnergyNode getTile()
	{
		return this;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return (TileEntity) energyContainer;
	}

	@Override
	public boolean isAttached()
	{
		return energyContainer != null;
	}

	@Override
	public int distanceTo(TileEntity to)
	{
		return (int) getTile().getPos().distanceSq(to.getPos());
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

	public void setInput(boolean input)
	{
		isInput = input;
	}

	@Override
	public boolean isOutput()
	{
		return isOutput;
	}

	public void setOutput(boolean output)
	{
		isOutput = output;
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	@Override
	public void setActive(boolean act)
	{
		active = act;
	}

	@Override
	public boolean hasNetwork()
	{
		return network != null;
	}

	@Override
	public SelectionBox[] getBoxes()
	{
		return new SelectionBox[]{new SelectionBox(0, new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375), new AxisAlignedBB(0.1875, 0.125, 0.1875, 0.8125, 0.1875, 0.8125)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite())};
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

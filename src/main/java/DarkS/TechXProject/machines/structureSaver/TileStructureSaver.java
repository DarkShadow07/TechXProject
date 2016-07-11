package DarkS.TechXProject.machines.structureSaver;

import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.packets.PacketUpdateEnergy;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.NBTUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class TileStructureSaver extends TileEnergyContainer
{
	public static final int drain = 160;

	public int x, y, z, xS, yS, zS, xE, yE, zE, stored = 0, cycles = 1;

	public boolean started = false, working = false, end = false;

	private boolean saving = false;

	private List<BlockPos> positions = new ArrayList<>();
	private List<IBlockState> states = new ArrayList<>();

	public TileStructureSaver()
	{
		super(100000, 10000);

		for (EnumFacing facing : EnumFacing.values())
			setSideInput(facing);
	}

	public void start(BlockPos start, BlockPos end)
	{
		if (!started)
		{
			xS = start.getX();
			yS = start.getY();
			zS = start.getZ();

			xE = end.getX();
			yE = end.getY();
			zE = end.getZ();

			started = true;
			working = true;
			this.end = false;

			stored = 0;

			x = xS;
			y = yS;
			z = zS;
		}
	}

	@Override
	public void update()
	{
		working = getEnergy() >= drain;

		saving = false;

		if (saving)
			saveStructure();
		else
			loadStructure();

		move();
	}

	private void loadStructure()
	{
		if (working && started && !end)
		{
			if (positions.isEmpty() || states.isEmpty()) return;

			BlockPos getting = new BlockPos(x, y, z);

			int index;

			if ((index = positions.indexOf(getting)) != -1)
			{
				worldObj.setBlockState(getting, states.get(index));
			}
		}
	}

	private void saveStructure()
	{
		if (!worldObj.isRemote && working && started && !end)
		{
			for (int i = 0; i < cycles; i++)
			{
				BlockPos getting = new BlockPos(x, y, z);

				if (worldObj.getTileEntity(getting) != null && worldObj.getTileEntity(getting) == this)
				{
					move();

					return;
				}

				positions.add(getting);
				states.add(worldObj.getBlockState(getting));

				worldObj.setBlockToAir(getting);

				stored += 1;
				subtractEnergy(drain, false);

				PacketHandler.sendToAllAround(new PacketUpdateEnergy(getEnergy(), this), this);
			}
		}
	}

	public void loadFromItem(ItemStack stack)
	{
		positions.clear();
		states.clear();

		for (Pair<BlockPos, IBlockState> state : Util.blocksFromNBT(worldObj, stack.getTagCompound()))
		{
			positions.add(state.getLeft());
			states.add(state.getRight());
		}

		Logger.info(positions);
		Logger.info(states);

		stack.setTagCompound(new NBTTagCompound());
	}

	public void saveToItem(ItemStack stack)
	{
		stack = NBTUtil.checkNBT(stack);

		Util.blocksToNBT(worldObj, positions, states, stack.getTagCompound());

		Logger.info(stack.getTagCompound());

		positions.clear();
		states.clear();
	}

	private void move()
	{
		int xD = xS - xE;
		int zD = zS - zE;

		xD = xD * xD;
		zD = zD * zD;

		xD = (int) Math.sqrt(xD);
		zD = (int) Math.sqrt(zD);

		int xOffset = Math.abs(xS - x);
		int zOffset = Math.abs(zS - z);

		y -= 1;

		if (y < yE)
		{

			y = yS;

			if (xOffset < xD)
			{
				if (x > xE)
				{
					x -= 1;
				} else
				{
					x += 1;
				}
			} else
			{
				x = xS;

				if (zOffset < zD)
				{
					if (z >= zE)
					{
						z -= 1;
					} else z += 1;
				} else
				{
					working = false;
					started = false;
					end = true;
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("stored", stored);

		data.setInteger("xM", x);
		data.setInteger("yM", y);
		data.setInteger("zM", z);

		data.setInteger("xS", xS);
		data.setInteger("yS", yS);
		data.setInteger("zS", zS);

		data.setInteger("xE", xE);
		data.setInteger("yE", yE);
		data.setInteger("zE", zE);

		data.setInteger("cycles", cycles);

		data.setBoolean("started", started);
		data.setBoolean("active", working);
		data.setBoolean("end", end);

		tag.setTag("data", data);

		Util.blocksToNBT(worldObj, positions, states, tag);

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = tag.getCompoundTag("data");

		stored = data.getInteger("stored");

		x = data.getInteger("xM");
		y = data.getInteger("yM");
		z = data.getInteger("zM");

		xS = data.getInteger("xS");
		yS = data.getInteger("yS");
		zS = data.getInteger("zS");

		xE = data.getInteger("xE");
		yE = data.getInteger("yE");
		zE = data.getInteger("zE");

		cycles = data.getInteger("cycles");

		started = data.getBoolean("started");
		working = data.getBoolean("active");
		end = data.getBoolean("end");

		for (Pair<BlockPos, IBlockState> state : Util.blocksFromNBT(worldObj, tag))
		{
			positions.add(state.getLeft());
			states.add(state.getRight());
		}
		super.readFromNBT(tag);
	}
}

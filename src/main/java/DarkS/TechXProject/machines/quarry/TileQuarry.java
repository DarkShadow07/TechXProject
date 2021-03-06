package DarkS.TechXProject.machines.quarry;

import DarkS.TechXProject.blocks.tile.TileEnergyContainer;
import DarkS.TechXProject.machines.node.item.NodeUtil;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.packets.PacketUpdateEnergy;
import DarkS.TechXProject.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TileQuarry extends TileEnergyContainer
{
	public static int drain = 3600;
	public int x, y, z, xS, yS, zS, xE, yE, zE, mined = 0, cycles = 1;
	public String error = "";
	public boolean started = false, working = false, end = false;
	private boolean silktouch = false;
	private int fortune = 0;

	public TileQuarry()
	{
		super(5000000, 64000);

		for (EnumFacing facing : EnumFacing.VALUES)
		{
			setSideInput(facing);
		}
	}

	public void start(BlockPos start, BlockPos end)
	{
		if (!started)
		{
			xS = start.getX();
			yS = start.getY() + 5;
			zS = start.getZ();

			xE = end.getX();
			yE = 0;
			zE = end.getZ();

			started = true;
			working = true;
			this.end = false;

			mined = 0;

			x = xS;
			y = yS;
			z = zS;
		}
	}

	@Override
	public void update()
	{
		if (worldObj.getTileEntity(pos.offset(EnumFacing.UP)) != null && worldObj.getTileEntity(pos.offset(EnumFacing.UP)) instanceof IInventory)
		{
			error = "";
			working = true;

			if (!Util.inventoryFull((IInventory) worldObj.getTileEntity(pos.offset(EnumFacing.UP))))
			{
				error = "";
				working = true;
			} else
			{
				working = false;
				error = worldObj.getTileEntity(pos.offset(EnumFacing.UP)).getBlockType().getLocalizedName() + " is Full! Quarry Stopped";
				return;
			}
		} else
		{
			working = false;
			error = "Container non Existent! Quarry Stopped";
			return;
		}
		if (getEnergy() >= drain)
		{
			error = "";
			working = true;
		} else
		{
			working = false;
			error = "Low Energy! Quarry Stopped";
			return;
		}

		if (!worldObj.isRemote && working && started && !end)
		{
			for (int i = 0; i < cycles; i++)
			{
				move();

				BlockPos mining = new BlockPos(x, y, z);
				Block block = worldObj.getBlockState(mining).getBlock();

				TileEntity tile = worldObj.getTileEntity(pos.offset(EnumFacing.UP));
				IInventory inventory = (IInventory) tile;

				if (!(block instanceof BlockLiquid) && block.getDefaultState() != Blocks.AIR.getDefaultState() && block.getDefaultState() != Blocks.BEDROCK.getDefaultState() && !block.hasTileEntity(block.getDefaultState()))
				{
					if (silktouch)
					{
						if (NodeUtil.transferStack(new ItemStack(block), inventory, EnumFacing.DOWN) != null)
						{
							worldObj.destroyBlock(mining, false);
						}
					} else
					{
						for (ItemStack stack : block.getDrops(worldObj, mining, worldObj.getBlockState(mining), 0))
						{
							Random r = new Random();

							ItemStack dropStack = stack.copy();
							dropStack.stackSize = block.quantityDroppedWithBonus(fortune, r);

							if (NodeUtil.transferStack(dropStack, inventory, EnumFacing.DOWN) != null)
							{
								worldObj.destroyBlock(mining, false);
							}
						}
					}

					mined += 1;
					subtractEnergy(drain, false);

					PacketHandler.sendToAllAround(new PacketUpdateEnergy(getEnergy(), this), this);
				}
			}
		}
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

		if (y < 0)
		{
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

			y = yS;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("mined", mined);

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
		data.setInteger("fortune", fortune);

		data.setBoolean("started", started);
		data.setBoolean("active", working);
		data.setBoolean("silktouch", silktouch);
		data.setBoolean("end", end);

		tag.setTag("data", data);

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound data = tag.getCompoundTag("data");

		mined = data.getInteger("mined");

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
		fortune = data.getInteger("fortune");

		started = data.getBoolean("started");
		working = data.getBoolean("active");
		silktouch = data.getBoolean("silktouch");
		end = data.getBoolean("end");

		super.readFromNBT(tag);
	}
}

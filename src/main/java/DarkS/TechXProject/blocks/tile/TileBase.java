package DarkS.TechXProject.blocks.tile;

import DarkS.TechXProject.util.Util;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class TileBase extends TileEntity
{
	public void markForUpdate()
	{
		if (worldObj == null) return;

		worldObj.markBlockRangeForRenderUpdate(pos, pos);

		Block block = worldObj.getBlockState(pos).getBlock();

		if (ForgeEventFactory.onNeighborNotify(worldObj, pos, worldObj.getBlockState(pos), EnumSet.allOf(EnumFacing.class)).isCanceled())
			return;

		worldObj.notifyNeighborsOfStateChange(pos, block);
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity)
	{
		super.onDataPacket(networkManager, s35PacketUpdateTileEntity);

		readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
		worldObj.markBlockRangeForRenderUpdate(pos, pos);

		markForUpdate();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
	}

	protected TileEntity[] getTilesOnSides()
	{
		TileEntity[] result = new TileEntity[6];

		if (!Util.isNull(worldObj, pos))
		{
			for (int i = 0; i < 6; i++) result[i] = worldObj.getTileEntity(pos.offset(EnumFacing.getFront(i)));
		}
		return result;
	}
}

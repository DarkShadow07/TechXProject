package DarkS.TechXProject.blocks.tile;

import DarkS.TechXProject.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		toNBT(compound);

		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		fromNBT(compound);

		super.readFromNBT(compound);
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new SPacketUpdateTileEntity(this.pos, 1, data);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity)
	{
		readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
		worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
		markForUpdate();
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	public void toNBT(NBTTagCompound tag)
	{

	}

	public void fromNBT(NBTTagCompound tag)
	{

	}

	public TileEntity[] getTilesOnSides()
	{
		TileEntity[] result = new TileEntity[6];

		if (!Util.isNull(worldObj, pos))
		{
			for (int i = 0; i < 6; i++) result[i] = worldObj.getTileEntity(pos.offset(EnumFacing.getFront(i)));
		}
		return result;
	}
}

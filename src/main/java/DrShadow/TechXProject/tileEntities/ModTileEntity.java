package DrShadow.TechXProject.tileEntities;

import DrShadow.TechXProject.util.Helper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class ModTileEntity extends TileEntity implements ITickable
{
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		toNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		fromNBT(compound);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();

		writeToNBT(nbttagcompound);
		toNBT(nbttagcompound);

		return new S35PacketUpdateTileEntity(pos, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		super.onDataPacket(net, packet);
		fromNBT(packet.getNbtCompound());
	}

	public void toNBT(NBTTagCompound tag)
	{
		/* no-op */
	}

	public void fromNBT(NBTTagCompound tag)
	{
		/* no-op */
	}

	public TileEntity[] getTilesOnSides()
	{
		TileEntity[] result = new TileEntity[6];

		if (!Helper.isNull(worldObj, pos))
		{
			for (int i = 0; i < 6; i++) result[i] = worldObj.getTileEntity(pos.offset(EnumFacing.getFront(i)));
		}
		return result;
	}

	@Override
	public void update()
	{

	}
}

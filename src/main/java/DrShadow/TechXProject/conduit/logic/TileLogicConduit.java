package DrShadow.TechXProject.conduit.logic;

import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.conduit.logic.condition.Condition;
import DrShadow.TechXProject.conduit.logic.condition.EnumConditionType;
import DrShadow.TechXProject.tileEntities.ModTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileLogicConduit extends ModTileEntity
{
	public Condition condition;
	public boolean conditionChanged = false;
	private BlockPos tilePos;

	public TileLogicConduit()
	{
		setCondition(EnumConditionType.NO_CONDITION);
	}

	public void setCondition(EnumConditionType type)
	{
		condition = new Condition(type);
	}

	@Override
	public void update()
	{
		updateLogic();
	}

	private void updateLogic()
	{
		if (condition != null && tilePos != null)
		{
			TileEntity tileEntity = worldObj.getTileEntity(tilePos);

			if (tileEntity instanceof INetworkElement)
			{
				INetworkElement tile = (INetworkElement) tileEntity;

				tile.setActive(condition.validState(pos, worldObj));
			}
		}
	}

	public void onBlockBreak()
	{
		if (tilePos != null)
		{
			TileEntity tileEntity = worldObj.getTileEntity(tilePos);

			if (tileEntity instanceof INetworkElement)
			{
				INetworkElement tile = (INetworkElement) tileEntity;

				tile.setActive(true);
			}
		}
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		super.toNBT(tag);

		NBTTagCompound tile = new NBTTagCompound();

		if (tilePos != null)
		{
			tile.setInteger("x", tilePos.getX());
			tile.setInteger("y", tilePos.getY());
			tile.setInteger("z", tilePos.getZ());
		}
		tag.setTag("tilePos", tile);

		NBTTagCompound condition = new NBTTagCompound();

		condition.setString("condition", this.condition.getType().name());

		tag.setTag("conditionId", condition);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		super.fromNBT(tag);

		NBTTagCompound tile = tag.getCompoundTag("tilePos");

		tilePos = new BlockPos(tile.getInteger("x"), tile.getInteger("y"), tile.getInteger("z"));

		NBTTagCompound condition = tag.getCompoundTag("conditionId");

		if (conditionChanged)
		{
			conditionChanged = false;

			setCondition(EnumConditionType.valueOf(condition.getString("condition")));
		}
	}

	public void setTilePos(BlockPos pos)
	{
		tilePos = pos;
	}
}

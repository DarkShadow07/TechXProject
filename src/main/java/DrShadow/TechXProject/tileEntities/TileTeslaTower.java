package DrShadow.TechXProject.tileEntities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TileTeslaTower extends ModTileEntity
{
	private static final float damage = 2;

	public static List<EntityLivingBase> targets;

	private static List<UUID> owners = new ArrayList<UUID>();
	private static List<String> ownersNames = new ArrayList<String>();

	public TileTeslaTower()
	{

	}

	@Override
	public void update()
	{
		targets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() - 16, pos.getY() - 1, pos.getZ() - 16, pos.getX() + 16, pos.getY() + 3, pos.getZ() + 16));
		for (EntityLivingBase entity : targets)
		{
			if (entity != null)
			{
				attackEntity(entity);
			}
		}
	}

	public void attackEntity(EntityLivingBase entity)
	{
		DamageSource source = new DamageSource("teslaDeath");

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (!owners.contains(player.getUniqueID()))
			{
				player.attackEntityFrom(source, damage);
			}
		} else
		{
			entity.attackEntityFrom(source, damage);
		}
	}

	public void addOwner(EntityPlayer player)
	{
		if (!owners.contains(player.getUniqueID()))
		{
			owners.add(player.getUniqueID());
		}
	}

	public String getOwners()
	{
		ownersNames.clear();

		owners.forEach(id -> ownersNames.add(worldObj.getPlayerEntityByUUID(id).getDisplayNameString()));

		return ownersNames.toString();
	}

	@Override
	public void toNBT(NBTTagCompound tag)
	{
		NBTTagCompound ownersTag = new NBTTagCompound();

		for (int i = 0; i < owners.size(); i++)
		{
			ownersTag.setString("owner" + i, owners.get(i).toString());
		}

		tag.setTag("ownersTag", ownersTag);
	}

	@Override
	public void fromNBT(NBTTagCompound tag)
	{
		NBTTagCompound ownersTag = tag.getCompoundTag("ownersTag");

		for (int i = 0; i < owners.size(); i++)
		{
			owners.set(i, UUID.fromString(ownersTag.getString("owner" + i)));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(pos, pos.add(0, 3, 0));
	}
}

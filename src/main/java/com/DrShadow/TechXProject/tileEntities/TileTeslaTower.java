package com.DrShadow.TechXProject.tileEntities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TileTeslaTower extends TileEntity implements ITickable
{
	private static final float damage = 6;

	public static List<EntityLivingBase> targets;

	private static List<UUID> owners = new ArrayList<UUID>();

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

	public void addOwener(EntityPlayer player)
	{
		if (!owners.contains(player.getUniqueID()))
		{
			owners.add(player.getUniqueID());
		}
	}

	public List<UUID> getOwners()
	{
		return owners;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		for (int i = 0; i < owners.size(); i++)
		{
			owners.set(i, UUID.fromString(tag.getString("owner_" + i)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);

		for (int i = 0; i < owners.size(); i++)
		{
			tag.setString("owner_" + i, owners.get(i).toString());
		}
	}
}

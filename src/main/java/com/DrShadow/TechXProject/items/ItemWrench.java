package com.DrShadow.TechXProject.items;

import com.DrShadow.TechXProject.power.IPower;
import com.DrShadow.TechXProject.power.PowerTile;
import com.DrShadow.TechXProject.tileEntities.TilePowerTransmitter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class ItemWrench extends ItemBase
{
	private IPower target;

	private int[] pos;

	public ItemWrench()
	{

	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.pos = new int[3];

		this.pos[0] = pos.getX();
		this.pos[1] = pos.getY();
		this.pos[2] = pos.getZ();

		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof IPower)
		{
			if (player.isSneaking())
			{
				target = (IPower) tile;
			}else if (tile instanceof TilePowerTransmitter)
			{
				((TilePowerTransmitter) tile).setTarget(target);
			}
		}else if (player.isSneaking()) target = null;

		return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> list, boolean advanced)
	{
		if (target != null)
		{
			list.add(String.format("Selected Block: %s, %s, %s", pos[0], pos[1], pos[2]));
		}
	}
}

package com.DrShadow.TechXProject.items;

import com.DrShadow.TechXProject.power.IPower;
import com.DrShadow.TechXProject.power.PowerTile;
import com.DrShadow.TechXProject.tileEntities.TilePowerTransmitter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class ItemWrench extends ItemBase
{
	private PowerTile target;

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof IPower)
		{
			if (player.isSneaking())
			{
				target = (PowerTile) tile;
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
			list.add(String.format("Selected Block: %s, %s, %s", target.getPos().getX(), target.getPos().getY(), target.getPos().getZ()));
		}
	}
}

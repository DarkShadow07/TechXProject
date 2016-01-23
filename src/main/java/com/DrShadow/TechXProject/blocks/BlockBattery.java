package com.DrShadow.TechXProject.blocks;

import com.DrShadow.TechXProject.tileEntities.TileBattery;
import com.DrShadow.TechXProject.util.Helper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBattery extends BlockContainer
{
	int maxPower;

	public BlockBattery(int maxPower)
	{
		super(Material.iron);

		this.maxPower = maxPower;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileBattery battery = (TileBattery) worldIn.getTileEntity(pos);
		if (worldIn.isRemote)
		{
			battery.addPower(100);

			Helper.minecraft().thePlayer.sendChatMessage(battery.getPower() + "");
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileBattery(maxPower);
	}
}

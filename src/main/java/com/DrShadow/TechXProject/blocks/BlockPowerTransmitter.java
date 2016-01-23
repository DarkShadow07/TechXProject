package com.DrShadow.TechXProject.blocks;

import com.DrShadow.TechXProject.tileEntities.TileBattery;
import com.DrShadow.TechXProject.tileEntities.TilePowerTransmitter;
import com.DrShadow.TechXProject.util.LogHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPowerTransmitter extends BlockContainer
{
	private int maxPower, lost;

	public BlockPowerTransmitter(int maxPower, int lost)
	{
		super(Material.rock);

		this.maxPower = maxPower;
		this.lost = lost;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TilePowerTransmitter transmitter = (TilePowerTransmitter) worldIn.getTileEntity(pos);
		if (worldIn.isRemote)
		{
			LogHelper.info(transmitter.getPower());
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TilePowerTransmitter(maxPower, lost);
	}
}

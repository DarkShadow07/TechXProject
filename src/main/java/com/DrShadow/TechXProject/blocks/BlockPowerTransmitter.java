package com.DrShadow.TechXProject.blocks;

import com.DrShadow.TechXProject.tileEntities.TilePowerTransmitter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerTransmitter extends BlockContainer
{
	private int maxPower, transfer, lost;

	public BlockPowerTransmitter(int maxPower, int transfer, int lost)
	{
		super(Material.rock);

		this.maxPower = maxPower;
		this.transfer = transfer;
		this.lost = lost;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TilePowerTransmitter transmitter = new TilePowerTransmitter();

		transmitter.init(maxPower, transfer);
		transmitter.setLost(lost);

		return transmitter;
	}
}

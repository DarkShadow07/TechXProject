package com.DrShadow.TechXProject.blocks;

import com.DrShadow.TechXProject.tileEntities.TileBattery;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBattery extends BlockContainer
{
	int maxPower, transfer;

	public BlockBattery(int maxPower, int transfer)
	{
		super(Material.iron);

		this.maxPower = maxPower;
		this.transfer = transfer;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileBattery battery = new TileBattery();

		battery.init(maxPower, transfer);

		return battery;
	}
}

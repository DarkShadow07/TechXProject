package com.DrShadow.TechXProject.blocks;

import com.DrShadow.TechXProject.events.ChatMessageEvent;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTest extends BlockBase
{
	public BlockTest()
	{
		super(Material.rock);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ChatMessageEvent.sendMessage(ChatMessageEvent.ticksExisted + " ticks");

		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}
}

package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.blocks.base.BlockBase;
import DarkS.TechXProject.items.ItemPaintBrush;
import DarkS.TechXProject.machines.canvas.TileCanvas;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.Util;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCanvas extends BlockBase implements ITileEntityProvider
{
	public BlockCanvas()
	{
		super(Material.CLOTH, 4.9f, 0, null);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//TODO: fix Client-Server color

		if (heldItem != null && heldItem.getItem() instanceof ItemPaintBrush && ((ItemPaintBrush) heldItem.getItem()).getColor() != null)
		{
			((TileCanvas) worldIn.getTileEntity(pos)).addColor(((ItemPaintBrush) heldItem.getItem()).getColor(), 16 * hitX, 16 * hitY, 16 * hitZ);
			worldIn.getTileEntity(pos).markDirty();

			return true;
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileCanvas();
	}
}

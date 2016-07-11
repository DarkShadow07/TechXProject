package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.blocks.base.BlockBase;
import DarkS.TechXProject.items.ItemStructureCard;
import DarkS.TechXProject.machines.structureSaver.TileStructureSaver;
import DarkS.TechXProject.util.ChatUtil;
import DarkS.TechXProject.util.Logger;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockStructureSaver extends BlockBase implements ITileEntityProvider
{
	public BlockStructureSaver()
	{
		super(Material.IRON, 12.75f, 2, "pickaxe");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileStructureSaver tile = (TileStructureSaver) worldIn.getTileEntity(pos);

		if (tile != null && tile.end && heldItem != null && heldItem.getItem() instanceof ItemStructureCard)
		{
			if (heldItem.hasTagCompound())
			{
				tile.loadFromItem(heldItem);

				ChatUtil.sendNoSpam(playerIn, "Structure Loaded from Structure Card! " + tile.stored + " Blocks Stored");
			} else
			{
				tile.saveToItem(heldItem);

				ChatUtil.sendNoSpam(playerIn, "Structure Saved to Structure Card! " + tile.stored + " Blocks Stored");
			}

			return true;
		} else ChatUtil.sendNoSpam(playerIn, tile.stored + " Blocks Stored");

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileStructureSaver();
	}
}

package DrShadow.TechXProject.blocks.machine;

import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.machines.quarry.TileQuarry;
import DrShadow.TechXProject.util.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockQuarry extends BlockContainerBase
{
	// TODO: textures

	public BlockQuarry()
	{
		super(Material.iron, 4.7f, 3, "pickaxe");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileQuarry tile = (TileQuarry) worldIn.getTileEntity(pos);

		if (!worldIn.isRemote)
		{
			if (tile.end)
			{
				ChatUtil.sendNoSpamClient(ChatFormatting.GREEN + "Quarry Ended!" + " (" + tile.mined + " Blocks Mined)");
			} else if (tile.working)
			{
				ChatUtil.sendNoSpamClient("Mining at " + tile.x + " " + tile.y + " " + tile.z + " (" + tile.mined + " Blocks Mined)");
			} else
			{
				ChatUtil.sendNoSpamClient(ChatFormatting.RED + tile.error);
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileQuarry();
	}
}

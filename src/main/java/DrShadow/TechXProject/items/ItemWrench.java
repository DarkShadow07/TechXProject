package DrShadow.TechXProject.items;

import DrShadow.TechXProject.api.IWrench;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase implements IWrench
{
	private BlockPos tilePos = null;

	public ItemWrench()
	{

	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block block = worldIn.getBlockState(pos).getBlock();

		if (block != null && block.rotateBlock(worldIn, pos, facing)) return EnumActionResult.SUCCESS;

		if (!playerIn.isSneaking())
		{
			if (tilePos == null)
			{
				tilePos = pos;
			} else if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileLogicConduit)
			{
				TileLogicConduit logicConduit = (TileLogicConduit) worldIn.getTileEntity(pos);

				if (tilePos != null) logicConduit.setTilePos(tilePos);
			}
		}

		return EnumActionResult.SUCCESS;
	}
}

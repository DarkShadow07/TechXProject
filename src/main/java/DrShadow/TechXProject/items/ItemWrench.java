package DrShadow.TechXProject.items;

import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase
{
	private BlockPos tilePos = null;

	public ItemWrench()
	{

	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (playerIn.isSneaking())
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

		return false;
	}
}

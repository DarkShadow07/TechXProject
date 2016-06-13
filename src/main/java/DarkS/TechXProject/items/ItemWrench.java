package DarkS.TechXProject.items;

import DarkS.TechXProject.api.IWrench;
import DarkS.TechXProject.conduit.logic.TileLogicConduit;
import DarkS.TechXProject.entity.EntityDefender;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
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
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (!worldIn.isRemote)
		{
			EntityDefender defender = new EntityDefender(worldIn, playerIn);
			worldIn.spawnEntityInWorld(defender);
		}

		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block block = world.getBlockState(pos).getBlock();

		if (block.rotateBlock(world, pos, EnumFacing.UP))
		{
			return EnumActionResult.SUCCESS;
		}
		if (!player.isSneaking())
		{
			if (tilePos == null)
			{
				tilePos = pos;
			} else if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileLogicConduit)
			{
				TileLogicConduit logicConduit = (TileLogicConduit) world.getTileEntity(pos);

				if (tilePos != null) logicConduit.setTilePos(tilePos);
			}
		}

		return EnumActionResult.FAIL;
	}
}

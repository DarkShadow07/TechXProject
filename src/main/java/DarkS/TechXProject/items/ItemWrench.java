package DarkS.TechXProject.items;

import DarkS.TechXProject.api.IWrench;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase implements IWrench
{
	public ItemWrench()
	{
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block block = world.getBlockState(pos).getBlock();

		if (block.rotateBlock(world, pos, EnumFacing.UP))
		{
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}
}

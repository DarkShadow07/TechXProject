package DrShadow.TechXProject.items.dev;

import DrShadow.TechXProject.items.ItemBase;
import DrShadow.TechXProject.util.NBTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

public class ItemNull extends ItemBase
{
	public ItemNull()
	{

	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	private void saveWorld(ItemStack stack, BlockPos start)
	{
		stack = NBTUtil.checkNBT(stack);
	}

	public Pair<BlockPos, IBlockState> getWorld(ItemStack stack)
	{
		return null;
	}
}

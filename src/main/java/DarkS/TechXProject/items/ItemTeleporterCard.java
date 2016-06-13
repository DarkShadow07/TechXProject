package DarkS.TechXProject.items;

import DarkS.TechXProject.util.ChatUtil;
import DarkS.TechXProject.util.NBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ItemTeleporterCard extends ItemBase
{
	public ItemTeleporterCard()
	{
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!playerIn.isSneaking())
		{
			setPos(stack, pos, playerIn.dimension, stack.getDisplayName());
			ChatUtil.sendNoSpamClient("Location set! " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " (Dimension " + playerIn.dimension + ")");
		} else setPos(stack, BlockPos.ORIGIN, 0, "");

		return EnumActionResult.SUCCESS;
	}

	private void setPos(ItemStack stack, BlockPos pos, int dim, String name)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());

		tag.setInteger("dim", dim);

		tag.setString("name", name);
	}

	public Pair<String, Pair<BlockPos, Integer>> getPos(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		return new ImmutablePair<>(tag.getString("name"), new ImmutablePair<>(new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")), tag.getInteger("dim")));
	}
}

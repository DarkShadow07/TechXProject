package DarkS.TechXProject.items;

import DarkS.TechXProject.machines.quarry.TileQuarry;
import DarkS.TechXProject.machines.structureSaver.TileStructureSaver;
import DarkS.TechXProject.util.ChatUtil;
import DarkS.TechXProject.util.NBTUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemAreaCard extends ItemBase
{
	public ItemAreaCard()
	{

	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		NBTUtil.checkNBT(stack);

		BlockPos start = getStartPos(stack);
		BlockPos end = getEndPos(stack);

		tooltip.add("Area: from " + start.getX() + " " + start.getY() + " " + start.getZ() + " to " + end.getX() + " " + end.getY() + " " + end.getZ());
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		NBTUtil.checkNBT(stack);

		BlockPos start = getStartPos(stack);
		BlockPos end = getEndPos(stack);

		if (start.equals(BlockPos.ORIGIN))
		{
			setPosStart(stack, pos);
			ChatUtil.sendNoSpam(playerIn, "Start Position set to " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
		} else if (end.equals(BlockPos.ORIGIN))
		{
			int x = start.getX() - pos.getX();
			int y = start.getY() - pos.getY();
			int z = start.getZ() - pos.getZ();

			x = x * x;
			y = y * y;
			z = z * z;

			int distance = (int) Math.sqrt(x + y + z);

			if (distance <= 256)
			{
				setPosEnd(stack, pos);
				ChatUtil.sendNoSpam(playerIn, "End Position set to " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
			} else
				ChatUtil.sendNoSpam(playerIn, ChatFormatting.RED + "Max Distance is 256 Blocks! (" + distance + ")");
		} else if (worldIn.getTileEntity(pos) != null)
		{
			if (worldIn.getTileEntity(pos) instanceof TileQuarry)
			{
				((TileQuarry) worldIn.getTileEntity(pos)).start(start, end);

				setPosStart(stack, BlockPos.ORIGIN);
				setPosEnd(stack, BlockPos.ORIGIN);

				ChatUtil.sendNoSpam(playerIn, "Quarry Started! (" + start.getX() + " " + start.getY() + " " + start.getZ() + " to " + end.getX() + " " + end.getY() + " " + end.getZ() + ")");
			}else if (worldIn.getTileEntity(pos) instanceof TileStructureSaver)
			{
				((TileStructureSaver) worldIn.getTileEntity(pos)).start(start, end);

				setPosStart(stack, BlockPos.ORIGIN);
				setPosEnd(stack, BlockPos.ORIGIN);

				ChatUtil.sendNoSpam(playerIn, "Saving Structure! (" + start.getX() + " " + start.getY() + " " + start.getZ() + " to " + end.getX() + " " + end.getY() + " " + end.getZ() + ")");
			}
		}

		return EnumActionResult.SUCCESS;
	}

	private void setPosStart(ItemStack stack, BlockPos pos)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		tag.setInteger("xS", pos.getX());
		tag.setInteger("yS", pos.getY());
		tag.setInteger("zS", pos.getZ());
	}

	private void setPosEnd(ItemStack stack, BlockPos pos)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		tag.setInteger("xE", pos.getX());
		tag.setInteger("yE", pos.getY());
		tag.setInteger("zE", pos.getZ());
	}

	public BlockPos getStartPos(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		return new BlockPos(tag.getInteger("xS"), tag.getInteger("yS"), tag.getInteger("zS"));
	}

	public BlockPos getEndPos(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		return new BlockPos(tag.getInteger("xE"), tag.getInteger("yE"), tag.getInteger("zE"));
	}
}

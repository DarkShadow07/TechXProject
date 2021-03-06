package DarkS.TechXProject.items.itemWire;

import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.INetworkRelay;
import DarkS.TechXProject.items.ItemBase;
import DarkS.TechXProject.machines.node.network.NetworkUtil;
import DarkS.TechXProject.util.ChatUtil;
import DarkS.TechXProject.util.NBTUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemWire extends ItemBase
{
	public ItemWire()
	{

	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		stack = NBTUtil.checkNBT(stack);

		BlockPos pos = getPos(stack);
		if (pos != null)
			tooltip.add("Linking from: " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote) return EnumActionResult.FAIL;

		TileEntity tile = worldIn.getTileEntity(pos);

		if (!(tile instanceof INetworkElement || tile instanceof INetworkRelay))
		{
			BlockPos nbtPos = getPos(stack);

			if (!nbtPos.equals(BlockPos.ORIGIN))
			{
				setPos(stack, BlockPos.ORIGIN);

				ChatUtil.sendNoSpam(playerIn, ChatFormatting.RED + "That's not a Valid Network Element!");
				return EnumActionResult.FAIL;
			}

			return EnumActionResult.FAIL;
		}

		BlockPos nbtPos = getPos(stack);

		if (nbtPos.equals(BlockPos.ORIGIN))
		{
			setPos(stack, pos);
			ChatUtil.sendNoSpam(playerIn, ChatFormatting.GREEN + "Starting Connection");

			worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.BLOCKS, 0.5f, 0.7f);
		} else
		{
			int dx = pos.getX() - nbtPos.getX();
			int dy = pos.getY() - nbtPos.getY();
			int dz = pos.getZ() - nbtPos.getZ();

			dx = dx * dx;
			dy = dy * dy;
			dz = dz * dz;

			double distance = Math.sqrt(dx + dy + dz);

			if (distance > 24)
			{
				ChatUtil.sendNoSpam(playerIn, ChatFormatting.RED + "This Wire is too Short, Max Distance is 24 Blocks");

				return EnumActionResult.FAIL;
			}

			if (!playerIn.capabilities.isCreativeMode)
				stack.stackSize -= 1;

			ChatUtil.sendNoSpam(playerIn, ChatFormatting.GREEN + "Connection Done!");
			TileEntity nbtTile = worldIn.getTileEntity(nbtPos);

			NetworkUtil.link(nbtTile, tile);

			setPos(stack, BlockPos.ORIGIN);

			worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.BLOCKS, 0.5f, 0.7f);
		}

		return EnumActionResult.SUCCESS;
	}

	private void setPos(ItemStack stack, BlockPos pos)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());
	}

	public BlockPos getPos(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		int x = stack.getTagCompound().getInteger("x");
		int y = stack.getTagCompound().getInteger("y");
		int z = stack.getTagCompound().getInteger("z");

		return new BlockPos(x, y, z);
	}
}

package DrShadow.TechXProject.items;

import DrShadow.TechXProject.api.IWrench;
import DrShadow.TechXProject.api.IWrenchable;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.util.NBTUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemWrench extends ItemBase implements IWrench
{
	private BlockPos tilePos = null;

	public ItemWrench()
	{
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		// TODO: fix myself

		//if (!tryToBreak(player, world, pos, hand))
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
		}

		return EnumActionResult.FAIL;
	}

	private boolean tryToBreak(EntityPlayer player, World world, BlockPos pos, EnumHand hand)
	{
		if (player != null && player.isSneaking() && world.getBlockState(pos).getBlock() != null && world.getBlockState(pos).getBlock() instanceof IWrenchable)
		{
			Random r = new Random();

			ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock());

			stack = NBTUtil.checkNBT(stack);

			TileEntity tile = world.getTileEntity(pos);

			if (tile != null)
			{
				tile.writeToNBT(stack.getTagCompound());
			}

			float xO = r.nextFloat() * 0.8F + 0.1F;
			float yO = r.nextFloat() * 0.8F + 0.1F;
			float zO = r.nextFloat() * 0.8F + 0.1F;

			EntityItem item = new EntityItem(world, pos.getX() + xO, pos.getY() + yO, pos.getZ() + zO, stack);

			if (stack.hasTagCompound())
			{
				item.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());

				item.getEntityItem().setStackDisplayName(stack.getDisplayName() + " (Configured)");
			}

			float f = 0.05F;
			item.motionX = r.nextGaussian() * (double) f;
			item.motionY = r.nextGaussian() * (double) f + 0.20000000298023224D;
			item.motionZ = r.nextGaussian() * (double) f;

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(item);

				world.setBlockToAir(pos);
			}

			return true;
		}

		return false;
	}
}

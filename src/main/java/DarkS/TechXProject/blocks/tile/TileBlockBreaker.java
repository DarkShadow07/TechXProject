package DarkS.TechXProject.blocks.tile;

import DarkS.TechXProject.machines.node.item.NodeUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileBlockBreaker extends TileBase implements ITickable
{
	private boolean isActive = false;
	private EnumFacing rotation;
	private IInventory inventory = null;

	private float breakProgress = 0;

	public TileBlockBreaker()
	{
		rotation = EnumFacing.DOWN;
	}

	@Override
	public void update()
	{
		isActive = worldObj.isBlockPowered(pos);

		checkForInv();

		tryToBreakBlock(pos.offset(rotation));
	}

	public void checkForInv()
	{
		if (worldObj.getTileEntity(pos.offset(rotation.getOpposite())) != null)
		{
			if (worldObj.getTileEntity(pos.offset(rotation.getOpposite())) instanceof IInventory)
			{
				inventory = (IInventory) worldObj.getTileEntity(pos.offset(rotation.getOpposite()));
			} else inventory = null;
		} else inventory = null;
	}

	public void tryToBreakBlock(BlockPos pos)
	{
		if (isActive)
		{
			if (worldObj.isBlockFullCube(pos))
			{
				worldObj.sendBlockBreakProgress(0, pos, (int) breakProgress);

				breakProgress += 0.75f;
			}

			if (breakProgress >= 9)
			{
				breakProgress = 0;

				if (inventory != null)
				{
					for (ItemStack stack : worldObj.getBlockState(pos).getBlock().getDrops(worldObj, pos, worldObj.getBlockState(pos), 0))
					{
						if (NodeUtil.transferStack(stack, inventory, rotation.getOpposite()) != null)
						{
							worldObj.destroyBlock(pos, false);
						}
					}
				} else
				{
					worldObj.destroyBlock(pos, true);
				}

				worldObj.sendBlockBreakProgress(0, pos, -1);
			}
		}
	}
}

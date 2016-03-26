package DrShadow.TechXProject.tileEntities;

import DrShadow.TechXProject.blocks.multiHighlight.IMultiHighlightProvider;
import DrShadow.TechXProject.conduit.item.ItemConduitUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class TileBlockBreaker extends ModTileEntity implements IMultiHighlightProvider
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
		super.update();

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
						if (ItemConduitUtil.transferStack(stack, inventory, rotation.getOpposite()) != null)
						{
							worldObj.destroyBlock(pos, false);
						}
					}
				} else
				{
					worldObj.destroyBlock(pos, true);
				}

				worldObj.sendBlockBreakProgress(0, pos, 0);
			}
		}
	}

	@Override
	public List<AxisAlignedBB> getBoxes()
	{
		List<AxisAlignedBB> boxes = new ArrayList<>();

		boxes.add(new AxisAlignedBB(0.2f, 0.2f, 0.2f, 0.8f, 0.8f, 0.8f));
		boxes.add(new AxisAlignedBB(0f, 0, 0f, 0.8f, 0.2f, 0.8f));

		return boxes;
	}

	@Override
	public List<AxisAlignedBB> getActiveBoxes()
	{
		return null;
	}
}

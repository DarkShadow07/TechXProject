package DrShadow.TechXProject.items.energy;

import DrShadow.TechXProject.util.VectorUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.text.NumberFormat;
import java.util.List;

public class ItemMagnet extends ItemEnergyContainer
{
	public static final int range = 16;
	private int drain = 1;

	public ItemMagnet()
	{
		super(200000, 200000, 1000);

		setMaxStackSize(1);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (getEnergy(stack) >= drain && entityIn instanceof EntityPlayer && !entityIn.isSneaking() && !GuiScreen.isShiftKeyDown())
		{
			List<EntityItem> items = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(entityIn.posX - range, entityIn.posY - range, entityIn.posZ - range, entityIn.posX + range, entityIn.posY + range, entityIn.posZ + range));

			for (EntityItem item : items)
			{
				if (item != null)
				{
					Vec3d vec = VectorUtil.multiply(entityIn.getPositionVector().addVector(0, 0.25f, 0).subtract(item.getPositionVector()), 0.25f);

					item.motionX = vec.xCoord;
					item.motionY = vec.yCoord;
					item.motionZ = vec.zCoord;

					subtractEnergy(stack, drain, false);
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("Energy: " + NumberFormat.getInstance().format(getEnergy(stack)) + "/" + NumberFormat.getInstance().format(getMaxEnergy()));
	}
}

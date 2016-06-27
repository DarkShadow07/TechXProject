package DarkS.TechXProject.entity.item;

import DarkS.TechXProject.api.energy.item.IEnergyItem;
import DarkS.TechXProject.items.energy.ItemMagnet;
import DarkS.TechXProject.util.VectorUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityItemMagnet extends EntityItem
{
	public static final int range = 16;
	private int drain = 1;

	public EntityItemMagnet(World worldIn, double x, double y, double z, ItemStack stack)
	{
		super(worldIn, x, y, z, stack);

		setEntityInvulnerable(true);
		isImmuneToFire = true;

		lifespan = 72000;
		setPickupDelay(20);
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		ItemStack stack = getEntityItem();

		IEnergyItem energyItem = (IEnergyItem) stack.getItem();

		if (energyItem.getEnergy(stack) >= drain)
		{
			List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));

			for (EntityItem item : items)
			{
				if (item != null && !(item.getEntityItem().getItem() instanceof ItemMagnet))
				{
					Vec3d vec = VectorUtil.multiply(getPositionVector().subtract(item.getPositionVector()), 0.15f);

					item.motionX = vec.xCoord;
					item.motionY = vec.yCoord;
					item.motionZ = vec.zCoord;

					energyItem.subtractEnergy(stack, drain, false);
				}
			}
		}
	}
}

package DarkS.TechXProject.entity;

import DarkS.TechXProject.util.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityDefender extends EntityThrowable
{
	private int rad = 64;

	private EntityLivingBase target;

	public EntityDefender(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);

		setEntityInvulnerable(true);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		worldObj.playEvent(null, 2000, getPosition(), 0);
		worldObj.playSound(Util.player(), getPosition(), SoundEvents.ENTITY_ELDER_GUARDIAN_HURT, SoundCategory.PLAYERS, 0.35f, 0.5f);
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		List<EntityLivingBase> targets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - rad, posY - rad, posZ - rad, posX + rad, posY + rad, posZ + rad));

		for (EntityLivingBase target : targets)
			if ((this.target == null || this.target.isDead) && !(target instanceof EntityPlayer) && !target.isDead)
				this.target = target;

		if (target == null) setDead();

		if (target != null)
		{
			double distance = getDistanceToEntity(target);

			double dx = target.posX - posX;
			double dy = target.posY - posY;
			double dz = target.posZ - posZ;

			dx /= distance;
			dy /= distance;
			dz /= distance;

			motionX = dx;
			motionY = dy;
			motionZ = dz;
		}
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (result != null && result.typeOfHit.equals(RayTraceResult.Type.ENTITY))
			if (result.entityHit.equals(target))
			{
				worldObj.playEvent(null, 1999, getPosition(), 0);

				target.attackEntityFrom(new DamageSource("defender"), target.getHealth() + target.getTotalArmorValue());
				target.setDead();

				setDead();
			}
	}
}

package DarkS.TechXProject.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.server.FMLServerHandler;

public class Teleporter
{
	public static void teleport(Entity entity, BlockPos pos, int dim)
	{
		if (entity.dimension == dim)
		{
			teleportSameDim(entity, pos);
		} else teleportToDim(entity, pos, dim);
	}

	private static void teleportSameDim(Entity entity, BlockPos pos)
	{
		if (entity != null && entity.timeUntilPortal <= 0)
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayerMP player = (EntityPlayerMP) entity;

				player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				player.worldObj.updateEntityWithOptionalForce(player, false);
				player.connection.sendPacket(new SPacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
				player.timeUntilPortal = 20;
			} else
			{
				WorldServer worldServer = (WorldServer) entity.worldObj;

				entity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				entity.timeUntilPortal = 20;
				worldServer.resetUpdateEntityTick();
			}
		}
	}

	private static void teleportToDim(Entity entity, BlockPos pos, int dim)
	{
		World oldWorld = entity.getEntityWorld();

		if (entity.timeUntilPortal <= 0)
		{
			MinecraftServer server = FMLServerHandler.instance().getServer();
			WorldServer oldWorldServer = server.worldServerForDimension(entity.dimension);
			WorldServer newWorldServer = server.worldServerForDimension(dim);

			if (entity instanceof EntityPlayer)
			{
				EntityPlayerMP player = (EntityPlayerMP) entity;

				if (!player.worldObj.isRemote)
				{
					server.getPlayerList().transferPlayerToDimension(player, dim, new TechTeleporter(newWorldServer));
					player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					player.worldObj.updateEntityWithOptionalForce(player, false);
					player.connection.sendPacket(new SPacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
					player.timeUntilPortal = 20;
				}
			} else
			{
				NBTTagCompound tag = new NBTTagCompound();
				entity.writeToNBTOptional(tag);
				entity.setDead();

				Entity teleported = EntityList.createEntityFromNBT(tag, newWorldServer);

				if (teleported != null)
				{
					teleported.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, entity.rotationYaw, entity.rotationPitch);
					teleported.forceSpawn = true;
					newWorldServer.spawnEntityInWorld(teleported);
					teleported.setWorld(newWorldServer);
					teleported.timeUntilPortal = 20;
				}

				oldWorldServer.resetUpdateEntityTick();
				newWorldServer.resetUpdateEntityTick();
			}
		}
	}

	private static class TechTeleporter extends net.minecraft.world.Teleporter
	{
		public TechTeleporter(WorldServer worldServer)
		{
			super(worldServer);
		}

		@Override
		public boolean makePortal(Entity entity)
		{
			return false;
		}

		@Override
		public void removeStalePortalLocations(long worldTime)
		{

		}

		@Override
		public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
		{
			return false;
		}

		@Override
		public void placeInPortal(Entity entity, float rotationYaw)
		{
			entity.setLocationAndAngles(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY) + 2, MathHelper.floor_double(entity.posZ), entity.rotationYaw, entity.rotationPitch);
		}
	}
}
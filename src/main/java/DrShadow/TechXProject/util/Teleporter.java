package DrShadow.TechXProject.util;

import DrShadow.TechXProject.init.InitSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Teleporter
{
	@SuppressWarnings("unchecked")
	private static void teleportEntity(Entity entity, TeleportLocation destination)
	{
		if (entity != null)
		{
			if (entity.timeUntilPortal <= 0)
			{
				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
				WorldServer oldWorldServer = server.worldServerForDimension(entity.dimension);
				WorldServer newWorldServer = server.worldServerForDimension(destination.getDimension());

				if (entity instanceof EntityPlayer)
				{
					EntityPlayerMP player = (EntityPlayerMP) entity;

					if (!player.worldObj.isRemote)
					{
						player.changeDimension(destination.getDimension()); //TODO: UNTESTED
						player.setPositionAndUpdate(destination.xCoord + 0.5, destination.yCoord + 0.5, destination.zCoord + 0.5);
						player.worldObj.updateEntityWithOptionalForce(player, false);
						player.playerNetServerHandler.sendPacket(new SPacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
					}

				} else if (!entity.worldObj.isRemote)
				{
					NBTTagCompound tag = new NBTTagCompound();

					entity.writeToNBTOptional(tag);
					entity.setDead();

					Entity teleportedEntity = EntityList.createEntityFromNBT(tag, newWorldServer);
					if (teleportedEntity != null)
					{
						teleportedEntity.setLocationAndAngles(destination.xCoord + 0.5, destination.yCoord + 0.5, destination.zCoord + 0.5, entity.rotationYaw, entity.rotationPitch);
						teleportedEntity.forceSpawn = true;
						newWorldServer.spawnEntityInWorld(teleportedEntity);
						teleportedEntity.setWorld(newWorldServer);
						teleportedEntity.timeUntilPortal = teleportedEntity instanceof EntityPlayer ? 150 : 20;
					}

					oldWorldServer.resetUpdateEntityTick();
					newWorldServer.resetUpdateEntityTick();
				}
				entity.timeUntilPortal = entity instanceof EntityLiving ? 150 : 20;
			}
		}
	}

	public static class TeleportLocation
	{
		protected double xCoord;
		protected double yCoord;
		protected double zCoord;
		protected int dimension;
		protected float pitch;
		protected float yaw;
		protected String name;
		protected String dimensionName = "";
		protected boolean writeProtected = false;

		public TeleportLocation()
		{

		}

		public TeleportLocation(double x, double y, double z, int dimension)
		{
			this.xCoord = x;
			this.yCoord = y;
			this.zCoord = z;
			this.dimension = dimension;
			this.pitch = 0;
			this.yaw = 0;
		}

		public TeleportLocation(double x, double y, double z, int dimension, float pitch, float yaw)
		{
			this.xCoord = x;
			this.yCoord = y;
			this.zCoord = z;
			this.dimension = dimension;
			this.pitch = pitch;
			this.yaw = yaw;
		}

		public TeleportLocation(double x, double y, double z, int dimension, float pitch, float yaw, String name)
		{
			this.xCoord = x;
			this.yCoord = y;
			this.zCoord = z;
			this.dimension = dimension;
			this.pitch = pitch;
			this.yaw = yaw;
			this.name = name;
		}

		public double getXCoord()
		{
			return xCoord;
		}

		public void setXCoord(double x)
		{
			xCoord = x;
		}

		public double getYCoord()
		{
			return yCoord;
		}

		public void setYCoord(double y)
		{
			yCoord = y;
		}

		public double getZCoord()
		{
			return zCoord;
		}

		public void setZCoord(double z)
		{
			zCoord = z;
		}

		public int getDimension()
		{
			return dimension;
		}

		public void setDimension(int d)
		{
			dimension = d;
		}

		public String getDimensionName()
		{
			return dimensionName;
		}

		public void setDimensionName(String dimensionName)
		{
			this.dimensionName = dimensionName;
		}

		public float getPitch()
		{
			return pitch;
		}

		public void setPitch(float p)
		{
			pitch = p;
		}

		public float getYaw()
		{
			return yaw;
		}

		public void setYaw(float y)
		{
			yaw = y;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String s)
		{
			name = s;
		}

		public boolean getWriteProtected()
		{
			return writeProtected;
		}

		public void setWriteProtected(boolean b)
		{
			writeProtected = b;
		}

		public void writeToNBT(NBTTagCompound compound)
		{
			compound.setDouble("X", xCoord);
			compound.setDouble("Y", yCoord);
			compound.setDouble("Z", zCoord);
			compound.setInteger("Dimension", dimension);
			compound.setFloat("Pitch", pitch);
			compound.setFloat("Yaw", yaw);
			compound.setString("Name", name);
			compound.setString("DimentionName", dimensionName);
			compound.setBoolean("WP", writeProtected);
		}

		public void readFromNBT(NBTTagCompound compound)
		{
			xCoord = compound.getDouble("X");
			yCoord = compound.getDouble("Y");
			zCoord = compound.getDouble("Z");
			dimension = compound.getInteger("Dimension");
			pitch = compound.getFloat("Pitch");
			yaw = compound.getFloat("Yaw");
			name = compound.getString("Name");
			dimensionName = compound.getString("DimentionName");
			writeProtected = compound.getBoolean("WP");
		}

		public void sendEntityToCoords(Entity entity)
		{
			entity.worldObj.playSound(null, entity.getPosition(), InitSounds.teleport, SoundCategory.PLAYERS, 0.1F, entity.worldObj.rand.nextFloat() * 0.1F + 0.9F);

			teleportEntity(entity, this);

			entity.worldObj.playSound(null, entity.getPosition(), InitSounds.teleport, SoundCategory.PLAYERS, 0.1F, entity.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
	}
}
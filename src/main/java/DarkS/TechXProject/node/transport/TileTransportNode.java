package DarkS.TechXProject.node.transport;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.api.network.NodeNetwork;
import DarkS.TechXProject.blocks.tile.TileBase;
import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.highlight.IHighlightProvider;
import DarkS.TechXProject.highlight.SelectionBox;
import DarkS.TechXProject.reference.Guis;
import DarkS.TechXProject.util.ChatUtil;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.VectorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

public class TileTransportNode extends TileBase implements INetworkElement<TileBase>, IHighlightProvider, ITickable
{
	private NodeNetwork network;

	private BlockPos movePos;

	private String name;

	private Color color;

	public TileTransportNode()
	{
		setName("New Node");

		Random r = new Random();

		setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
	}

	@Override
	public void update()
	{

	}

	@Override
	public double getMaxRenderDistanceSquared()
	{
		return super.getMaxRenderDistanceSquared() * 8;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		movePos = new BlockPos(tag.getInteger("moveX"), tag.getInteger("moveY"), tag.getInteger("moveZ"));

		name = tag.getString("name");

		color = new Color(tag.getInteger("colorR"), tag.getInteger("colorG"), tag.getInteger("colorB"));

		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		tag.setString("name", name);

		tag.setInteger("colorR", color.getRed());
		tag.setInteger("colorG", color.getGreen());
		tag.setInteger("colorB", color.getBlue());

		if (movePos != null)
		{
			tag.setInteger("moveX", movePos.getX());
			tag.setInteger("moveY", movePos.getY());
			tag.setInteger("moveZ", movePos.getZ());
		}

		super.writeToNBT(tag);

		return tag;
	}

	public void setMovePos(BlockPos movePos)
	{
		this.movePos = movePos;
	}

	@Override
	public NodeNetwork getNetwork()
	{
		return network;
	}

	@Override
	public void setNetwork(NodeNetwork network)
	{
		this.network = network;
	}

	@Override
	public TileBase getTile()
	{
		return this;
	}

	@Override
	public TileEntity getAttachedTile()
	{
		return null;
	}

	@Override
	public boolean isAttached()
	{
		return false;
	}

	@Override
	public INetworkContainer getController()
	{
		return network.getController();
	}

	@Override
	public boolean isInput()
	{
		return true;
	}

	@Override
	public boolean isOutput()
	{
		return true;
	}

	@Override
	public boolean isActive()
	{
		return hasNetwork() && network.getController().isActive();
	}
	@Override
	public boolean hasNetwork()
	{
		return network != null;
	}

	@Override
	public SelectionBox[] getBoxes()
	{
		return new SelectionBox[]{new SelectionBox(0, new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375), new AxisAlignedBB(0.1875, 0.125, 0.1875, 0.8125, 0.1875, 0.8125)).rotate(EnumFacing.getFront(getBlockMetadata()).getOpposite())};
	}

	@Override
	public SelectionBox[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end)
	{
		return getBoxes();
	}

	@Override
	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end)
	{
		return SelectionBox.rayTraceAll(getBoxes(), pos, start, end);
	}

	@Override
	public boolean onBoxClicked(SelectionBox box, EntityPlayer clicker)
	{
		if (!clicker.isSneaking() && isActive())
		{
			GuiHandler.openGui(clicker, TechXProject.instance, Guis.TRANSPORT, pos);

			return true;
		}

		return false;
	}

	public void moveEntity(Entity entity)
	{
		if (entity.isRiding() || movePos == null) return;

		if (entity instanceof EntityPlayer) ((EntityPlayer) entity).closeScreen();

		Mover mover = new Mover(worldObj, entity.getPosition(), movePos);

		if (entity instanceof EntityPlayer)
			ChatUtil.sendNoSpam((EntityPlayer) entity, String.format("Moving to: x:%s y:%s z:%s", movePos.getX(), movePos.getY(), movePos.getZ()));

		worldObj.spawnEntityInWorld(mover);

		entity.startRiding(mover, false);
	}

	private static class Mover extends Entity
	{
		private static final DataParameter<BlockPos> START_POS = EntityDataManager.createKey(Mover.class, DataSerializers.BLOCK_POS);
		private static final DataParameter<BlockPos> END_POS = EntityDataManager.createKey(Mover.class, DataSerializers.BLOCK_POS);

		public Mover(World world)
		{
			super(world);
		}

		public Mover(World world, BlockPos start, BlockPos end)
		{
			super(world);

			setPosition(start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5);

			setStart(start);

			setEnd(end);
		}

		@Override
		protected void entityInit()
		{
			setSize(0.0f, 0.0f);
			noClip = true;

			dataManager.register(END_POS, BlockPos.ORIGIN);

			dataManager.register(START_POS, getPosition().add(0.5, 0.5, 0.5));
		}

		@Override
		public void onUpdate()
		{
			super.onUpdate();

			if (getPassengers().isEmpty())
			{
				setDead();
				return;
			}

			worldObj.spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, 1, 1, -1, 0);

			BlockPos startPos = dataManager.get(START_POS);
			BlockPos endPos = dataManager.get(END_POS);

			if (getPosition().getX() == endPos.getX() && getPosition().getZ() == endPos.getZ())
				if (getPosition().getY() > endPos.getY())
					posY -= Math.min(getPosition().getY() - endPos.getY(), 5);
				else
				{
					getPassengers().get(0).setEntityInvulnerable(false);

					posY += 1.5f;

					setDead();
				}
			else if (getPosition().getX() == startPos.getX() && getPosition().getY() <= 250 && getPosition().getZ() == startPos.getZ())
				posY += 5;
			else
			{
				getPassengers().get(0).setEntityInvulnerable(true);

				Vec3d motion = VectorUtil.multiply(getPositionVector(), -1).addVector(endPos.getX() + 0.5, endPos.getY() + 0.5, endPos.getZ() + 0.5).normalize();

				float speed = 10.6f;

				double motionX = motion.xCoord * speed;
				double motionZ = motion.zCoord * speed;

				posX += motionX;
				posZ += motionZ;
			}
		}

		@Override
		protected void readEntityFromNBT(NBTTagCompound tag)
		{
			setEnd(new BlockPos(tag.getInteger("endX"), tag.getInteger("endY"), tag.getInteger("endZ")));

			setStart(new BlockPos(tag.getInteger("startX"), tag.getInteger("startY"), tag.getInteger("startZ")));
		}

		@Override
		protected void writeEntityToNBT(NBTTagCompound tag)
		{
			BlockPos end = dataManager.get(END_POS);

			tag.setInteger("endX", end.getX());
			tag.setInteger("endY", end.getY());
			tag.setInteger("endZ", end.getZ());

			BlockPos start = dataManager.get(START_POS);

			tag.setInteger("startX", start.getX());
			tag.setInteger("startY", start.getY());
			tag.setInteger("startZ", start.getZ());
		}

		public void setEnd(BlockPos pos)
		{
			dataManager.set(END_POS, pos);
		}

		public void setStart(BlockPos pos)
		{
			dataManager.set(START_POS, pos);
		}
	}
}

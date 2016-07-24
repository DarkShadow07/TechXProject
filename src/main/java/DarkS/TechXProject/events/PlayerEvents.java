package DarkS.TechXProject.events;

import DarkS.TechXProject.api.energy.IEnergyContainer;
import DarkS.TechXProject.api.energy.item.IEnergyItem;
import DarkS.TechXProject.blocks.tile.highlight.IHighlightProvider;
import DarkS.TechXProject.blocks.tile.highlight.SelectionBox;
import DarkS.TechXProject.capability.CustomCapabilities;
import DarkS.TechXProject.capability.PacketEnergyCapability;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.util.PartialTicksUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class PlayerEvents
{
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		EntityPlayer player = event.player;

		if (player.hasCapability(CustomCapabilities.ENERGY_CAPABILITY, null))
		{
			CustomCapabilities.IEnergyData data = player.getCapability(CustomCapabilities.ENERGY_CAPABILITY, null);

			int serverEnergy = 0, clientEnergy = 0;

			if (player.worldObj.isRemote)
				clientEnergy = player.getCapability(CustomCapabilities.ENERGY_CAPABILITY, null).getEnergy();
			else serverEnergy = player.getCapability(CustomCapabilities.ENERGY_CAPABILITY, null).getEnergy();

			if (clientEnergy != serverEnergy && player instanceof EntityPlayerMP)
				PacketHandler.sendTo(new PacketEnergyCapability(((CustomCapabilities.EnergyData) data).serializeNBT()), (EntityPlayerMP) player);

			if (data.getEnergy() == data.getMaxEnergy())
			{
				int chance = new Random().nextInt(1000000000);

				if (chance == 0)
					player.attackEntityFrom(new DamageSource("energyShock"), 10);
			}

			for (ItemStack stack : player.inventory.mainInventory)
			{
				if (stack != null && stack.getItem() instanceof IEnergyItem)
				{
					int max = Math.min(100, ((IEnergyItem) stack.getItem()).addEnergy(stack, 100, true));

					if (data.getEnergy() < max) return;

					((IEnergyItem) stack.getItem()).addEnergy(stack, max, false);
					data.setEnergy(player, data.getEnergy() - max);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDamage(LivingHurtEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();

			if (player.hasCapability(CustomCapabilities.ENERGY_CAPABILITY, null))
			{
				CustomCapabilities.IEnergyData data = player.getCapability(CustomCapabilities.ENERGY_CAPABILITY, null);

				int energyLost = (int) (event.getAmount() * 200 + new Random().nextInt(300));

				data.setEnergy(player, data.getEnergy() - energyLost);

				if (event.getSource().getEntity() != null)
					event.getSource().getEntity().attackEntityFrom(new DamageSource("energyScape"), 2.5f);
			}
		}
	}

	@SubscribeEvent
	public void onBlockRightClickEvent(PlayerInteractEvent.RightClickBlock event)
	{
		World world = event.getWorld();
		BlockPos pos = event.getPos();

		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof IHighlightProvider)
		{
			IHighlightProvider provider = (IHighlightProvider) world.getTileEntity(pos);

			double blockReachDistance = event.getEntityPlayer() instanceof EntityPlayerMP ? ((EntityPlayerMP) event.getEntityPlayer()).interactionManager.getBlockReachDistance() : 4.5d;

			Vec3d start = event.getEntityPlayer().getPositionEyes(PartialTicksUtil.partialTicks);
			Vec3d look = event.getEntityPlayer().getLook(PartialTicksUtil.partialTicks);
			Vec3d end = start.addVector(look.xCoord * blockReachDistance, look.yCoord * blockReachDistance, look.zCoord * blockReachDistance);

			for (SelectionBox box : provider.getSelectedBoxes(pos, start, end))
				if (box != null)
					if (provider.onBoxClicked(box, event.getEntityPlayer()))
						event.setCanceled(true);
		}

		if (event.getEntityPlayer() != null && event.getItemStack() == null && event.getEntityPlayer().isSneaking() && event.getEntityPlayer().hasCapability(CustomCapabilities.ENERGY_CAPABILITY, null) && world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof IEnergyContainer)
		{
			CustomCapabilities.IEnergyData data = event.getEntityPlayer().getCapability(CustomCapabilities.ENERGY_CAPABILITY, null);

			IEnergyContainer energyContainer = (IEnergyContainer) world.getTileEntity(pos);

			int max = Math.min(Math.min(data.getEnergy(), 200), energyContainer.getMaxTransfer());

			data.setEnergy(event.getEntityPlayer(), data.getEnergy() - max);
			energyContainer.addEnergy(max, false);

			event.setCanceled(true);
			event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
		}
	}
}

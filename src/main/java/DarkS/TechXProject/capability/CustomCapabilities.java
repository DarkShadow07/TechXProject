package DarkS.TechXProject.capability;


import DarkS.TechXProject.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class CustomCapabilities
{
	@CapabilityInject(IEnergyData.class)
	public static Capability<IEnergyData> ENERGY_CAPABILITY = null;

	public CustomCapabilities()
	{
		CapabilityManager.INSTANCE.register(IEnergyData.class, new EnergyStorage(), EnergyData.class);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onCapabilityAttach(AttachCapabilitiesEvent.Entity event)
	{
		if (!(event.getEntity() instanceof EntityPlayer)) return;

		if (!event.getEntity().hasCapability(ENERGY_CAPABILITY, null))
		{
			EnergyData data = new EnergyData();

			event.addCapability(new ResourceLocation(Reference.MOD_ID, "energyCapability"), data);
		}
	}

	public interface IEnergyData
	{
		int getEnergy();

		void setEnergy(EntityPlayer player, int energy);

		int getMaxEnergy();

		void setMaxEnergy(EntityPlayer player, int energy);
	}

	public class EnergyStorage implements Capability.IStorage<IEnergyData>
	{
		@Override
		public NBTBase writeNBT(Capability<IEnergyData> capability, IEnergyData instance, EnumFacing side)
		{
			return ((EnergyData) instance).serializeNBT();
		}

		@Override
		public void readNBT(Capability<IEnergyData> capability, IEnergyData instance, EnumFacing side, NBTBase nbt)
		{
			((EnergyData) instance).deserializeNBT((NBTTagCompound) nbt);
		}
	}

	public class EnergyData implements ICapabilitySerializable<NBTTagCompound>, IEnergyData
	{
		public int energy = 0, maxEnergy = 25000;

		public EnergyData()
		{

		}

		@Override
		public int getEnergy()
		{
			return energy;
		}

		@Override
		public void setEnergy(EntityPlayer player, int energy)
		{
			this.energy = MathHelper.clamp_int(energy, 0, getMaxEnergy());
		}

		@Override
		public int getMaxEnergy()
		{
			return maxEnergy;
		}

		@Override
		public void setMaxEnergy(EntityPlayer player, int energy)
		{
			maxEnergy = energy;
		}

		@Override
		public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
		{
			return capability == ENERGY_CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
		{
			if (capability == ENERGY_CAPABILITY)
				return (T) this;

			return null;
		}

		@Override
		public NBTTagCompound serializeNBT()
		{
			NBTTagCompound tag = new NBTTagCompound();

			tag.setInteger("energy", getEnergy());
			tag.setInteger("maxEnergy", getMaxEnergy());

			return tag;
		}

		@Override
		public void deserializeNBT(NBTTagCompound tag)
		{
			setEnergy(null, tag.getInteger("energy"));
			setMaxEnergy(null, tag.getInteger("maxEnergy"));
		}
	}
}

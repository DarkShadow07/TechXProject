package DarkS.TechXProject.enchantment;

import DarkS.TechXProject.capability.CustomCapabilities;
import DarkS.TechXProject.reference.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class EnchantmentEnergyRepair extends Enchantment
{
	public EnchantmentEnergyRepair()
	{
		super(Rarity.VERY_RARE, EnumEnchantmentType.BREAKABLE, EntityEquipmentSlot.values());

		setName("energyRepair");
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "energyRepair"));

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		EntityPlayer player = event.player;

		if (!player.hasCapability(CustomCapabilities.ENERGY_CAPABILITY, null)) return;

		CustomCapabilities.IEnergyData data = player.getCapability(CustomCapabilities.ENERGY_CAPABILITY, null);

		for (ItemStack stack : player.inventoryContainer.getInventory())
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, stack);

			if (stack != null && level > 0 && data.getEnergy() > 300 / level && new Random().nextInt(60) / level == 0)
			{
				if (stack.getItemDamage() <= 0) return;

				stack.setItemDamage(stack.getItemDamage() - 1);

				data.setEnergy(player, data.getEnergy() - 300 / level);

				return;
			}
		}
	}

	@Override
	public int getMaxLevel()
	{
		return 3;
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel)
	{
		return super.getMinEnchantability(enchantmentLevel) + 20;
	}

	@Override
	public boolean canApply(ItemStack stack)
	{
		return stack.isItemStackDamageable();
	}
}

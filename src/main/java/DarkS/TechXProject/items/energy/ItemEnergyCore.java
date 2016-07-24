package DarkS.TechXProject.items.energy;

import DarkS.TechXProject.items.ItemBase;
import DarkS.TechXProject.util.NBTUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemEnergyCore extends ItemBase
{
	private final int maxInstability = 320;

	public ItemEnergyCore()
	{
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		int stability = getStability(stack);

		float div = (float) stability / maxInstability;

		String tip = "Instability " + (div <= 0.35f ? ChatFormatting.GREEN : div <= 0.65f ? ChatFormatting.YELLOW : ChatFormatting.RED) + stability;

		tooltip.add(ChatFormatting.RED + "Warning!" + ChatFormatting.RESET + " Very Unstable!");
		tooltip.add("");
		tooltip.add(tip);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		int stability = getStability(stack) == 0 ? 1 : getStability(stack);

		stability += new Random().nextInt(2);

		int chance = new Random().nextInt(Math.abs(maxInstability - stability));

		if (chance == 0 || stability >= maxInstability)
		{
			worldIn.createExplosion(entityIn, entityIn.posX, entityIn.posY, entityIn.posZ, 0.2f, true);
			entityIn.attackEntityFrom(new DamageSource("energyScape"), 0x1.fffffeP+120f);

			stack.stackSize -= 1;

			return;
		}

		setStability(stack, stability);
	}

	private void setStability(ItemStack stack, int stability)
	{
		NBTUtil.checkNBT(stack);

		stack.getTagCompound().setInteger("stability", stability);
	}

	private int getStability(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		return stack.getTagCompound().getInteger("stability");
	}
}

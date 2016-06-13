package DarkS.TechXProject.items;

import DarkS.TechXProject.init.InitBlocks;
import DarkS.TechXProject.machines.recipeStamper.MachineRecipeType;
import DarkS.TechXProject.util.NBTUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemMachineRecipe extends ItemBase
{
	public ItemMachineRecipe()
	{
		setMaxStackSize(16);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		setType(stack, -1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		if (getTypeInt(stack) > 0)
		{
			return getType(stack).name + " Recipe";
		} else return super.getItemStackDisplayName(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (getTypeInt(stack) > 0)
		{
			tooltip.add(ChatFormatting.GRAY + "Current Recipe: " + ChatFormatting.RESET + getType(stack).name);

			for (ItemStack recipe : getType(stack).inputs)
			{
				tooltip.add(" " + recipe.stackSize + "x " + ChatFormatting.WHITE + recipe.getItem().getItemStackDisplayName(recipe));
			}
		} else tooltip.add("Use on a " + InitBlocks.recipeStamper.block.getLocalizedName() + " to set the Recipe");
	}

	public void setType(ItemStack stack, int type)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		tag.setInteger("type", type);
	}

	public MachineRecipeType getType(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		return MachineRecipeType.values()[tag.getInteger("type")];
	}

	public int getTypeInt(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		NBTTagCompound tag = stack.getTagCompound();

		return tag.getInteger("type");
	}
}

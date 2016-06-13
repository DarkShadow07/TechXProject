package DarkS.TechXProject.items;

import DarkS.TechXProject.guide.GuiGuide;
import DarkS.TechXProject.util.NBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemGuide extends ItemBase
{
	public ItemGuide()
	{

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (world.isRemote && !player.isSneaking())
		{
			GuiGuide guide = new GuiGuide(getIndex(stack));
			guide.addElements();

			FMLCommonHandler.instance().showGuiScreen(guide);
		}

		return super.onItemRightClick(stack, world, player, hand);
	}

	public void setIndex(ItemStack stack, int index)
	{
		stack = NBTUtil.checkNBT(stack);

		stack.getTagCompound().setInteger("index", index);
	}

	public int getIndex(ItemStack stack)
	{
		NBTUtil.checkNBT(stack);

		return stack.getTagCompound().getInteger("index");
	}
}
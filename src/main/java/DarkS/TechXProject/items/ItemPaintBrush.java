package DarkS.TechXProject.items;

import DarkS.TechXProject.util.Util;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class ItemPaintBrush extends ItemBase
{
	private Color color;

	public ItemPaintBrush()
	{
		setMaxStackSize(1);

		setMaxDamage(512);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (color == null)
			tooltip.add("Right Click on a Color to Select it");
		else tooltip.add(String.format("Color %s %s %s", color.getRed(), color.getGreen(), color.getBlue()));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (worldIn.isRemote)
		{
			Framebuffer buffer = Util.minecraft().getFramebuffer();

			color = Util.GL.getScreenColor(buffer.framebufferWidth / 2, buffer.framebufferHeight / 2);

			itemStackIn.damageItem(1, playerIn);

			return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
		}

		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}

	public Color getColor()
	{
		return color;
	}
}

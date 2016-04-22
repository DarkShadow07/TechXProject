package DrShadow.TechXProject.metal;

import DrShadow.TechXProject.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMetalBase extends ItemBlock
{
	public ItemBlockMetalBase(Block block)
	{
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "tile." + Reference.MOD_ID.toLowerCase() + "." + EnumMetals.values()[stack.getItemDamage()].getName() + "Block";
	}
}

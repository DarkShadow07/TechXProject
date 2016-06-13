package DarkS.TechXProject.blocks.metal;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.items.ItemBase;
import DarkS.TechXProject.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import java.util.List;

public class ItemNuggetBase extends ItemBase implements IRenderer
{
	public ItemNuggetBase()
	{
		setHasSubtypes(true);

		setCreativeTab(TechXProject.oresTab);
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.NUGGET))
			{
				subItems.add(new ItemStack(this, 1, metal.ordinal()));
			}
		}
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item." + Reference.MOD_ID.toLowerCase() + "." + EnumMetals.values()[getMetadata(stack.getItemDamage())].getName() + "Nugget";
	}

	@Override
	public void registerModel()
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.NUGGET))
			{
				ModelLoader.setCustomModelResourceLocation(this, metal.ordinal(), new ModelResourceLocation(Reference.MOD_ID + ":" + metal.getName().toLowerCase() + "Nugget", "inventory"));
			}
		}
	}
}

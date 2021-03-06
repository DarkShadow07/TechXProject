package DarkS.TechXProject.blocks.metal;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.blocks.base.IRecipeProvider;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.compat.jei.crusher.CrusherRecipeHandler;
import DarkS.TechXProject.init.InitItems;
import DarkS.TechXProject.items.ItemBase;
import DarkS.TechXProject.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class ItemDustBase extends ItemBase implements IRenderer, IRecipeProvider
{
	public ItemDustBase()
	{
		setHasSubtypes(true);

		setCreativeTab(TechXProject.oresTab);
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.DUST))
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
		return "item." + Reference.MOD_ID.toLowerCase() + "." + EnumMetals.values()[getMetadata(stack.getItemDamage())].getName() + "Dust";
	}

	@Override
	public void registerModel()
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.DUST))
			{
				ModelLoader.setCustomModelResourceLocation(this, metal.ordinal(), new ModelResourceLocation(Reference.MOD_ID + ":" + metal.getName().toLowerCase() + "Dust", "inventory"));
			}
		}
	}

	@Override
	public void registerRecipes()
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.DUST) && metal.contains(EnumMetalType.INGOT) && metal.registerRecipe())
			{
				CrusherRecipeHandler.instance.addRecipe(new ItemStack(InitItems.ingot.item, 1, metal.ordinal()), 260, new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, metal.ordinal()), 1.0f));
				GameRegistry.addSmelting(new ItemStack(InitItems.dust.item, 1, metal.ordinal()), new ItemStack(InitItems.ingot.item, 1, metal.ordinal()), 0.7f);
			}
		}
	}
}

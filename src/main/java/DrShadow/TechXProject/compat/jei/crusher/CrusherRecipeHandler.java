package DrShadow.TechXProject.compat.jei.crusher;

import DrShadow.TechXProject.compat.jei.CategoryUid;
import DrShadow.TechXProject.init.InitItems;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CrusherRecipeHandler implements IRecipeHandler<CrusherRecipe>
{
	public static final CrusherRecipeHandler instance = new CrusherRecipeHandler();

	private List<CrusherRecipe> recipes = new ArrayList<>();

	public CrusherRecipeHandler()
	{
		init();
	}

	public void init()
	{
		addRecipe(new ItemStack(Blocks.stone), 100, new ItemStack(Blocks.cobblestone));
		addRecipe(new ItemStack(Blocks.cobblestone), 80, new ItemStack(Blocks.gravel));
		addRecipe(new ItemStack(Blocks.gravel), 60, new ItemStack(Blocks.sand));
		addRecipe(new ItemStack(Blocks.lapis_ore), 200, new ItemStack(Items.dye, 6, 4), new ItemStack(InitItems.dustStone));
		addRecipe(new ItemStack(Blocks.redstone_ore), 210, new ItemStack(Items.redstone, 8), new ItemStack(InitItems.dustStone));
		addRecipe(new ItemStack(Blocks.quartz_ore), 220, new ItemStack(Items.quartz, 4), new ItemStack(Blocks.netherrack));
		addRecipe(new ItemStack(Blocks.iron_ore), 160, new ItemStack(InitItems.dustIron, 2), new ItemStack(InitItems.dustStone));
		addRecipe(new ItemStack(Blocks.gold_ore), 180, new ItemStack(InitItems.dustGold, 2), new ItemStack(InitItems.dustStone));
		addRecipe(new ItemStack(Blocks.coal_ore), 120, new ItemStack(InitItems.dustCoal, 4), new ItemStack(InitItems.dustStone));
		addRecipe(new ItemStack(Blocks.diamond_ore), 240, new ItemStack(InitItems.dustDiamond, 2), new ItemStack(InitItems.dustStone));
		addRecipe(new ItemStack(Blocks.emerald_ore), 280, new ItemStack(InitItems.dustEmerald, 2), new ItemStack(InitItems.dustStone));
	}

	public void addRecipe(ItemStack in, int ticks, ItemStack... out)
	{
		List<ItemStack> inStacks = new ArrayList<>();

		if (OreDictionary.getOreIDs(in).length != 0)
		{
			for (int id : OreDictionary.getOreIDs(in))
			{
				inStacks.addAll(OreDictionary.getOres(OreDictionary.getOreName(id)));
			}
		} else inStacks.add(in);

		CrusherRecipe recipe = new CrusherRecipe(inStacks, Arrays.asList(out), ticks);

		recipes.add(recipe);
	}

	public List<ItemStack> getCrushingResult(ItemStack in)
	{
		for (CrusherRecipe recipe : recipes)
		{
			for (ItemStack stack : recipe.getInputs())
			{
				if (OreDictionary.itemMatches(stack, in, true))
				{
					return recipe.getOutputs();
				}
			}
		}

		return null;
	}

	public int getSmeltingTicks(ItemStack in)
	{
		for (CrusherRecipe recipe : recipes)
		{
			for (ItemStack stack : recipe.getInputs())
			{
				if (OreDictionary.itemMatches(stack, in, true))
				{
					return recipe.getTicks();
				}
			}
		}

		return -1;
	}

	public boolean isValidStack(ItemStack in)
	{
		for (CrusherRecipe recipe : recipes)
		{
			for (ItemStack stack : recipe.getInputs())
			{
				if (OreDictionary.itemMatches(stack, in, true))
				{
					return true;
				}
			}
		}

		return false;
	}

	public List<CrusherRecipe> getRecipeList()
	{
		return recipes;
	}

	@Nonnull
	@Override
	public Class<CrusherRecipe> getRecipeClass()
	{
		return CrusherRecipe.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid()
	{
		return CategoryUid.CRUSHER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull CrusherRecipe recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull CrusherRecipe recipe)
	{
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}
}

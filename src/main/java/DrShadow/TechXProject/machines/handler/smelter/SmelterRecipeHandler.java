package DrShadow.TechXProject.machines.handler.smelter;

import DrShadow.TechXProject.compat.jei.CategoryUid;
import DrShadow.TechXProject.util.Helper;
import DrShadow.TechXProject.util.LogHelper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmelterRecipeHandler implements IRecipeHandler<SmelterRecipe>
{
	public static final SmelterRecipeHandler instance = new SmelterRecipeHandler();

	private List<SmelterRecipe> recipes = new ArrayList<>();

	public SmelterRecipeHandler()
	{

	}

	public void init()
	{
		addRecipe(new ItemStack(Items.apple, 2), 0.5f, 100, new ItemStack[]{new ItemStack(Items.apple), new ItemStack(Items.wheat_seeds)});
	}

	public void addRecipe(ItemStack out, float xp, int ticks, ItemStack... in)
	{
		SmelterRecipe recipe = new SmelterRecipe(in, out, xp, ticks);

		if (!recipes.contains(recipe)) recipes.add(recipe);
	}

	public ItemStack getSmeltingResult(ItemStack... in)
	{
		ItemStack[] notNullArray = Helper.getStackArrayNoNull(in);

		for (int i = 0; i < notNullArray.length; i++)
		{
			if (isVanillaRecipe(notNullArray[i]))
			{
				FurnaceRecipes recipes = FurnaceRecipes.instance();

				return recipes.getSmeltingResult(notNullArray[i]);
			}
		}

		for (SmelterRecipe recipe : recipes)
		{
			List<ItemStack[]> itemStackList = recipe.getInputs();

			for (ItemStack[] entryStacks : itemStackList)
				if (Helper.isStackArrayEqual(in, entryStacks))
				{
					return recipe.getOutputs().get(0);
				}
		}

		return null;
	}

	public int getSmeltingTicks(ItemStack... in)
	{
		for (ItemStack stack : Helper.getStackArrayNoNull(in))
		{
			if (isVanillaRecipe(stack)) return 140;
		}

		for (SmelterRecipe recipe : recipes)
		{
			List<ItemStack[]> itemStackList = recipe.getInputs();

			for (ItemStack[] entryStacks : itemStackList)
				if (Helper.isStackArrayEqual(in, entryStacks))
				{
					return recipe.getTicks();
				}
		}

		return -1;
	}

	public float getSmeltingXP(ItemStack stack)
	{
		for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList())
		{
			
		}

		if (isVanillaRecipe(stack))
		{
			LogHelper.info("a");
			return FurnaceRecipes.instance().getSmeltingExperience(stack);
		} else
		{
			for (SmelterRecipe recipe : recipes)
			{
				List<ItemStack> itemStackList = recipe.getOutputs();

				for (ItemStack entryStack : itemStackList)
					if (OreDictionary.itemMatches(entryStack, stack, true))
					{
						LogHelper.info("a");

						return recipe.getXp();
					}
			}
		}

		return -1;
	}

	public boolean isVanillaRecipe(ItemStack in)
	{
		FurnaceRecipes recipes = FurnaceRecipes.instance();

		if (in == null) return false;

		return recipes.getSmeltingResult(in) == null ? false : true;
	}

	public boolean isValidStack(ItemStack stack)
	{
		if (isVanillaRecipe(stack))
		{
			return true;
		} else
		{
			for (SmelterRecipe recipe : recipes)
			{
				List<ItemStack[]> itemStackList = recipe.getInputs();

				for (ItemStack[] entryStacks : itemStackList)
				{
					for (ItemStack entryStack : entryStacks)
					{
						if (OreDictionary.itemMatches(entryStack, stack, true)) return true;
					}
				}
			}
		}

		return false;
	}

	public List<SmelterRecipe> getRecipeList()
	{
		return recipes;
	}

	@Nonnull
	@Override
	public Class<SmelterRecipe> getRecipeClass()
	{
		return SmelterRecipe.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid()
	{
		return CategoryUid.SMELTER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull SmelterRecipe recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull SmelterRecipe recipe)
	{
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}
}

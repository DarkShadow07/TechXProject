package DrShadow.TechXProject.compat.jei.smelter;

import DrShadow.TechXProject.compat.jei.CategoryUid;
import DrShadow.TechXProject.util.Util;
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
		init();
	}

	public void init()
	{
		addRecipe(new ItemStack(Items.apple, 2), 0.5f, 100, new ItemStack(Items.apple), new ItemStack(Items.wheat_seeds));
		addRecipe(new ItemStack(Items.ender_pearl, 2), 0.1f, 360, new ItemStack(Items.gunpowder), new ItemStack(Items.ghast_tear), new ItemStack(Items.ender_pearl));

		for (Map.Entry<ItemStack, ItemStack> vanillaRecipesEntry : FurnaceRecipes.instance().getSmeltingList().entrySet())
		{
			ItemStack in = vanillaRecipesEntry.getKey();
			ItemStack out = vanillaRecipesEntry.getValue();

			addRecipe(out, getSmeltingXP(out), getSmeltingTicks(in), in);
		}
	}

	public void addRecipe(ItemStack out, float xp, int ticks, ItemStack... in)
	{
		SmelterRecipe recipe = new SmelterRecipe(in, out, xp, ticks);

		recipes.add(recipe);
	}

	public ItemStack getSmeltingResult(ItemStack... in)
	{
		ItemStack[] notNullArray = Util.getStackArrayNoNull(in);

		for (SmelterRecipe recipe : recipes)
		{
			if (Util.isStackArrayEqual(notNullArray, recipe.getInputs().toArray(new ItemStack[recipe.getInputs().size()])))
			{
				return recipe.getOutputs().get(0);
			}
		}

		for (int i = 0; i < notNullArray.length; i++)
		{
			if (isVanillaRecipe(notNullArray[i]))
			{
				FurnaceRecipes recipes = FurnaceRecipes.instance();

				return recipes.getSmeltingResult(notNullArray[i]);
			}
		}

		return null;
	}

	public int getSmeltingTicks(ItemStack... in)
	{
		for (ItemStack stack : Util.getStackArrayNoNull(in))
		{
			if (isVanillaRecipe(stack)) return 140;
		}

		for (SmelterRecipe recipe : recipes)
		{
			ItemStack[] noNull = Util.getStackArrayNoNull(in);

			if (Util.isStackArrayEqual(noNull, recipe.getInputs().toArray(new ItemStack[recipe.getInputs().size()])))
			{
				return recipe.getTicks();
			}
		}

		return -1;
	}

	public float getSmeltingXP(ItemStack stack)
	{
		for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
		{
			ItemStack in = entry.getKey();
			ItemStack out = entry.getValue();

			if (OreDictionary.itemMatches(out, stack, true) && isVanillaRecipe(in))
			{
				return FurnaceRecipes.instance().getSmeltingExperience(stack) * stack.stackSize;
			}
		}

		for (SmelterRecipe recipe : recipes)
		{
			List<ItemStack> itemStackList = recipe.getOutputs();

			for (ItemStack entryStack : itemStackList)
				if (OreDictionary.itemMatches(entryStack, stack, true))
				{
					return recipe.getXp() * stack.stackSize;
				}
		}

		return -1;
	}

	public boolean isVanillaRecipe(ItemStack in)
	{
		if (in == null) return false;

		FurnaceRecipes recipes = FurnaceRecipes.instance();

		return recipes.getSmeltingResult(in) != null;
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
				List<ItemStack> itemStackList = recipe.getInputs();

				for (ItemStack itemStack : itemStackList)
				{
					if (OreDictionary.itemMatches(itemStack, stack, true))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isValidStackNoVanilla(ItemStack stack)
	{
		for (SmelterRecipe recipe : recipes)
		{
			List<ItemStack> itemStackList = recipe.getInputs();

			for (ItemStack itemStack : itemStackList)
			{
				if (OreDictionary.itemMatches(itemStack, stack, true))
				{
					return true;
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

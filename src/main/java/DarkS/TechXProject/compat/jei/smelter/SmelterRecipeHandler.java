package DarkS.TechXProject.compat.jei.smelter;

import DarkS.TechXProject.compat.jei.CategoryUid;
import DarkS.TechXProject.util.Util;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

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
		for (Map.Entry<ItemStack, ItemStack> vanillaRecipesEntry : FurnaceRecipes.instance().getSmeltingList().entrySet())
		{
			ItemStack in = vanillaRecipesEntry.getKey();
			ItemStack out = vanillaRecipesEntry.getValue();

			addRecipe(out, getSmeltingXP(out), getSmeltingTicks(in), in);
		}
	}

	@SuppressWarnings("incomplete")
	public void addRecipe(ItemStack out, float xp, int ticks, Object... in)
	{
		if (in instanceof ItemStack[])
			addRecipe(out, xp, ticks, (ItemStack[]) in);

		ItemStack[][] inputs = new ItemStack[][]{};

		for (int i = 0; i < in.length; i++)
			if (in[i] instanceof String)
				inputs[i] = OreDictionary.getOres((String) in[i]).toArray(new ItemStack[]{});

		for (int i = 0; i < inputs.length; i++)
		{
			if (inputs[i] == null && in[i] instanceof ItemStack) inputs[i] = new ItemStack[]{(ItemStack) in[i]};

			//TODO: fix
		}
	}

	public void addRecipe(ItemStack out, float xp, int ticks, ItemStack... in)
	{
		SmelterRecipe recipe = new SmelterRecipe(Arrays.asList(in), out, xp, ticks);

		recipes.add(recipe);
	}

	public int getMinStack(ItemStack out, ItemStack want)
	{
		for (SmelterRecipe recipe : recipes)
			if (OreDictionary.itemMatches(out, recipe.getOutputs().get(0), true))
				for (ItemStack stack : recipe.getInputs())
					if (OreDictionary.itemMatches(stack, want, true))
						return stack.stackSize;

		return 1;
	}

	public ItemStack getSmeltingResult(ItemStack... in)
	{
		for (SmelterRecipe recipe : recipes)
			if (Util.isStackArrayExactEqual(in, recipe.getInputs().toArray(new ItemStack[0])))
				return recipe.getOutputs().get(0);

		if (isVanillaRecipe(in))
		{
			FurnaceRecipes recipes = FurnaceRecipes.instance();

			for (ItemStack stack : in)
				if (recipes.getSmeltingResult(stack) != null) return recipes.getSmeltingResult(stack);
		}

		return null;
	}

	public int getSmeltingTicks(ItemStack... in)
	{
		if (isVanillaRecipe(Util.getStackArrayNoNull(in))) return 140;

		for (SmelterRecipe recipe : recipes)
			if (Util.isStackArrayEqual(in, recipe.getInputs().toArray(new ItemStack[recipe.getInputs().size()])))
				return recipe.getTicks();

		return -1;
	}

	public float getSmeltingXP(ItemStack stack)
	{
		for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
		{
			ItemStack in = entry.getKey();
			ItemStack out = entry.getValue();

			if (OreDictionary.itemMatches(out, stack, true) && isVanillaRecipe(in))
				return FurnaceRecipes.instance().getSmeltingExperience(stack) * stack.stackSize;
		}

		for (SmelterRecipe recipe : recipes)
		{
			List<ItemStack> itemStackList = recipe.getOutputs();

			for (ItemStack entryStack : itemStackList)
				if (OreDictionary.itemMatches(entryStack, stack, true))
					return recipe.getXp() * stack.stackSize;
		}

		return -1;
	}

	public boolean isVanillaRecipe(ItemStack... in)
	{
		if (in == null) return false;

		FurnaceRecipes recipes = FurnaceRecipes.instance();

		ItemStack prevItem;

		for (ItemStack stack : in)
		{
			prevItem = stack;

			if (recipes.getSmeltingResult(stack) == null || !OreDictionary.itemMatches(prevItem, stack, true))
				return false;
		}

		return true;
	}

	public boolean isValidStack(ItemStack stack)
	{
		if (isVanillaRecipe(stack))
			return true;
		else
			for (SmelterRecipe recipe : recipes)
			{
				List<ItemStack> itemStackList = recipe.getInputs();

				for (ItemStack itemStack : itemStackList)
					if (OreDictionary.itemMatches(itemStack, stack, true))
						return true;
			}

		return false;
	}

	public boolean isValidStackNoVanilla(ItemStack stack)
	{
		for (SmelterRecipe recipe : recipes)
		{
			List<ItemStack> itemStackList = recipe.getInputs();

			for (ItemStack itemStack : itemStackList)
				if (OreDictionary.itemMatches(itemStack, stack, true))
					return true;
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
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}
}

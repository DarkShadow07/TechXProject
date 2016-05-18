package DrShadow.TechXProject.compat.jei.crusher;

import DrShadow.TechXProject.blocks.metal.EnumMetals;
import DrShadow.TechXProject.compat.jei.CategoryUid;
import DrShadow.TechXProject.init.InitItems;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
		addRecipe(new ItemStack(Blocks.stone), 100, new ImmutablePair<>(new ItemStack(Blocks.cobblestone), 1.0f), new ImmutablePair<>(new ItemStack(Blocks.gravel), 0.25f));
		addRecipe(new ItemStack(Blocks.cobblestone), 80, new ImmutablePair<>(new ItemStack(Blocks.gravel), 1.0f), new ImmutablePair<>(new ItemStack(Items.flint), 0.25f));
		addRecipe(new ItemStack(Blocks.gravel), 60, new ImmutablePair<>(new ItemStack(Blocks.sand), 1.0f));
		addRecipe(new ItemStack(Blocks.lapis_ore), 200, new ImmutablePair<>(new ItemStack(Items.dye, 6, 4), 1.0f), new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, EnumMetals.STONE.ordinal()), 0.5f));
		addRecipe(new ItemStack(Blocks.redstone_ore), 210, new ImmutablePair<>(new ItemStack(Items.redstone, 8), 1.0f), new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, EnumMetals.STONE.ordinal()), 0.5f));
		addRecipe(new ItemStack(Blocks.quartz_ore), 220, new ImmutablePair<>(new ItemStack(Items.quartz, 4), 1.0f), new ImmutablePair<>(new ItemStack(Blocks.netherrack), 0.75f));
		addRecipe(new ItemStack(Blocks.iron_ore), 160, new ImmutablePair<>(new ItemStack(InitItems.dust.item, 2, EnumMetals.IRON.ordinal()), 1.0f), new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, EnumMetals.STONE.ordinal()), 0.5f));
		addRecipe(new ItemStack(Blocks.gold_ore), 180, new ImmutablePair<>(new ItemStack(InitItems.dust.item, 2, EnumMetals.GOLD.ordinal()), 1.0f), new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, EnumMetals.STONE.ordinal()), 0.5f));
		addRecipe(new ItemStack(Blocks.coal_ore), 120, new ImmutablePair<>(new ItemStack(InitItems.dust.item, 4, EnumMetals.COAL.ordinal()), 1.0f), new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, EnumMetals.STONE.ordinal()), 0.5f));
		addRecipe(new ItemStack(Blocks.diamond_ore), 240, new ImmutablePair<>(new ItemStack(InitItems.dust.item, 2, EnumMetals.DIAMOND.ordinal()), 1.0f), new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, EnumMetals.STONE.ordinal()), 0.5f));
		addRecipe(new ItemStack(Blocks.emerald_ore), 280, new ImmutablePair<>(new ItemStack(InitItems.dust.item, 2, EnumMetals.EMERALD.ordinal()), 1.0f), new ImmutablePair<>(new ItemStack(InitItems.dust.item, 1, EnumMetals.STONE.ordinal()), 0.5f));
	}

	public void addRecipe(ItemStack in, int ticks, Pair<ItemStack, Float>... out)
	{
		List<ItemStack> inStacks = new ArrayList<>();

		if (OreDictionary.getOreIDs(in).length > 0)
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
					List<ItemStack> result = new ArrayList<>();

					for (Pair<ItemStack, Float> pair : recipe.getOutputPairs())
					{
						result.add(pair.getLeft());
					}

					return result;
				}
			}
		}

		return null;
	}

	public int getItemChance(ItemStack in)
	{
		for (CrusherRecipe recipe : recipes)
		{
			for (Pair<ItemStack, Float> pair : recipe.getOutputPairs())
			{
				if (OreDictionary.itemMatches(pair.getLeft(), in, true))
				{
					return (int) (pair.getRight() * 100);
				}
			}
		}

		return 0;
	}

	public int getCrushingTicks(ItemStack in)
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

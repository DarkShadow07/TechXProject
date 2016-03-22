package DrShadow.TechXProject.compat.jei;


import DrShadow.TechXProject.machines.handler.smelter.SmelterRecipe;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Lang;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class SmelterRecipeCategory extends BlankRecipeCategory
{
	private static final int inSlot1 = 0;
	private static final int inSlot2 = 1;
	private static final int inSlot3 = 2;
	private static final int outSlot = 3;

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;
	@Nonnull
	private final IDrawableAnimated flame;

	public SmelterRecipeCategory(IGuiHelper guiHelper)
	{
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/smelter.png");
		background = guiHelper.createDrawable(location, 0, 0, 66, 71);
		localizedName = Lang.localize("gui.jei.smelter");

		IDrawableStatic flameDrawable = guiHelper.createDrawable(location, 176, 0, 14, 14);
		flame = guiHelper.createAnimatedDrawable(flameDrawable, 140, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Nonnull
	@Override
	public String getUid()
	{
		return CategoryUid.SMELTER;
	}

	@Nonnull
	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Nonnull
	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void drawAnimations(@Nonnull Minecraft minecraft)
	{
		flame.draw(minecraft, 2, 31);
		flame.draw(minecraft, 50, 31);
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inSlot1, true, 0, 10);
		guiItemStacks.init(inSlot2, true, 24, 0);
		guiItemStacks.init(inSlot3, true, 48, 10);

		guiItemStacks.init(outSlot, false, 24, 49);

		if (recipeWrapper instanceof SmelterRecipe)
		{
			SmelterRecipe recipe = (SmelterRecipe) recipeWrapper;
			List<ItemStack> stacksArray = recipe.getInputs();

			for (int i = 0; i < stacksArray.size(); i++)
			{
				guiItemStacks.set(i, stacksArray.get(i));
			}

			guiItemStacks.setFromRecipe(3, recipe.getOutputs());
		}
	}
}

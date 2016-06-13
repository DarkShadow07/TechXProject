package DarkS.TechXProject.compat.jei.crusher;

import DarkS.TechXProject.compat.jei.CategoryUid;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Lang;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class CrusherRecipeCategory extends BlankRecipeCategory
{
	private static final int inSlot = 0;
	private static final int outSlot1 = 1;
	private static final int outSlot2 = 2;
	private static final int outSlot3 = 3;
	private static final int outSlot4 = 4;

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;
	@Nonnull
	private final IDrawableAnimated crush;

	public CrusherRecipeCategory(IGuiHelper guiHelper)
	{
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/crusher.png");
		background = guiHelper.createDrawable(location, 0, 0, 78, 58);
		localizedName = Lang.localize("gui.jei.crusher");

		IDrawableStatic crushDrawable = guiHelper.createDrawable(location, 176, 0, 14, 14);
		crush = guiHelper.createAnimatedDrawable(crushDrawable, 140, IDrawableAnimated.StartDirection.BOTTOM, false);
	}

	@Nonnull
	@Override
	public String getUid()
	{
		return CategoryUid.CRUSHER;
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
		crush.draw(minecraft, 32, 22);
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inSlot, true, 30, 0);

		guiItemStacks.init(outSlot1, false, 0, 40);
		guiItemStacks.init(outSlot2, false, 20, 40);
		guiItemStacks.init(outSlot3, false, 40, 40);
		guiItemStacks.init(outSlot4, false, 60, 40);

		if (recipeWrapper instanceof CrusherRecipe)
		{
			CrusherRecipe recipe = (CrusherRecipe) recipeWrapper;

			guiItemStacks.setFromRecipe(inSlot, recipe.getInputs());

			for (int i = 0; i < recipe.getOutputs().size(); i++)
			{
				guiItemStacks.set(1 + i, recipe.getOutputs().get(i));
			}
		}
	}
}

package DrShadow.TechXProject.compat.jei;

import DrShadow.TechXProject.compat.jei.crusher.CrusherRecipeCategory;
import DrShadow.TechXProject.compat.jei.crusher.CrusherRecipeHandler;
import DrShadow.TechXProject.compat.jei.smelter.SmelterRecipeCategory;
import DrShadow.TechXProject.compat.jei.smelter.SmelterRecipeHandler;
import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.machines.crusher.GuiCrusher;
import DrShadow.TechXProject.machines.smelter.GuiSmelter;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class TechPlugin implements IModPlugin
{
	@Override
	public void register(@Nonnull IModRegistry registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(
				new SmelterRecipeCategory(guiHelper),
				new CrusherRecipeCategory(guiHelper)
		);

		registry.addRecipeHandlers(
				new SmelterRecipeHandler(),
				new CrusherRecipeHandler()
		);

		registry.addRecipeClickArea(GuiSmelter.class, 74, 25, 30, 30, CategoryUid.SMELTER);
		registry.addRecipeClickArea(GuiCrusher.class, 82, 29, 14, 14, CategoryUid.CRUSHER);

		registry.addRecipes(SmelterRecipeHandler.instance.getRecipeList());
		registry.addRecipes(CrusherRecipeHandler.instance.getRecipeList());

		registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.smelter.block) , CategoryUid.SMELTER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.crusher.block) , CategoryUid.CRUSHER);
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime)
	{

	}
}

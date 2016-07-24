package DarkS.TechXProject.compat.jei;

import DarkS.TechXProject.compat.jei.crusher.CrusherRecipeCategory;
import DarkS.TechXProject.compat.jei.crusher.CrusherRecipeHandler;
import DarkS.TechXProject.compat.jei.smelter.SmelterRecipeCategory;
import DarkS.TechXProject.compat.jei.smelter.SmelterRecipeHandler;
import DarkS.TechXProject.init.InitBlocks;
import DarkS.TechXProject.machines.crusher.GuiCrusher;
import DarkS.TechXProject.machines.smelter.GuiSmelter;
import mezz.jei.api.*;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class TechPlugin implements IModPlugin
{
	public static boolean jeiLoaded = false;
	private static IJeiRuntime runtime;

	public static void setSearchText(String text)
	{
		runtime.getItemListOverlay().setFilterText(text);
	}

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
		registry.addRecipeClickArea(GuiCrusher.class, 7, 61, 18, 18, CategoryUid.CRUSHER);

		registry.addRecipes(SmelterRecipeHandler.instance.getRecipeList());
		registry.addRecipes(CrusherRecipeHandler.instance.getRecipeList());

		registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.smelter.block), CategoryUid.SMELTER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.crusher.block), CategoryUid.CRUSHER);

		IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime)
	{
		runtime = jeiRuntime;
		jeiLoaded = true;
	}
}

package DrShadow.TechXProject.compat.jei;

import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.machines.handler.smelter.SmelterRecipeHandler;
import DrShadow.TechXProject.machines.smelter.GuiSmelter;
import mezz.jei.JeiHelpers;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class TechPlugin implements IModPlugin
{
	private IJeiHelpers jeiHelpers;

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers)
	{
		this.jeiHelpers = jeiHelpers;
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry)
	{

	}

	@Override
	public void register(@Nonnull IModRegistry registry)
	{
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(InitBlocks.teslaTower));

		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(
				new SmelterRecipeCategory(guiHelper)
		);

		registry.addRecipeHandlers(
				new SmelterRecipeHandler()
		);

		registry.addRecipeClickArea(GuiSmelter.class, 74, 25, 30, 30, CategoryUid.SMELTER);

		registry.addRecipes(SmelterRecipeHandler.instance.getRecipeList());
	}

	@Override
	public void onRecipeRegistryAvailable(@Nonnull IRecipeRegistry recipeRegistry)
	{

	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime)
	{

	}
}

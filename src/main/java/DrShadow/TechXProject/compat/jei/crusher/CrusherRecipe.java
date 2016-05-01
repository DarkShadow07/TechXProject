package DrShadow.TechXProject.compat.jei.crusher;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CrusherRecipe extends BlankRecipeWrapper
{
	@Nonnull
	private final List<ItemStack> inputs;
	@Nonnull
	private final List<ItemStack> outputs;

	private final String ticksString;

	private final int ticks;

	public CrusherRecipe(@Nonnull List<ItemStack> input, @Nonnull List<ItemStack> output, int ticks)
	{
		this.inputs = input;
		this.outputs = output;
		this.ticks = ticks;

		ticksString = ticks + " Ticks";
	}

	@Nonnull
	public List<ItemStack> getInputs()
	{
		return inputs;
	}

	@Nonnull
	public List<ItemStack> getOutputs()
	{
		return outputs;
	}

	public int getTicks()
	{
		return ticks;
	}

	@Nullable
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> info = new ArrayList<>();
		info.add(ticksString);

		Rectangle rect = new Rectangle(31, 21, 16, 16);

		if (rect.contains(mouseX, mouseY)) return info;

		return null;
	}
}

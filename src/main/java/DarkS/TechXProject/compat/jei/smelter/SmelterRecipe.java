package DarkS.TechXProject.compat.jei.smelter;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmelterRecipe extends BlankRecipeWrapper
{
	@Nonnull
	private final List<ItemStack> inputs;
	@Nonnull
	private final List<ItemStack> outputs;

	private final String experienceString;

	private final String ticksString;

	private final int ticks;

	private final float xp;

	public SmelterRecipe(@Nonnull List<ItemStack> input, @Nonnull ItemStack output, float experience, int ticks)
	{
		this.inputs = input;
		this.outputs = Collections.singletonList(output);
		this.ticks = ticks;
		this.xp = experience;

		if (experience > 0.0)
		{
			experienceString = experience + " Xp";
		} else experienceString = "No Xp";

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

	public float getXp()
	{
		return xp;
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
		info.add(experienceString);
		info.add(ticksString);

		Rectangle rectangleL = new Rectangle(2, 31, 14, 14);
		Rectangle rectangleR = new Rectangle(50, 31, 14, 14);

		if (rectangleL.contains(mouseX, mouseY) || rectangleR.contains(mouseX, mouseY)) return info;

		return null;
	}
}

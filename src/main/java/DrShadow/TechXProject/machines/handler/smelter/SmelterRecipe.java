package DrShadow.TechXProject.machines.handler.smelter;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class SmelterRecipe extends BlankRecipeWrapper
{
	@Nonnull
	private final List<ItemStack[]> input;
	@Nonnull
	private final List<ItemStack> outputs;

	@Nullable
	private final String experienceString;

	@Nullable
	private final String ticksString;

	@Nonnull
	private final int ticks;

	@Nonnull
	private final float xp;

	public SmelterRecipe(@Nonnull ItemStack[] input, @Nonnull ItemStack output, float experience, int ticks)
	{
		this.input = Collections.singletonList(input);
		this.outputs = Collections.singletonList(output);
		this.ticks = ticks;
		this.xp = experience;

		if (experience > 0.0)
		{
			experienceString = experience + " XP";
		} else
		{
			experienceString = null;
		}

		ticksString = ticks + " Ticks";
	}

	@Nonnull
	public List<ItemStack[]> getInputs()
	{
		return input;
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

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		if (experienceString != null)
		{
			FontRenderer fontRendererObj = minecraft.fontRendererObj;
			int stringWidth = fontRendererObj.getStringWidth(experienceString);
			fontRendererObj.drawString(experienceString, recipeWidth - stringWidth, 0, Color.gray.getRGB());
		}

		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		int stringWidth = fontRendererObj.getStringWidth(ticksString);
		fontRendererObj.drawString(ticksString, recipeWidth - stringWidth, 12, Color.gray.getRGB());
	}
}

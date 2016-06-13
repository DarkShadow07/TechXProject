package DarkS.TechXProject.compat.jei.crusher;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

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
	private final List<Pair<ItemStack, Float>> outputs;

	private final int ticks;

	public CrusherRecipe(@Nonnull List<ItemStack> input, @Nonnull List<Pair<ItemStack, Float>> output, int ticks)
	{
		this.inputs = input;
		this.outputs = output;
		this.ticks = ticks;
	}

	@Nonnull
	public List<ItemStack> getInputs()
	{
		return inputs;
	}

	@Nonnull
	public List<Pair<ItemStack, Float>> getOutputPairs()
	{
		return outputs;
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs()
	{
		List<ItemStack> result = new ArrayList<>();

		for (Pair<ItemStack, Float> pair : getOutputPairs())
		{
			result.add(pair.getLeft());
		}

		return result;
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
		info.add(ticks + " Ticks");

		Rectangle rect = new Rectangle(31, 21, 16, 16);

		if (rect.contains(mouseX, mouseY)) return info;

		return null;
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		for (int i = 0; i < outputs.size(); i++)
		{
			Rectangle slot = new Rectangle(1 + 20 * i, 40, 18, 18);

			if (slot.contains(mouseX, mouseY))
			{
				String chance = (int) (outputs.get(i).getRight() * 100) + "%";

				minecraft.fontRendererObj.drawString(chance, 0 - minecraft.fontRendererObj.getStringWidth(chance) - 2, 46, 4210752);
			}
		}
	}
}

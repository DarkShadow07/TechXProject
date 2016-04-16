package DrShadow.TechXProject.machines.recipeStamper;

import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.init.InitItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.io.Serializable;

public enum MachineRecipeType implements Serializable
{
	CLEAR(new ItemStack(Blocks.air), "Clear Recipe", "Clears the Current Machine Recipe"),
	CAPACITOR(new ItemStack(InitBlocks.basicCapacitor), InitBlocks.basicCapacitor.getLocalizedName(), "Stores Energy.", new ItemStack(Items.iron_ingot), new ItemStack(Blocks.redstone_block), new ItemStack(Blocks.iron_block)),
	SMELTER(new ItemStack(InitBlocks.smelter), InitBlocks.smelter.getLocalizedName(), "Smelts Rocks, Metals and other Things.", new ItemStack(Blocks.furnace), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.nether_brick), new ItemStack(Blocks.nether_brick)),
	CRUSHER(new ItemStack(InitBlocks.crusher), InitBlocks.crusher.getLocalizedName(), "Crushes Rocks, Metals and other things.", new ItemStack(Items.flint), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.iron_bars), new ItemStack(Blocks.iron_bars)),
	STORAGE_UNIT(new ItemStack(InitBlocks.storageUnit), InitBlocks.storageUnit.getLocalizedName(), "Massive Storage in One Block.", new ItemStack(Blocks.chest), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.iron_block)),
	TELEPORTER(new ItemStack(InitBlocks.teleporter), InitBlocks.teleporter.getLocalizedName(), "Teleports the Player across Dimensions.", new ItemStack(Items.ender_pearl), new ItemStack(Blocks.redstone_block), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot)),
	ENERGY_MONITOR(new ItemStack(InitBlocks.energyMonitor), InitBlocks.energyMonitor.getLocalizedName(), "Monitors the Current Network Energy, Gain and Drain.", new ItemStack(Items.comparator), new ItemStack(InitBlocks.basicCapacitor), new ItemStack(Items.redstone), new ItemStack(Blocks.iron_block), new ItemStack(InitItems.wrench));

	public final String name;
	public final String desc;
	public final ItemStack out;
	public final ItemStack[] inputs;

	MachineRecipeType(@Nonnull ItemStack output, @Nonnull String name, @Nonnull String desc, @Nonnull ItemStack... inputs)
	{
		this.name = name;
		this.desc = desc;
		this.out = output;

		if (output.stackSize <= 0) out.stackSize = 1;

		this.inputs = inputs;
	}
}

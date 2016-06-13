package DarkS.TechXProject.machines.recipeStamper;

import DarkS.TechXProject.init.InitBlocks;
import DarkS.TechXProject.init.InitItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.io.Serializable;

public enum MachineRecipeType implements Serializable
{
	CLEAR(new ItemStack(Blocks.AIR), "Clear Recipe", "Clears the Current Machine Recipe"),
	CAPACITOR(new ItemStack(InitBlocks.basicCapacitor.block), InitBlocks.basicCapacitor.block.getLocalizedName(), "Stores Energy.", new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.REDSTONE_BLOCK), new ItemStack(Blocks.IRON_BLOCK)),
	SMELTER(new ItemStack(InitBlocks.smelter.block), InitBlocks.smelter.block.getLocalizedName(), "Smelts Rocks, Metals and other Things.", new ItemStack(Blocks.FURNACE, 3), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.NETHER_BRICK), new ItemStack(Blocks.NETHER_BRICK)),
	CRUSHER(new ItemStack(InitBlocks.crusher.block), InitBlocks.crusher.block.getLocalizedName(), "Crushes Rocks, Metals and other things.", new ItemStack(Items.FLINT), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.IRON_BARS), new ItemStack(Blocks.IRON_BARS)),
	STORAGE_UNIT(new ItemStack(InitBlocks.storageUnit.block), InitBlocks.storageUnit.block.getLocalizedName(), "Massive Storage in One Block.", new ItemStack(Blocks.CHEST), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.IRON_BLOCK)),
	TELEPORTER(new ItemStack(InitBlocks.teleporter.block), InitBlocks.teleporter.block.getLocalizedName(), "Teleports the Player across Dimensions.", new ItemStack(Items.ENDER_PEARL), new ItemStack(Blocks.REDSTONE_BLOCK), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT)),
	ENERGY_MONITOR(new ItemStack(InitBlocks.energyMonitor.block), InitBlocks.energyMonitor.block.getLocalizedName(), "Monitors the Current Network Energy, Gain and Drain.", new ItemStack(Items.COMPARATOR), new ItemStack(InitBlocks.basicCapacitor.block), new ItemStack(Items.REDSTONE), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(InitItems.wrench.item));

	public final String name;
	public final String desc;
	public final ItemStack out;
	public final ItemStack[] inputs;

	MachineRecipeType(@Nonnull ItemStack output, @Nonnull String name, @Nonnull String desc, @Nonnull ItemStack... inputs)
	{
		this.name = name;
		this.desc = desc;
		this.out = output;

		this.inputs = inputs;
	}
}

package DarkS.TechXProject.machines.recipeStamper;

import DarkS.TechXProject.blocks.metal.EnumMetals;
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
	CAPACITOR(new ItemStack(InitBlocks.basicCapacitor.block), InitBlocks.basicCapacitor.block.getLocalizedName(), "Stores Energy", new ItemStack(InitItems.ingot.item, 4, EnumMetals.LEAD.ordinal()), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(InitItems.energyCore.item), new ItemStack(InitBlocks.metalBlock.block, 1, EnumMetals.LEAD.ordinal())),
	SMELTER(new ItemStack(InitBlocks.smelter.block), InitBlocks.smelter.block.getLocalizedName(), "Smelts Rocks, Metals and other Things", new ItemStack(Blocks.FURNACE, 3), new ItemStack(InitItems.energyCore.item), new ItemStack(InitBlocks.metalBlock.block, 2, EnumMetals.COPPER.ordinal()), new ItemStack(Items.CAULDRON)),
	CRUSHER(new ItemStack(InitBlocks.crusher.block), InitBlocks.crusher.block.getLocalizedName(), "Crushes Rocks, Metals and other things", new ItemStack(Items.FLINT, 8), new ItemStack(Blocks.IRON_BARS, 2), new ItemStack(Blocks.ANVIL), new ItemStack(InitBlocks.metalBlock.block, 1, EnumMetals.LEAD.ordinal())),
	STORAGE_UNIT(new ItemStack(InitBlocks.storageUnit.block, 2), InitBlocks.storageUnit.block.getLocalizedName(), "Bigger than a Chest", new ItemStack(Blocks.CHEST, 2), new ItemStack(InitItems.nugget.item, EnumMetals.IRON.ordinal())),
	QUARRY(new ItemStack(InitBlocks.quarry.block), InitBlocks.quarry.block.getLocalizedName(), "Mine the World", new ItemStack(Blocks.OBSIDIAN, 6), new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(Items.DIAMOND_SHOVEL), new ItemStack(Items.DIAMOND_AXE), new ItemStack(InitItems.energyCore.item), new ItemStack(InitItems.ingot.item, 8, EnumMetals.NICKEL.ordinal())),
	FARMER(new ItemStack(InitBlocks.farmer.block), InitBlocks.farmer.block.getLocalizedName(), "Automated Farming, crazy", new ItemStack(Items.SHEARS), new ItemStack(Blocks.CHEST), new ItemStack(Items.DIAMOND_AXE), new ItemStack(Items.DYE, 4, 15), new ItemStack(InitItems.processingMatrix.item)),
	TELEPORTER(new ItemStack(InitBlocks.teleporter.block), InitBlocks.teleporter.block.getLocalizedName(), "Teleports the Player across Dimensions", new ItemStack(Items.ENDER_PEARL, 8), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(InitItems.meteorShard.item, 2), new ItemStack(Blocks.ENDER_CHEST)),
	ENERGY_MONITOR(new ItemStack(InitBlocks.energyMonitor.block), InitBlocks.energyMonitor.block.getLocalizedName(), "Monitors the Current Network Energy, Gain and Drain", new ItemStack(Items.COMPARATOR, 2), new ItemStack(InitBlocks.basicCapacitor.block), new ItemStack(InitItems.energyCore.item), new ItemStack(Blocks.REDSTONE_BLOCK, 4)),
	WIRELESS_CHARGER(new ItemStack(InitBlocks.wirelessCharger.block), InitBlocks.wirelessCharger.block.getLocalizedName(), "Used to Transfer Energy without Wires to Players", new ItemStack(InitItems.energyCore.item), new ItemStack(InitItems.wire.item, 2), new ItemStack(InitItems.ingot.item, 1, EnumMetals.GRAPHENE.ordinal()), new ItemStack(InitBlocks.metalBlock.block, 1, EnumMetals.LEAD.ordinal()), new ItemStack(InitBlocks.nodeEnergy.block)),;

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

package DrShadow.TechXProject.blocks.metal;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.base.BlockBase;
import DrShadow.TechXProject.blocks.base.IRecipeProvider;
import DrShadow.TechXProject.blocks.base.IRenderer;
import DrShadow.TechXProject.compat.jei.crusher.CrusherRecipeHandler;
import DrShadow.TechXProject.init.InitBlocks;
import DrShadow.TechXProject.init.InitItems;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class BlockOreBase extends BlockBase implements IRenderer, IRecipeProvider
{
	public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetals.class);

	public BlockOreBase()
	{
		super(Material.iron);

		setCreativeTab(TechXProject.oresTab);

		setDefaultState(blockState.getBaseState().withProperty(METAL, EnumMetals.COPPER));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumMetals metal = (EnumMetals) state.getValue(METAL);

		setHardness(metal.getHardness());
		setHarvestLevel("pickaxe", metal.getHarvestLevel());

		return super.getActualState(state, worldIn, pos);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (EnumMetals.values()[meta].contains(EnumMetalType.ORE))
		{
			return getDefaultState().withProperty(METAL, EnumMetals.values()[meta]);
		} else return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumMetals) state.getValue(METAL)).ordinal();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, METAL);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.ORE))
			{
				list.add(new ItemStack(itemIn, 1, metal.ordinal()));
			}
		}
	}

	@Override
	public void registerModel()
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.ORE))
			{
				Logger.info("Registered Custom Model Block " + getUnlocalizedName() + " with Variant " + metal.ordinal() + "!");

				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), metal.ordinal(), new ModelResourceLocation(Reference.MOD_ID + ":metals/ore/" + metal.getName() + "Ore", "inventory"));
			}
		}

		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation(Reference.MOD_ID + ":metals/ore/" + ((EnumMetals) state.getValue(METAL)).getName() + "Ore", "inventory");
			}
		});
	}

	@Override
	public void registerRecipes()
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.INGOT) && metal.contains(EnumMetalType.ORE) && metal.registerRecipe())
			{
				GameRegistry.addSmelting(new ItemStack(InitBlocks.metalOre.block, 1, metal.ordinal()), new ItemStack(InitItems.ingot.item, 1, metal.ordinal()), 0.6f);
			}

			if (metal.contains(EnumMetalType.DUST) && metal.contains(EnumMetalType.ORE) && metal.registerRecipe())
			{
				CrusherRecipeHandler.instance.addRecipe(new ItemStack(InitBlocks.metalOre.block, 1, metal.ordinal()), 240, new ItemStack(InitItems.dust.item, 2, metal.ordinal()));
			}
		}
	}
}

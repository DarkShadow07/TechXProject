package DarkS.TechXProject.blocks.metal;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.blocks.base.BlockBase;
import DarkS.TechXProject.blocks.base.IRecipeProvider;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.init.InitBlocks;
import DarkS.TechXProject.init.InitItems;
import DarkS.TechXProject.reference.Reference;
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

public class BlockMetalBase extends BlockBase implements IRenderer, IRecipeProvider
{
	public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetals.class);

	public BlockMetalBase()
	{
		super(Material.IRON, 0, 1, "pickaxe");

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
		if (EnumMetals.values()[meta].contains(EnumMetalType.BLOCK))
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
			if (metal.contains(EnumMetalType.BLOCK))
			{
				list.add(new ItemStack(itemIn, 1, metal.ordinal()));
			}
		}
	}

	@Override
	public void registerModel()
	{
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				if (((EnumMetals) state.getValue(METAL)).contains(EnumMetalType.ORE))
					return new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":metalBlock", getPropertyString(state.getProperties()));

				return new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":metalBlock", "inventory");
			}
		});

		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.ORE))
			{
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(InitBlocks.metalBlock.block), metal.ordinal(), new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":metalBlock", "metal=" + metal.name()));
			}
		}
	}

	@Override
	public void registerRecipes()
	{
		for (EnumMetals metal : EnumMetals.values())
		{
			if (metal.contains(EnumMetalType.INGOT) && metal.contains(EnumMetalType.BLOCK) && metal.registerRecipe())
			{
				GameRegistry.addShapedRecipe(new ItemStack(InitBlocks.metalBlock.block, 1, metal.ordinal()), "iii", "iii", "iii", 'i', new ItemStack(InitItems.ingot.item, 1, metal.ordinal()));
				GameRegistry.addShapelessRecipe(new ItemStack(InitItems.ingot.item, 9, metal.ordinal()), new ItemStack(InitBlocks.metalBlock.block, 1, metal.ordinal()));
			}
		}
	}
}

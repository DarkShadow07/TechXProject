package DarkS.TechXProject.blocks.base;

import DarkS.TechXProject.reference.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockRotatingBase extends BlockBase implements IRenderer
{
	public static final PropertyEnum<EnumFacing> facing = PropertyEnum.create("facing", EnumFacing.class);

	public BlockRotatingBase(Material material, float hardness, int harvestLevel, String tool)
	{
		super(material, hardness, harvestLevel, tool);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockRotatingBase.facing, facing.getOpposite());
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(facing).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(facing, EnumFacing.getHorizontal(meta));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(facing).build();
	}

	@Override
	public void registerModel()
	{
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + getUnlocalizedName().substring(18), getPropertyString(state.getProperties()));
			}
		});

		for (int i = 0; i < EnumFacing.HORIZONTALS.length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + getUnlocalizedName().substring(18), "inventory"));
		}
	}
}

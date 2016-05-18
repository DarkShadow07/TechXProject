package DrShadow.TechXProject.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRotatingBase extends BlockBase implements IRenderer
{
	public static final PropertyEnum<EnumFacing> facing = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);

	public BlockRotatingBase(Material material)
	{
		super(material);
	}

	public BlockRotatingBase(Material material, float hardness, int harvestLevel, String tool)
	{
		super(material, hardness, harvestLevel, tool);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if (GuiScreen.isCtrlKeyDown())
		{
			return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockRotatingBase.facing, placer.getHorizontalFacing());
		}

		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockRotatingBase.facing, placer.getHorizontalFacing().getOpposite());
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

	}
}

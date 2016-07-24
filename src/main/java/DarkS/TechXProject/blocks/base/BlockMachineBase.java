package DarkS.TechXProject.blocks.base;

import DarkS.TechXProject.api.IWrenchable;
import DarkS.TechXProject.reference.Reference;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockMachineBase extends BlockBase implements IWrenchable, ITileEntityProvider, IRenderer
{
	public static final PropertyEnum<EnumFacing> facing = PropertyEnum.create("facing", EnumFacing.class, EnumFacing.HORIZONTALS);

	public BlockMachineBase(Material material, float hardness, int harvestLevel, String tool)
	{
		super(material, hardness, harvestLevel, tool);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(this.facing, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
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
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return null;
	}
}

package DrShadow.TechXProject.blocks;

import DrShadow.TechXProject.tileEntities.TileTeslaTower;
import DrShadow.TechXProject.util.Helper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTeslaTower extends BlockContainerBase
{
	public static final PropertyDirection rotation = PropertyDirection.create("rotation");

	private float pixel = 1F / 16F;

	public BlockTeslaTower()
	{
		super(Material.iron);

		setDefaultState(blockState.getBaseState().withProperty(rotation, EnumFacing.NORTH));

		setBlockBounds(3.35f * pixel, 0, 3.35f * pixel, 13.35f * pixel, 42 * pixel, 13.35f * pixel);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileTeslaTower();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileTeslaTower tile = (TileTeslaTower) worldIn.getTileEntity(pos);

		if (placer != null)
		{
			if (placer instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) placer;

				tile.addOwner(player);
			}
		}

		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(rotation, EnumFacing.fromAngle(placer.rotationYaw));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileTeslaTower tile = (TileTeslaTower) worldIn.getTileEntity(pos);

		Helper.minecraft().thePlayer.sendChatMessage("This Tesla Belongs to " + tile.getOwners());

		return true;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{rotation});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(rotation, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(rotation).ordinal();
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);

		if (worldIn.isAirBlock(pos.down()))
		{
			worldIn.destroyBlock(pos, true);
		}
	}
}

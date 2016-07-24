package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.blocks.base.BlockRotatingBase;
import DarkS.TechXProject.blocks.tile.highlight.IHighlightProvider;
import DarkS.TechXProject.blocks.tile.highlight.SelectionBox;
import DarkS.TechXProject.machines.activator.TileActivator;
import DarkS.TechXProject.util.ChatUtil;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockActivator extends BlockRotatingBase implements ITileEntityProvider
{
	public static PropertyBool active = PropertyBool.create("active");

	public BlockActivator()
	{
		super(Material.IRON, 5.4f, 1, "pickaxe");

		setDefaultState(getDefaultState().withProperty(active, false).withProperty(facing, EnumFacing.UP));
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
	{
		for (SelectionBox box : ((IHighlightProvider) worldIn.getTileEntity(pos)).getBoxes())
			if (box != null)
				for (AxisAlignedBB aabb : box.getBoxes())
					addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
	}

	@Nullable
	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
	{
		return ((IHighlightProvider) worldIn.getTileEntity(pos)).rayTrace(pos, start, end);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextBoolean() && worldIn.getTileEntity(pos) != null && ((TileActivator) worldIn.getTileEntity(pos)).timeOn > 0)
		{
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch (getMetaFromState(state))
		{
			case 0:
				return new AxisAlignedBB(0, 0, 0, 1, 0.125, 1);
			case 1:
				return new AxisAlignedBB(0, 1, 0, 1, 0.875, 1);
			case 2:
				return new AxisAlignedBB(0, 0, 0, 1, 1, 0.125);
			case 3:
				return new AxisAlignedBB(0, 0, 0.875, 1, 1, 1);
			case 4:
				return new AxisAlignedBB(0, 0, 0, 0.125, 1, 1);
			case 5:
				return new AxisAlignedBB(0.875, 0, 0, 1, 1, 1);
		}

		return new AxisAlignedBB(0, 0, 0, 1, 1, 1);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileActivator tile = (TileActivator) worldIn.getTileEntity(pos);

		return getDefaultState().withProperty(facing, EnumFacing.getFront(getMetaFromState(state))).withProperty(BlockActivator.active, tile != null && tile.timeOn > 0);
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileActivator tile = (TileActivator) worldIn.getTileEntity(pos);

		if (tile != null)
			if (playerIn.isSneaking())
			{
				tile.defaultOn -= GuiScreen.isCtrlKeyDown() ? 10 : 1;

				tile.defaultOn = MathHelper.clamp_int(tile.defaultOn, 5, Integer.MAX_VALUE);

				ChatUtil.sendNoSpam(playerIn, "Time On: " + tile.defaultOn, "Time Off: " + tile.defaultOff);

				worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0f, 1.1f);

				return true;
			} else
			{
				tile.defaultOn += GuiScreen.isCtrlKeyDown() ? 10 : 1;

				tile.defaultOn = MathHelper.clamp_int(tile.defaultOn, 5, Integer.MAX_VALUE);

				ChatUtil.sendNoSpam(playerIn, "Time On: " + tile.defaultOn, "Time Off: " + tile.defaultOff);

				worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 1.0f, 1.25f);

				return true;
			}

		return false;
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	{
		TileActivator tile = (TileActivator) worldIn.getTileEntity(pos);

		if (tile != null)
			if (playerIn.isSneaking())
			{
				tile.defaultOff -= GuiScreen.isCtrlKeyDown() ? 10 : 1;

				tile.defaultOff = MathHelper.clamp_int(tile.defaultOff, 5, Integer.MAX_VALUE);

				ChatUtil.sendNoSpam(playerIn, "Time On: " + tile.defaultOn, "Time Off: " + tile.defaultOff);

				worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0f, 1.1f);
			} else
			{
				tile.defaultOff += GuiScreen.isCtrlKeyDown() ? 10 : 1;

				tile.defaultOff = MathHelper.clamp_int(tile.defaultOff, 5, Integer.MAX_VALUE);

				ChatUtil.sendNoSpam(playerIn, "Time On: " + tile.defaultOn, "Time Off: " + tile.defaultOff);

				worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 1.0f, 1.5f);
			}
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		if (blockAccess.getTileEntity(pos) != null)
		{
			return ((TileActivator) blockAccess.getTileEntity(pos)).getPower();
		}
		return 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		if (blockAccess.getTileEntity(pos) != null)
		{
			return ((TileActivator) blockAccess.getTileEntity(pos)).getPower();
		}
		return 0;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(facing, active).build();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileActivator();
	}
}

package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.client.gui.GuiHandler;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.reference.Guis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLogicConduit extends BlockContainerBase
{
	public BlockLogicConduit()
	{
		super(Material.iron, 2.4f, 2, "pickaxe");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.CONDUIT_LOGIC, pos);

		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		TileLogicConduit tile = (TileLogicConduit) worldIn.getTileEntity(pos);

		return tile.condition.validState(pos, worldIn) ? 14 : 0;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileLogicConduit tile = (TileLogicConduit) worldIn.getTileEntity(pos);

		tile.onBlockBreak();

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileLogicConduit();
	}
}

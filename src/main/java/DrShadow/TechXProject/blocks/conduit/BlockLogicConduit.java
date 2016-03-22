package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.conduit.logic.TileLogicConduit;
import DrShadow.TechXProject.gui.GuiHandler;
import DrShadow.TechXProject.lib.Guis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLogicConduit extends BlockContainerBase
{
	public BlockLogicConduit()
	{
		super(Material.iron);

		setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.CONDUIT_LOGIC, pos);

		return true;
	}

	@Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos)
	{
		TileLogicConduit tile = (TileLogicConduit) worldIn.getTileEntity(pos);

		return tile.condition.validState(pos, worldIn) == true ? 14 : 0;
	}

	@Override
	public boolean hasComparatorInputOverride()
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
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileLogicConduit();
	}
}

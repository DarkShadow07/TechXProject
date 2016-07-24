package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.machines.node.redstone.TileRedstoneNode;
import DarkS.TechXProject.reference.Guis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNodeRedstone extends BlockNodeBase
{
	public BlockNodeRedstone()
	{

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!playerIn.isSneaking())
		{
			GuiHandler.openGui(playerIn, TechXProject.instance, Guis.CONDUIT, pos);
			return true;
		}

		return false;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		TileRedstoneNode node = (TileRedstoneNode) blockAccess.getTileEntity(pos);

		if (node != null && node.isOutput())
			return node.getPower();

		return 0;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		TileRedstoneNode node = (TileRedstoneNode) blockAccess.getTileEntity(pos);

		if (node != null && node.isOutput())
			return node.getPower();

		return 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return side != EnumFacing.getFront(getMetaFromState(state)) || side != EnumFacing.getFront(getMetaFromState(state)).getOpposite();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileRedstoneNode tile = new TileRedstoneNode();
		tile.setInput(true);

		return tile;
	}
}

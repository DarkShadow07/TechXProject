package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.blocks.base.BlockMachineBase;
import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.machines.energyMonitor.TileEnergyMonitor;
import DarkS.TechXProject.reference.Guis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyMonitor extends BlockMachineBase
{
	public BlockEnergyMonitor()
	{
		super(Material.IRON, 4.1f, 2, "pickaxe");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.ENERGY_MONITOR, pos);

		return true;
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return ((TileEnergyMonitor) blockAccess.getTileEntity(pos)).getRedstoneLevel();
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return ((TileEnergyMonitor) blockAccess.getTileEntity(pos)).getRedstoneLevel();
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		TileEnergyMonitor tile = (TileEnergyMonitor) worldIn.getTileEntity(pos);

		return tile.getRedstoneLevel();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEnergyMonitor();
	}
}

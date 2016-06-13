package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.blocks.base.BlockMachineBase;
import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.machines.capacitor.TileBasicCapacitor;
import DarkS.TechXProject.machines.capacitor.TileCapacitor;
import DarkS.TechXProject.reference.Guis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCapacitorBasic extends BlockMachineBase
{
	public static PropertyInteger energy = PropertyInteger.create("energy", 0, 10);

	public BlockCapacitorBasic()
	{
		super(Material.IRON, 4.3f, 2, "pickaxe");
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileCapacitor tile = (TileCapacitor) worldIn.getTileEntity(pos);

		if (tile != null)
		{
			return getDefaultState().withProperty(energy, tile.getEnergy() * 10 / tile.getMaxEnergy()).withProperty(facing, EnumFacing.getFront(getMetaFromState(state)));
		}

		return getDefaultState();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, facing, energy);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.CAPACITOR, pos);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileBasicCapacitor();
	}
}

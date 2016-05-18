package DrShadow.TechXProject.blocks.machine;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.base.BlockMachineBase;
import DrShadow.TechXProject.blocks.base.IRenderer;
import DrShadow.TechXProject.client.gui.GuiHandler;
import DrShadow.TechXProject.machines.capacitor.TileBasicCapacitor;
import DrShadow.TechXProject.machines.capacitor.TileCapacitor;
import DrShadow.TechXProject.reference.Guis;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockCapacitorBasic extends BlockMachineBase
{
	public static PropertyInteger energy = PropertyInteger.create("energy", 0, 10);

	public BlockCapacitorBasic()
	{
		super(Material.iron, 4.3f, 2, "pickaxe");
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileCapacitor tile = (TileCapacitor) worldIn.getTileEntity(pos);

		return getDefaultState().withProperty(energy, tile.getEnergy() * 10 / tile.getMaxEnergy()).withProperty(facing, EnumFacing.getFront(getMetaFromState(state)));
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

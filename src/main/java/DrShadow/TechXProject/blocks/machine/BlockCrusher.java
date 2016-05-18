package DrShadow.TechXProject.blocks.machine;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.base.BlockMachineBase;
import DrShadow.TechXProject.blocks.base.IRenderer;
import DrShadow.TechXProject.client.gui.GuiHandler;
import DrShadow.TechXProject.machines.crusher.TileCrusher;
import DrShadow.TechXProject.reference.Guis;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockCrusher extends BlockMachineBase
{
	public static PropertyBool working = PropertyBool.create("working");

	public BlockCrusher()
	{
		super(Material.iron, 3.7f, 2, "pickaxe");
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileCrusher tile = (TileCrusher) worldIn.getTileEntity(pos);

		return getDefaultState().withProperty(working, tile.working).withProperty(facing, EnumFacing.getFront(getMetaFromState(state)));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, facing, working);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.CRUSHER, pos);

		return true;
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileCrusher();
	}
}

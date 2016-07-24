package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.blocks.base.BlockMachineBase;
import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.machines.itemInterface.TileItemInterface;
import DarkS.TechXProject.reference.Guis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemInterface extends BlockMachineBase
{
	public BlockItemInterface()
	{
		super(Material.IRON, 8.2f, 2, "pickaxe");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.INTERFACE, pos);

		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return super.createTileEntity(world, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileItemInterface();
	}
}

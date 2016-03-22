package DrShadow.TechXProject.blocks.machine;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.gui.GuiHandler;
import DrShadow.TechXProject.lib.Guis;
import DrShadow.TechXProject.machines.battery.TileBasicBattery;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBatteryBasic extends BlockContainerBase
{
	public BlockBatteryBasic()
	{
		super(Material.iron);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.BATTERY, pos);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileBasicBattery();
	}
}

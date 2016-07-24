package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.machines.node.energy.TileEnergyNode;
import DarkS.TechXProject.reference.Guis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergyNode extends BlockNodeBase
{
	public BlockEnergyNode()
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
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEnergyNode tile = new TileEnergyNode();
		tile.setInput(true);

		return tile;
	}
}

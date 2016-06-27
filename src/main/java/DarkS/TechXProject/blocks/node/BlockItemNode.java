package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.TechXProject;
import DarkS.TechXProject.api.IWrench;
import DarkS.TechXProject.client.gui.GuiHandler;
import DarkS.TechXProject.node.item.TileItemNode;
import DarkS.TechXProject.reference.Guis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemNode extends BlockNodeBase
{
	public BlockItemNode()
	{

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!playerIn.isSneaking())
		{
			if (playerIn.getHeldItem(hand) != null && playerIn.getHeldItem(hand).getItem() instanceof IWrench)
			{
				TileItemNode tile = (TileItemNode) worldIn.getTileEntity(pos);

				if (tile.isInput())
				{
					tile.setInput(false);
					tile.setOutput(true);
				} else if (tile.isOutput())
				{
					tile.setOutput(false);
					tile.setInput(true);
				}
			} else GuiHandler.openGui(playerIn, TechXProject.instance, Guis.CONDUIT, pos);
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileItemNode tile = new TileItemNode();
		tile.setInput(true);

		return tile;
	}
}

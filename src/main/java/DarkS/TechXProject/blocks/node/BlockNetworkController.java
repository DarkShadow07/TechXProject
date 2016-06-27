package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.blocks.base.BlockContainerBase;
import DarkS.TechXProject.node.item.NodeUtil;
import DarkS.TechXProject.node.network.controller.TileNetworkController;
import DarkS.TechXProject.util.ChatUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockNetworkController extends BlockContainerBase
{
	public BlockNetworkController()
	{
		super(Material.IRON, 3.0f, 2, "pickaxe");
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		((INetworkContainer) worldIn.getTileEntity(pos)).searchNetwork();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileNetworkController tile = (TileNetworkController) worldIn.getTileEntity(pos);

		if (playerIn.isSneaking() && tile.getNetwork().getElements().size() > 0)
		{
			List<String> messages = NodeUtil.getNetworkInfo(tile.getNetwork());

			ChatUtil.sendNoSpamClient(messages.toArray(new String[0]));

			return true;
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileNetworkController();
	}
}

package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.conduit.network.relay.TileNetworkRelay;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockNetworkRelay extends BlockContainerBase
{
	public BlockNetworkRelay()
	{
		super(Material.iron);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileNetworkRelay relay = (TileNetworkRelay) worldIn.getTileEntity(pos);

		relay.drawLines();

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileNetworkRelay();
	}
}

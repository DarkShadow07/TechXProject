package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.node.network.relay.TileNetworkRelay;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNetworkRelay extends BlockNodeBase
{
	public BlockNetworkRelay()
	{

	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileNetworkRelay();
	}
}

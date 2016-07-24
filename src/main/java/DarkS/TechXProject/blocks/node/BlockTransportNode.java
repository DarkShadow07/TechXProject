package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.machines.node.transport.TileTransportNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTransportNode extends BlockNodeBase
{
	public BlockTransportNode()
	{

	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileTransportNode();
	}
}

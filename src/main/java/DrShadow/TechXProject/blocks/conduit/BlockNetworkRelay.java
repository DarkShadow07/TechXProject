package DrShadow.TechXProject.blocks.conduit;

import DrShadow.TechXProject.api.network.INetworkElement;
import DrShadow.TechXProject.blocks.BlockContainerBase;
import DrShadow.TechXProject.conduit.network.relay.TileNetworkRelay;
import DrShadow.TechXProject.fx.EntityReddustFXT;
import DrShadow.TechXProject.util.Util;
import DrShadow.TechXProject.util.VectorUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
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

		for (INetworkElement element : relay.getElements())
		{
			for (Vec3 vec : VectorUtil.dotsOnRay(new Vec3(pos).addVector(0.5, 0.5, 0.5), new Vec3(element.getTile().getPos()).addVector(0.5, 0.5, 0.5), 0.1f))
			{
				Util.spawnEntityFX(new EntityReddustFXT(worldIn, vec.xCoord, vec.yCoord, vec.zCoord, 0.01f, 0.45f, 0.55f));
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileNetworkRelay();
	}
}

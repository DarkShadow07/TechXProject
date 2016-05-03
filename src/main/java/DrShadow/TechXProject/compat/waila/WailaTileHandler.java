package DrShadow.TechXProject.compat.waila;

import DrShadow.TechXProject.blocks.base.BlockBase;
import DrShadow.TechXProject.client.render.IRenderObject;
import DrShadow.TechXProject.events.WorldEvents;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WailaTileHandler implements IWailaDataProvider
{
	public static void register(IWailaRegistrar registrar)
	{
		WailaTileHandler instance = new WailaTileHandler();

		registrar.registerHeadProvider(instance, BlockBase.class);
		registrar.registerBodyProvider(instance, BlockBase.class);
		registrar.registerTailProvider(instance, BlockBase.class);
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return accessor.getStack();
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		TileEntity tileEntity = accessor.getTileEntity();
		if (tileEntity instanceof IWailaHead)
			return ((IWailaHead) tileEntity).getWailaHead(itemStack, currenttip, accessor, config);

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		TileEntity tileEntity = accessor.getTileEntity();
		if (tileEntity instanceof IWailaBody)
			return ((IWailaBody) tileEntity).getWailaBody(itemStack, currenttip, accessor, config);

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		TileEntity tileEntity = accessor.getTileEntity();
		if (tileEntity instanceof IWailaTail)
			return ((IWailaTail) tileEntity).getWailaTail(itemStack, currenttip, accessor, config);

		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
	{
		if (te != null)
		{
			te.writeToNBT(tag);
		}

		return tag;
	}
}

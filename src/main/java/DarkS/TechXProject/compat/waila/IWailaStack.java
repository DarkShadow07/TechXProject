package DarkS.TechXProject.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;

public interface IWailaStack
{
	ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config);
}

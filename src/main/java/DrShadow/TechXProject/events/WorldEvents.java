package DrShadow.TechXProject.events;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.client.render.IRenderObject;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.ChatUtil;
import DrShadow.TechXProject.util.PartialTicksUtil;
import DrShadow.TechXProject.util.UpdateChecker;
import DrShadow.TechXProject.util.VectorUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldEvents
{
	public static List<IRenderObject> renderObjects = new ArrayList<>();

	public static void addRenderObject(IRenderObject obj)
	{
		renderObjects.add(obj);
	}

	@SubscribeEvent
	public void worldRenderLast(RenderWorldLastEvent event)
	{
		renderObjects.forEach(IRenderObject::setDead);

		/*if (Util.player() != null && Util.player().getHeldItemMainhand() != null && Util.player().getHeldItemMainhand().getItem() instanceof ItemNull)
		{
			ItemNull item = (ItemNull) Util.player().getHeldItemMainhand().getItem();

			for (Pair<BlockPos, IBlockState> data : item.getWorld(Util.player().getHeldItemMainhand()))
			{
				if (data.getValue() != null)
				{
					BlockPos pos = new BlockPos(360 + data.getLeft().getX(), 70 + data.getLeft().getY(), -87 + data.getLeft().getZ());

					//BlockPos pos = new BlockPos(360, 59, -87);

					renderObjects.add(new GhostBlockRenderer(data.getRight(), pos));
				}
			}
		}*/

		Vec3d pos = VectorUtil.multiply(PartialTicksUtil.calculatePos(Minecraft.getMinecraft().getRenderViewEntity()), -1);

		GlStateManager.pushMatrix();

		GlStateManager.translate(pos.xCoord, pos.yCoord, pos.zCoord);

		RenderHelper.enableStandardItemLighting();

		Iterator toRender = renderObjects.iterator();

		while (toRender.hasNext())
		{
			IRenderObject obj = (IRenderObject) toRender.next();

			obj.render();
			if (obj.isDead()) toRender.remove();
		}

		RenderHelper.disableStandardItemLighting();

		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public void worldSaveEvent(WorldEvent.Save event)
	{
		TechXProject.configurationHandler.writeConfig();
	}

	@SubscribeEvent
	public void worldLoadEvent(WorldEvent.Load event)
	{
		TechXProject.configurationHandler.readConfig();

		if (UpdateChecker.show)
		{
			ChatUtil.sendNoSpamClient(Reference.MOD_NAME + " is Outdated! " + ChatFormatting.RED + UpdateChecker.currentVersion + ChatFormatting.RESET + " || " + ChatFormatting.AQUA + UpdateChecker.newestVersion);
		}
	}
}

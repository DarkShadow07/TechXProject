package DrShadow.TechXProject.events;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.api.IWrenchable;
import DrShadow.TechXProject.client.render.IRenderObject;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.*;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
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
}

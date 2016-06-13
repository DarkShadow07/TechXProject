package DarkS.TechXProject.conduit.logic.gui;

import DarkS.TechXProject.conduit.logic.ContainerLogicConduit;
import DarkS.TechXProject.conduit.logic.TileLogicConduit;
import DarkS.TechXProject.conduit.logic.condition.EnumConditionType;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.packets.PacketUpdateCondition;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Lang;
import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiLogicConduit extends GuiContainer
{
	private int left, top;
	private TileLogicConduit conduit;

	public GuiLogicConduit(InventoryPlayer inventoryPlayer, TileLogicConduit conduit)
	{
		super(new ContainerLogicConduit(inventoryPlayer, conduit));

		this.conduit = conduit;

		this.xSize = 176;
		this.ySize = 142;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		Rectangle rect = new Rectangle(left + 80, top + 22, 16, 16);
		if (rect.contains(mouseX, mouseY))
		{
			EnumConditionType[] values = EnumConditionType.values();

			if (mouseButton == 0)
			{
				PacketHandler.sendToServer(new PacketUpdateCondition(Util.cycleArray(conduit.condition.getType(), values), conduit));
			} else if (mouseButton == 1)
			{
				PacketHandler.sendToServer(new PacketUpdateCondition(Util.cycleArrayReverse(conduit.condition.getType(), values), conduit));
			}
		}
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		conduit.conditionChanged = true;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRendererObj.drawString(Lang.localize("container.conduitLogic"), 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 49, 4210752);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawCondition(80, 23, conduit.condition.getType());

		Rectangle rect = new Rectangle(left + 80, top + 23, 16, 16);
		if (rect.contains(mouseX, mouseY))
		{
			drawRect(80, 23, 80 + 16, 23 + 16, new Color(1, 1, 1, 0.4f).hashCode());

			List<String> active = new ArrayList<>();
			active.add(ChatFormatting.GRAY + "Current Condition: " + ChatFormatting.RESET + Lang.localize("condition." + conduit.condition.getType().name()));
			drawHoveringText(active, mouseX - left, mouseY - top);
		}
	}

	private void drawCondition(int x, int y, EnumConditionType type)
	{
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_ConduitLogic.png");
		mc.getTextureManager().bindTexture(texture);

		int texX, texY = 143;

		texX = 1 + 18 * type.ordinal();

		drawTexturedModalRect(x, y, texX, texY, 16, 16);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_ConduitLogic.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

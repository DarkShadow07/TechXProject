package DarkS.TechXProject.machines.farmer;

import DarkS.TechXProject.client.gui.GuiContainerBase;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.GuiUtil;
import DarkS.TechXProject.util.Lang;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class GuiFarmer extends GuiContainerBase
{
	private int top, left;

	private TileFarmer tile;

	public GuiFarmer(InventoryPlayer inventoryPlayer, TileFarmer tile)
	{
		super(new ContainerFarmer(inventoryPlayer, tile));

		this.tile = tile;

		this.xSize = 176;
		this.ySize = 172;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GuiUtil guiUtil = new GuiUtil();

		if (tile.getStackInSlot(0) == null)
			guiUtil.drawItemStack(new ItemStack(Items.STONE_AXE), 27, 18, itemRender, fontRendererObj, new Color(139, 139, 139, 160).hashCode());
		if (tile.getStackInSlot(6) == null)
			guiUtil.drawItemStack(new ItemStack(Items.DYE, 0, 15), 67, 18, itemRender, fontRendererObj, new Color(139, 139, 139, 160).hashCode());
		if (tile.getStackInSlot(7) == null)
			guiUtil.drawItemStack(new ItemStack(Items.DYE, 0, 15), 85, 18, itemRender, fontRendererObj, new Color(139, 139, 139, 160).hashCode());

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 78, 4210752);
		fontRendererObj.drawString(Lang.localize("gui.farmer"), 8, 6, 4210752);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Farmer.png");
		mc.getTextureManager().bindTexture(texture);

		Rectangle energyBar = new Rectangle(left + 11, top + 21, 10, 50);

		int energy = (tile.getEnergy() * 50) / tile.getMaxEnergy();
		drawTexturedModalRect(11, 21 + 50 - energy, 176, 50 - energy, 10, energy);

		java.util.List<String> energyInfo = new ArrayList<>();
		energyInfo.add("Drain: " + TileFarmer.drainTick + " TF/t");
		energyInfo.add(ChatFormatting.GRAY + NumberFormat.getInstance().format(tile.getEnergy()) + "/" + NumberFormat.getInstance().format(tile.getMaxEnergy()));

		if (energyBar.contains(mouseX, mouseY))
		{
			drawHoveringText(energyInfo, mouseX - left, mouseY - top);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Farmer.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

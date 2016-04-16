package DrShadow.TechXProject.machines.capacitor;

import DrShadow.TechXProject.reference.Reference;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GuiCapacitor extends GuiContainer
{
	private int top, left;

	private TileCapacitor capacitor;

	public GuiCapacitor(InventoryPlayer inventoryPlayer, TileCapacitor battery)
	{
		super(new ContainerCapacitor(inventoryPlayer));

		this.capacitor = battery;

		xSize = 176;
		ySize = 166;
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
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Capacitor.png");
		mc.getTextureManager().bindTexture(texture);

		int energy = (capacitor.getEnergy() * 50) / capacitor.getMaxEnergy();
		drawTexturedModalRect(83, 18 + 50 - energy, 176, 50 - energy, 10, energy);

		fontRendererObj.drawString(capacitor.getBlockType().getLocalizedName(), 6, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 73, 4210752);

		Rectangle energyBar = new Rectangle(left + 83, top + 18, 10, 50);

		List<String> energyInfo = new ArrayList<>();
		energyInfo.add(NumberFormat.getInstance().format(capacitor.getEnergy()) + "/" + NumberFormat.getInstance().format(capacitor.getMaxEnergy()));
		energyInfo.add(ChatFormatting.GRAY + "Max Transfer: " + NumberFormat.getInstance().format(capacitor.getMaxTransfer()) + " TF/t");

		int send = (int) capacitor.tracker.getSendPerTick();
		int receive = (int) capacitor.tracker.getReceivePerTick();

		int transfer = receive - send;

		if (transfer < 0)
		{
			energyInfo.add(ChatFormatting.RED + NumberFormat.getInstance().format(transfer));
		}
		if (transfer > 0)
		{
			energyInfo.add(ChatFormatting.GREEN + "+" + NumberFormat.getInstance().format(transfer));
		}

		if (energyBar.contains(mouseX, mouseY))
		{
			drawHoveringText(energyInfo, mouseX - left, mouseY - top);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Capacitor.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

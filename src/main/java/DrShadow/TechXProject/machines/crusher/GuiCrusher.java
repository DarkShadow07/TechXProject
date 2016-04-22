package DrShadow.TechXProject.machines.crusher;

import DrShadow.TechXProject.gui.GuiContainerBase;
import DrShadow.TechXProject.reference.Reference;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class GuiCrusher extends GuiContainerBase
{
	private int top, left;

	private TileCrusher crusher;

	public GuiCrusher(InventoryPlayer inventoryPlayer, TileCrusher crusher)
	{
		super(new ContainerCrusher(inventoryPlayer, crusher));

		this.crusher = crusher;
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
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Crusher.png");
		mc.getTextureManager().bindTexture(texture);

		Rectangle crush = new Rectangle(left + 81, top + 28, 16, 16);
		Rectangle energyBar = new Rectangle(left + 11, top + 8, 10, 50);

		if (crusher.working)
		{
			int progress = (crusher.getProgress() * 15) / 100;

			drawTexturedModalRect(82, 29 + 14 - progress, 176, 14 - progress, 14, progress);
		}

		int energy = (crusher.getEnergy() * 50) / crusher.getMaxEnergy();
		drawTexturedModalRect(11, 8 + 50 - energy, 176, 14 + 50 - energy, 10, energy);

		java.util.List<String> energyInfo = new ArrayList<>();
		energyInfo.add("Drain: " + crusher.drainTick + " TF/t");
		energyInfo.add(ChatFormatting.GRAY + NumberFormat.getInstance().format(crusher.getEnergy()) + "/" + NumberFormat.getInstance().format(crusher.getMaxEnergy()));

		if (energyBar.contains(mouseX, mouseY))
		{
			drawHoveringText(energyInfo, mouseX - left, mouseY - top);
		}

		java.util.List<String> info = new ArrayList<>();
		info.add("Progress " + crusher.getProgress() + "%");

		if ((crush.contains(mouseX, mouseY) || crush.contains(mouseX, mouseY)) && crusher.working)
		{
			drawHoveringText(info, mouseX - left, mouseY - top);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Crusher.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

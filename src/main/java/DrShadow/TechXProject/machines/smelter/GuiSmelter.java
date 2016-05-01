package DrShadow.TechXProject.machines.smelter;

import DrShadow.TechXProject.client.gui.GuiContainerBase;
import DrShadow.TechXProject.reference.Reference;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GuiSmelter extends GuiContainerBase
{
	private int top, left;

	private TileSmelter smelter;

	public GuiSmelter(InventoryPlayer inventoryPlayer, TileSmelter smelter)
	{
		super(new ContainerSmelter(inventoryPlayer, smelter));

		this.smelter = smelter;
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
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Smelter.png");
		mc.getTextureManager().bindTexture(texture);

		Rectangle fireL = new Rectangle(left + 57, top + 36, 16, 16);
		Rectangle fireR = new Rectangle(left + 105, top + 36, 16, 16);
		Rectangle energyBar = new Rectangle(left + 11, top + 8, 10, 50);

		if (smelter.working)
		{
			int progress = (smelter.getProgress() * 13) / 100;

			drawTexturedModalRect(106, 38 + 13 - progress, 176, 13 - progress, 13, progress);
			drawTexturedModalRect(58, 38 + 13 - progress, 176, 13 - progress, 13, progress);
		}

		int energy = (smelter.getEnergy() * 50) / smelter.getMaxEnergy();
		drawTexturedModalRect(11, 8 + 50 - energy, 176, 14 + 50 - energy, 10, energy);

		List<String> energyInfo = new ArrayList<>();
		energyInfo.add("Drain: " + smelter.drainTick + " TF/t");
		energyInfo.add(ChatFormatting.GRAY + NumberFormat.getInstance().format(smelter.getEnergy()) + "/" + NumberFormat.getInstance().format(smelter.getMaxEnergy()));

		if (energyBar.contains(mouseX, mouseY))
		{
			drawHoveringText(energyInfo, mouseX - left, mouseY - top);
		}

		List<String> info = new ArrayList<>();
		info.add("Progress " + smelter.getProgress() + "%");

		if ((fireL.contains(mouseX, mouseY) || fireR.contains(mouseX, mouseY)) && smelter.working)
		{
			drawHoveringText(info, mouseX - left, mouseY - top);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Smelter.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

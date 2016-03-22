package DrShadow.TechXProject.machines.battery;

import DrShadow.TechXProject.reference.Reference;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBattery extends GuiContainer
{
	private int top, left;

	private TileBatteryBase battery;

	public GuiBattery(InventoryPlayer inventoryPlayer, TileBatteryBase battery)
	{
		super(new ContainerBattery(inventoryPlayer));

		this.battery = battery;

		xSize = 176;
		ySize = 146;
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
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Battery.png");
		mc.getTextureManager().bindTexture(texture);

		int energy = (battery.getEnergy() * 50) / battery.getMaxEnergy();
		drawTexturedModalRect(82, 8 + 50 - energy, 176, 50 - energy, 10, energy);

		Rectangle energyBar = new Rectangle(left + 82, top + 8, 10, 50);

		List<String> energyInfo = new ArrayList<>();
		energyInfo.add(NumberFormat.getInstance().format(battery.getEnergy()) + "/" + NumberFormat.getInstance().format(battery.getMaxEnergy()));
		energyInfo.add(ChatFormatting.GRAY + "Max Transfer: " + NumberFormat.getInstance().format(battery.getMaxTransfer()) + " TF/t");

		if (energyBar.contains(mouseX, mouseY))
		{
			drawHoveringText(energyInfo, mouseX - left, mouseY - top);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Battery.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

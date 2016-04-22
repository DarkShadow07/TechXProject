package DrShadow.TechXProject.machines.energyMonitor;

import DrShadow.TechXProject.gui.GuiContainerBase;
import DrShadow.TechXProject.reference.Reference;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GuiEnergyMonitor extends GuiContainerBase
{
	private int top, left;

	private TileEnergyMonitor monitor;

	public GuiEnergyMonitor(InventoryPlayer inventoryPlayer, TileEnergyMonitor monitor)
	{
		super(new ContainerEnergyMonitor(inventoryPlayer));

		this.monitor = monitor;

		xSize = 176;
		ySize = 196;
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
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_EnergyMonitor.png");
		mc.getTextureManager().bindTexture(texture);

		if (monitor.hasNetowork())
		{
			if (monitor.getMaxNetworkEnergy() > 0)
			{
				int networkEnergy = (int) ((monitor.getNetworkEnergy() * 100) / monitor.getMaxNetworkEnergy());
				drawTexturedModalRect(22, 8 + 100 - networkEnergy, 186, 100 - networkEnergy, 10, networkEnergy);
			}
		}

		int energy = (monitor.getEnergy() * 100) / monitor.getMaxEnergy();
		drawTexturedModalRect(8, 8 + 100 - energy, 176, 100 - energy, 10, energy);

		Rectangle energyBar = new Rectangle(left + 8, top + 8, 10, 100);
		Rectangle networkEnergyBar = new Rectangle(left + 22, top + 8, 10, 100);

		if (energyBar.contains(mouseX, mouseY))
		{
			List<String> info = new ArrayList<>();

			info.add(ChatFormatting.GRAY + "Energy Monitor Energy");
			info.add(NumberFormat.getInstance().format(monitor.getEnergy()) + "/" + NumberFormat.getInstance().format(monitor.getMaxEnergy()));

			drawHoveringText(info, mouseX - left, mouseY - top);
		}

		if (monitor.hasNetowork())
		{
			if (networkEnergyBar.contains(mouseX, mouseY))
			{
				if (monitor.getMaxNetworkEnergy() > 0)
				{
					int networkEnergy = (int) ((monitor.getNetworkEnergy() * 100) / monitor.getMaxNetworkEnergy());

					List<String> info = new ArrayList<>();

					info.add(ChatFormatting.GRAY + "Network Energy - " + networkEnergy + "%");
					info.add(NumberFormat.getInstance().format(monitor.getNetworkEnergy()) + "/" + NumberFormat.getInstance().format(monitor.getMaxNetworkEnergy()));

					long generating = (long) (monitor.networkTracker.getReceivePerTick() - monitor.networkTracker.getSendPerTick());

					if (generating > 0)
					{
						info.add(ChatFormatting.GREEN + "+" + NumberFormat.getInstance().format(generating));
					} else
					{
						info.add(ChatFormatting.RED + NumberFormat.getInstance().format(generating));
					}

					drawHoveringText(info, mouseX - left, mouseY - top);
				}
			}
		} else if (networkEnergyBar.contains(mouseX, mouseY))
		{
			List<String> info = new ArrayList<>();

			info.add(ChatFormatting.RED + "Need to be attached to a Conduit Network!");

			drawHoveringText(info, mouseX - left, mouseY - top);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_EnergyMonitor.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

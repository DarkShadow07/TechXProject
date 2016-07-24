package DarkS.TechXProject.machines.energyMonitor;

import DarkS.TechXProject.client.gui.GuiContainerBase;
import DarkS.TechXProject.client.gui.widget.GuiButtonExpand;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GuiEnergyMonitor extends GuiContainerBase
{
	private int top, left;

	private TileEnergyMonitor monitor;

	private GuiTextField minEnergy, maxEnergy;

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

		new GuiButtonExpand(this, 0, 126, 74, Color.red, new ResourceLocation("textures/items/redstone_dust.png"), 0, 0, "Redstone Control", "Use the Redstone Control Mode to define at what Levels of Energy the Monitor should Emit a Rendstone Signal");

		minEnergy = new GuiTextField(0, fontRendererObj, left + 98, top + 22, 26, 12);
		minEnergy.setCanLoseFocus(true);
		minEnergy.setMaxStringLength(3);
		minEnergy.setVisible(true);

		maxEnergy = new GuiTextField(1, fontRendererObj, left + 98, top + 40, 26, 12);
		maxEnergy.setCanLoseFocus(true);
		maxEnergy.setMaxStringLength(3);
		maxEnergy.setVisible(true);

		minEnergy.setText(monitor.minE + "");
		maxEnergy.setText(monitor.maxE + "");
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		minEnergy.updateCursorCounter();
		maxEnergy.updateCursorCounter();
	}

	@Override
	public void onGuiClosed()
	{
		PacketHandler.sendToServer(new PacketUpdateMonitor(monitor, Integer.valueOf(minEnergy.getText()), Integer.valueOf(maxEnergy.getText())));

		monitor.minE = Integer.valueOf(minEnergy.getText());
		monitor.maxE = Integer.valueOf(maxEnergy.getText());

		super.onGuiClosed();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		minEnergy.mouseClicked(mouseX, mouseY, mouseButton);
		maxEnergy.mouseClicked(mouseX, mouseY, mouseButton);

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (Util.isInteger(typedChar + "") || keyCode == Keyboard.KEY_BACK)
		{
			minEnergy.textboxKeyTyped(typedChar, keyCode);
			maxEnergy.textboxKeyTyped(typedChar, keyCode);
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (!minEnergy.getText().equals(""))
		{
			int percent = Integer.valueOf(minEnergy.getText());

			if (percent > 100) minEnergy.setText("100");
		}

		if (!maxEnergy.getText().equals(""))
		{
			int percent = Integer.valueOf(maxEnergy.getText());

			if (percent > 100) maxEnergy.setText("100");
		}

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

		fontRendererObj.drawString("Redstone Control Mode", 38, 8, 4210752);
		fontRendererObj.drawSplitString("Min Energy         %", 38, 24, 140, 4210752);
		fontRendererObj.drawSplitString("Max Energy        %", 38, 42, 140, 4210752);

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
					} else if (generating < 0)
					{
						info.add(ChatFormatting.RED + NumberFormat.getInstance().format(generating));
					}

					drawHoveringText(info, mouseX - left, mouseY - top);
				}
			}
		} else if (networkEnergyBar.contains(mouseX, mouseY))
		{
			List<String> info = new ArrayList<>();

			info.add(ChatFormatting.RED + "Need to be attached to a Node Network!");

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

		minEnergy.drawTextBox();
		maxEnergy.drawTextBox();
	}
}

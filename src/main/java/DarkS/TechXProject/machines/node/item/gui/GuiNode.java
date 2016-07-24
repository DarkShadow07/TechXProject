package DarkS.TechXProject.machines.node.item.gui;

import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.init.InitItems;
import DarkS.TechXProject.machines.node.PacketUpdateIO;
import DarkS.TechXProject.machines.node.energy.TileEnergyNode;
import DarkS.TechXProject.machines.node.item.ContainerItemNode;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.GuiUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiNode extends GuiContainer
{
	private int left, top;

	private INetworkElement element;

	public GuiNode(InventoryPlayer inventoryPlayer, INetworkElement element)
	{
		super(new ContainerItemNode(inventoryPlayer, element instanceof IInventory ? (IInventory) element : null));

		this.element = element;

		this.xSize = 180;
		this.ySize = 166;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;

		buttonList.add(new GuiButton(0, left + 7, top + 7, 9, 20, "<"));
		buttonList.add(new GuiButton(1, left + 107, top + 7, 9, 20, ">"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		switch (button.id)
		{
			case 0:
			case 1:
			{
				if (element instanceof TileEnergyNode)
				{
					if (element.isInput() && element.isOutput())
					{
						element.setInput(true);
						element.setOutput(false);
					} else if (element.isInput())
					{
						element.setInput(false);
						element.setOutput(true);
					} else
					{
						element.setInput(true);
						element.setOutput(true);
					}
				} else
				{
					element.setInput(!element.isInput());
					element.setOutput(!element.isOutput());
				}

				PacketHandler.sendToServer(new PacketUpdateIO(element));
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Conduit.png");
		mc.getTextureManager().bindTexture(texture);

		drawRect(29, 31, 173, 31 + 18, new Color(1, 1, 1, 0.35f).getRGB());

		drawTexturedModalRect(15, 7, 0, 166, 92, 20);

		String mode = element.isInput() && element.isOutput() ? "Input/Output" : element.isInput() ? "Input" : "Output";

		drawCenteredString(fontRendererObj, mode, 61, 12, Color.white.getRGB());

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 74, 4210752);

		if (element instanceof IInventory && !inventorySlots.inventorySlots.get(0).getHasStack())
		{
			GuiUtil util = new GuiUtil();

			util.drawItemStack(new ItemStack(InitItems.filterBase.item), inventorySlots.inventorySlots.get(0).xDisplayPosition, inventorySlots.inventorySlots.get(0).yDisplayPosition, itemRender, fontRendererObj, new Color(168, 168, 168, 168).getRGB());
		}

		if (!(element instanceof IInventory))
			drawRect(7, 31, 7 + 166, 31 + 38, new Color(198, 198, 198).getRGB());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Conduit.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

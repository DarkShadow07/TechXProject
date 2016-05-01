package DrShadow.TechXProject.machines.storageUnit;

import DrShadow.TechXProject.client.gui.GuiContainerBase;
import DrShadow.TechXProject.reference.Reference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiStorageUnit extends GuiContainerBase
{
	private int top, left;

	public GuiStorageUnit(InventoryPlayer inventoryPlayer, TileStorageUnit tile)
	{
		super(new ContainerStorageUnit(inventoryPlayer, tile));

		xSize = 176;
		ySize = 248;
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
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 155, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_StorageUnit.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

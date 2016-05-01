package DrShadow.TechXProject.machines.recipeChest;

import DrShadow.TechXProject.client.gui.GuiContainerBase;
import DrShadow.TechXProject.client.gui.widget.GuiButtonExpand;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Lang;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiRecipeChest extends GuiContainerBase
{
	private int top, left;

	public GuiRecipeChest(InventoryPlayer inventoryPlayer, TileRecipeChest tile)
	{
		super(new ContainerRecipeChest(inventoryPlayer, tile));

		xSize = 176;
		ySize = 186;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;

		buttonList.add(new GuiButtonExpand(this, 0, 118, 38, Color.cyan, new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/gui/icon/icon_Info.png"), 0, 0, "Machine Recipe Chest", "A Chest used to Store Machine Recipes"));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 92, 4210752);
		fontRendererObj.drawString(Lang.localize("gui.recipeChest"), 8, 7, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_RecipeChest.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

package DrShadow.TechXProject.machines.recipeChest;

import DrShadow.TechXProject.gui.GuiContainerBase;
import DrShadow.TechXProject.reference.Reference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiRecipeChest extends GuiContainerBase
{
	private int top, left;

	public GuiRecipeChest(InventoryPlayer inventoryPlayer, TileRecipeChest tile)
	{
		super(new ContainerRecipeChest(inventoryPlayer, tile));

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
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_RecipeChest.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

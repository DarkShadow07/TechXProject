package DarkS.TechXProject.machines.recipeStamper;

import DarkS.TechXProject.client.gui.GuiContainerBase;
import DarkS.TechXProject.reference.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiRecipeStamper extends GuiContainerBase
{
	private int top, left;

	private GuiButton next, prev;

	private TileRecipeStamper tile;

	public GuiRecipeStamper(InventoryPlayer inventoryPlayer, TileRecipeStamper tile)
	{
		super(new ContainerRecipeStamper(inventoryPlayer, tile));

		this.tile = tile;

		this.xSize = 176;
		this.ySize = 176;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;

		prev = new GuiButton(0, left + 7, top + 6, 8, 20, "<");
		next = new GuiButton(1, left + 139, top + 6, 8, 20, ">");

		buttonList.add(prev);
		buttonList.add(next);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		next.enabled = tile.hasNext();
		prev.enabled = tile.hasPrev();
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();

		int direction = Mouse.getEventDWheel();

		if (direction != 0)
		{
			if (direction > 1 && tile.hasPrev()) tile.selected -= 1;
			if (direction < 1 && tile.hasNext()) tile.selected += 1;

			tile.selected = MathHelper.clamp_int(tile.selected, 0, MachineRecipeType.values().length - 1);

			tile.stamp();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		switch (button.id)
		{
			case 0:
				tile.selected -= 1;
				tile.stamp();
				break;
			case 1:
				tile.selected += 1;
				tile.stamp();
				break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GlStateManager.color(1, 1, 1, 0.25f);

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 82, 4210752);

		fontRendererObj.drawSplitString(tile.getSelectedType().desc, 9, 31, 158, 4210752);

		fontRendererObj.drawString(tile.getSelectedType().name, 78 - fontRendererObj.getStringWidth(tile.getSelectedType().name) / 2, 12, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_RecipeStamper.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

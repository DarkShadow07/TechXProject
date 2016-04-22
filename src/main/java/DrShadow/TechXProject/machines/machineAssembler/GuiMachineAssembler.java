package DrShadow.TechXProject.machines.machineAssembler;


import DrShadow.TechXProject.gui.GuiContainerBase;
import DrShadow.TechXProject.gui.widget.GuiButtonExpand;
import DrShadow.TechXProject.items.ItemMachineRecipe;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Lang;
import DrShadow.TechXProject.util.OverlayHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiMachineAssembler extends GuiContainerBase
{
	private int top, left;

	private TileMachineAssembler assembler;

	private GuiButtonExpand infoButton;

	public GuiMachineAssembler(InventoryPlayer inventoryPlayer, TileMachineAssembler assembler)
	{
		super(new ContainerMachineAssembler(inventoryPlayer, assembler));

		this.assembler = assembler;

		this.xSize = 176;
		this.ySize = 190;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;

		infoButton = new GuiButtonExpand(this, 0, 122, 74, Color.green, new ResourceLocation(Reference.MOD_ID + ":textures/items/wrench.png"), 0, 0, "Machine Assembler", "This machine is used for Assembling other Machines, it needs a Machine Recipe crafted on a Recipe Stamper that is not consumed");

		buttonList.add(infoButton);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_MachineAssembler.png");
		mc.getTextureManager().bindTexture(texture);

		int progress = assembler.progress * 24 / 500;

		drawTexturedModalRect(121, 18, 176, 0, progress, 17);

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 96, 4210752);
		fontRendererObj.drawString(Lang.localize("gui.assembler"), 8, 6, 4210752);

		if (assembler.getStackInSlot(0) != null)
		{
			ItemMachineRecipe recipe = (ItemMachineRecipe) assembler.getStackInSlot(0).getItem();

			fontRendererObj.drawStringWithShadow(recipe.getType(assembler.getStackInSlot(0)).name, 29, 22, Color.WHITE.getRGB());

			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					ItemStack[] stacks = recipe.getType(assembler.getStackInSlot(0)).inputs;
					if (j + i * 9 < stacks.length && getSlotUnderMouse() != null && getSlotUnderMouse() instanceof ContainerMachineAssembler.SlotAssembler && !getSlotUnderMouse().getHasStack())
					{
						OverlayHelper helper = new OverlayHelper();

						helper.drawPlaneWithBorder(mouseX + 14 - left, mouseY - 10 - top, Math.max(stacks.length * 18, fontRendererObj.getStringWidth("Items needed")), 24, -267386864, 1347420415, true);
						fontRendererObj.drawStringWithShadow("Items needed", mouseX + 14 - left, mouseY - 10 - top, Color.WHITE.getRGB());
						drawItemStack(stacks[j + i * 9], mouseX + 14 - left + j * 18, mouseY - top + i * 18);

					}
				}
			}
		}

		Rectangle progressArea = new Rectangle(left + 121, top + 18, 24, 17);
		if (progressArea.contains(mouseX, mouseY) && assembler.working)
		{
			List<String> info = new ArrayList<>();
			info.add("Progress " + assembler.progress / 5 + "%");

			drawHoveringText(info, mouseX - left, mouseY - top);
		}
	}

	private void drawItemStack(ItemStack stack, int x, int y)
	{
		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_MachineAssembler.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

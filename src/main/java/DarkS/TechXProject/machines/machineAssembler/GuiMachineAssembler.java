package DarkS.TechXProject.machines.machineAssembler;


import DarkS.TechXProject.client.gui.GuiContainerBase;
import DarkS.TechXProject.client.gui.widget.GuiButtonExpand;
import DarkS.TechXProject.items.ItemMachineRecipe;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.GuiUtil;
import DarkS.TechXProject.util.Lang;
import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiMachineAssembler extends GuiContainerBase
{
	private int top, left;

	private TileMachineAssembler assembler;

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

		new GuiButtonExpand(this, 0, 122, 74, Color.cyan, new ResourceLocation(Reference.MOD_ID + ":textures/gui/icon/icon_Info.png"), 0, 0, "Machine Assembler", "This machine is used for Assembling other Machines, it needs a Machine Recipe crafted on a Recipe Stamper that is not consumed");
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_MachineAssembler.png");
		mc.getTextureManager().bindTexture(texture);

		int progress = assembler.progress * 24 / 800;

		drawTexturedModalRect(31, 18, 176, 0, progress, 17);

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 96, 4210752);

		if (assembler.getStackInSlot(0) != null)
		{
			ItemMachineRecipe recipe = (ItemMachineRecipe) assembler.getStackInSlot(0).getItem();

			fontRendererObj.drawString("Recipe: " + recipe.getType(assembler.getStackInSlot(0)).name, 8, 6, 4210752);

			ItemStack[] stacks = recipe.getType(assembler.getStackInSlot(0)).inputs;

			for (Slot slot : inventorySlots.inventorySlots)
			{
				if (slot instanceof ContainerMachineAssembler.SlotAssembler && slot.getSlotIndex() > stacks.length + 1)
				{
					drawRect(slot.xDisplayPosition - 1, slot.yDisplayPosition - 1, slot.xDisplayPosition + 17, slot.yDisplayPosition + 17, new Color(198, 198, 198, 255).getRGB());
				}
			}

			for (int i = 0; i < stacks.length; i++)
			{
				Slot slot = inventorySlots.getSlot(i + 2);

				GuiUtil guiUtil = new GuiUtil();

				if (!slot.getHasStack())
				{
					guiUtil.drawItemStack(stacks[i], slot.xDisplayPosition, slot.yDisplayPosition, itemRender, fontRendererObj, new Color(139, 139, 139, 160).hashCode());
				}

				Slot slotUnder = getSlotUnderMouse();

				if (slotUnder instanceof ContainerMachineAssembler.SlotAssembler && !slotUnder.getHasStack() && slotUnder.getSlotIndex() < stacks.length + 2)
				{
					List<String> info = new ArrayList<>();
					info.add(stacks[slotUnder.getSlotIndex() - 2].getDisplayName() + " (" + stacks[slotUnder.getSlotIndex() - 2].stackSize + "x)");

					info.add(ChatFormatting.DARK_GRAY + Item.REGISTRY.getNameForObject(stacks[slotUnder.getSlotIndex() - 2].getItem()).toString());

					info.add(String.format("%s%s" + Util.getMod(stacks[slotUnder.getSlotIndex() - 2]), ChatFormatting.BLUE, ChatFormatting.ITALIC));

					drawHoveringText(info, mouseX - left, mouseY - top);
				}
			}
		} else fontRendererObj.drawString(Lang.localize("gui.assembler"), 8, 6, 4210752);

		Rectangle progressArea = new Rectangle(left + 31, top + 18, 24, 17);
		if (progressArea.contains(mouseX, mouseY) && assembler.working)
		{
			List<String> info = new ArrayList<>();
			info.add("Progress " + assembler.progress / 8 + "%");

			drawHoveringText(info, mouseX - left, mouseY - top);
		}
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

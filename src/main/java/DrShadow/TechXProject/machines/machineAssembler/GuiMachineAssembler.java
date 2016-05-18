package DrShadow.TechXProject.machines.machineAssembler;


import DrShadow.TechXProject.client.gui.GuiContainerBase;
import DrShadow.TechXProject.client.gui.widget.GuiButtonExpand;
import DrShadow.TechXProject.items.ItemMachineRecipe;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.GuiUtil;
import DrShadow.TechXProject.util.Lang;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.sun.xml.internal.ws.util.StringUtils;
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

		int progress = assembler.progress * 24 / 500;

		drawTexturedModalRect(121, 18, 176, 0, progress, 17);

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 96, 4210752);
		fontRendererObj.drawString(Lang.localize("gui.assembler"), 8, 6, 4210752);

		if (assembler.getStackInSlot(0) != null)
		{
			ItemMachineRecipe recipe = (ItemMachineRecipe) assembler.getStackInSlot(0).getItem();

			fontRendererObj.drawStringWithShadow(recipe.getType(assembler.getStackInSlot(0)).name, 29, 22, Color.WHITE.getRGB());

			ItemStack[] stacks = recipe.getType(assembler.getStackInSlot(0)).inputs;

			for (Slot slot : inventorySlots.inventorySlots)
			{
				if (slot instanceof ContainerMachineAssembler.SlotAssembler && slot.getSlotIndex() > stacks.length + 1)
				{
					drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, new Color(0.75f, 0.0f, 0.015f, 0.15f).getRGB());
				}
			}

			for (int i = 0; i < stacks.length; i++)
			{
				Slot slot = inventorySlots.getSlot(i + 2);

				GuiUtil guiUtil = new GuiUtil();

				if (!slot.getHasStack())
				{
					guiUtil.drawItemStack(stacks[i], slot.xDisplayPosition, slot.yDisplayPosition, itemRender, true);
				}

				Slot slotUnder = getSlotUnderMouse();

				if (slotUnder != null && slotUnder instanceof ContainerMachineAssembler.SlotAssembler && !slotUnder.getHasStack() && slotUnder.getSlotIndex() < stacks.length + 2)
				{
					List<String> info = new ArrayList<>();
					info.add(stacks[slotUnder.getSlotIndex() - 2].getDisplayName() + " (" + stacks[slotUnder.getSlotIndex() - 2].stackSize + "x)");

					info.add(ChatFormatting.DARK_GRAY + Item.itemRegistry.getNameForObject(stacks[slotUnder.getSlotIndex() - 2].getItem()).toString());
					info.add(String.format("%s%s" + StringUtils.capitalize(Item.itemRegistry.getNameForObject(stacks[slotUnder.getSlotIndex() - 2].getItem()).getResourceDomain()), ChatFormatting.BLUE, ChatFormatting.ITALIC));

					drawHoveringText(info, mouseX - left, mouseY - top);
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

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_MachineAssembler.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

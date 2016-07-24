package DarkS.TechXProject.machines.itemInterface;

import DarkS.TechXProject.compat.jei.TechPlugin;
import DarkS.TechXProject.machines.node.network.NetworkUtil;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.GuiUtil;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuiItemInterface extends GuiContainer
{
	private int top, left;

	private TileItemInterface tile;

	private GuiTextField filter;

	private float scroll;
	private boolean scrollEnabled, scrolling;
	private Rectangle scrollArea;

	private List<ItemStack> filteredStacks;

	public GuiItemInterface(InventoryPlayer inventoryPlayer, TileItemInterface tile)
	{
		super(new ContainerItemInterface(inventoryPlayer, tile));

		this.xSize = 194;
		this.ySize = 244;

		this.tile = tile;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;

		filter = new GuiTextField(0, fontRendererObj, 81, 6, 88, 12);
		filter.setEnableBackgroundDrawing(false);

		Keyboard.enableRepeatEvents(true);

		scrollArea = new Rectangle(left + 175, top + 18, 12, 106);

		filteredStacks = Util.mergeSameItem(tile.stacks.stream().filter(stack -> stack.getDisplayName().toLowerCase().contains(filter.getText().toLowerCase())).collect(Collectors.toList()));
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		int dir = Mouse.getEventDWheel();

		if (dir != 0)
		{
			if (dir > 0)
				scroll -= 0.056f;
			else scroll += 0.056f;
		}

		scroll = MathHelper.clamp_float(scroll, 0, 1);

		super.handleMouseInput();
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		if (scrollEnabled && scrolling)
			scroll = (float) ((mouseY - scrollArea.getY()) / scrollArea.getHeight());

		scroll = MathHelper.clamp_float(scroll, 0, 1);

		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		filter.updateCursorCounter();

		scrollEnabled = (float) filteredStacks.size() / 9 - 6 > 0;
		if (!scrollEnabled) scroll = 0;

		filteredStacks = Util.mergeSameItem(tile.stacks.stream().filter(stack -> stack.getDisplayName().toLowerCase().contains(filter.getText().toLowerCase())).collect(Collectors.toList()));
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (filter.textboxKeyTyped(typedChar, keyCode))
		{
			if (TechPlugin.jeiLoaded)
				TechPlugin.setSearchText(filter.getText());
		} else
			super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		scrolling = false;

		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if (scrollEnabled && scrollArea.contains(mouseX, mouseY))
		{
			scrolling = true;

			scroll = (float) ((mouseY - scrollArea.getY()) / scrollArea.getHeight());
		}

		scroll = MathHelper.clamp_float(scroll, 0, 1);

		filter.mouseClicked(mouseX - left, mouseY - top, mouseButton);

		if (filter.isFocused() && mouseButton == 1 || mouseButton == 2) filter.setText("");

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if (filteredStacks.size() > j + i * 9 + 9 * Math.ceil(Math.ceil(((float) filteredStacks.size() / 9 - 6)) * scroll))
				{
					ItemStack stack = filteredStacks.get((int) (j + i * 9 + 9 * Math.ceil(Math.ceil(((float) filteredStacks.size() / 9 - 6)) * scroll))).copy();

					stack.stackSize = Math.min(stack.stackSize, isShiftKeyDown() ? 64 : 1);

					Rectangle r = new Rectangle(left + 7 + j * 18, top + 18 + i * 18, 18, 18);

					if (r.contains(mouseX, mouseY))
					{
						PacketHandler.sendToServer(new PacketExtractFromNetwork(tile, stack));
					}
				}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Interface.png");
		mc.getTextureManager().bindTexture(texture);

		drawTexturedModalRect(175, (float) 18 + (91 * scroll), 194 + (scrollEnabled ? 0 : 12), 0, 12, 15);

		filter.drawTextBox();

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 151, 4210752);
		fontRendererObj.drawString("Interface", 8, 6, 4210752);

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if (filteredStacks.size() > j + i * 9 + 9 * Math.ceil(Math.ceil(((float) filteredStacks.size() / 9 - 6)) * scroll))
				{
					GuiUtil util = new GuiUtil();
					Rectangle r = new Rectangle(left + 7 + j * 18, top + 17 + i * 18, 18, 18);

					if (r.contains(mouseX, mouseY))
						util.drawItemStack(filteredStacks.get((int) (j + i * 9 + 9 * Math.ceil(Math.ceil(((float) filteredStacks.size() / 9 - 6)) * scroll))), 8 + j * 18, 18 + i * 18, itemRender, fontRendererObj, new Color(1, 1, 1, 0.7f).getRGB());
					else
						util.drawItemStack(filteredStacks.get((int) (j + i * 9 + 9 * Math.ceil(Math.ceil(((float) filteredStacks.size() / 9 - 6)) * scroll))), 8 + j * 18, 18 + i * 18, itemRender, fontRendererObj, 0);
				}

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++)
				if (filteredStacks.size() > j + i * 9 + 9 * Math.ceil(Math.ceil(((float) filteredStacks.size() / 9 - 6)) * scroll))
				{
					ItemStack stack = filteredStacks.get((int) (j + i * 9 + 9 * Math.ceil(Math.ceil(((float) filteredStacks.size() / 9 - 6)) * scroll)));

					Rectangle r = new Rectangle(left + 7 + j * 18, top + 18 + i * 18, 18, 18);

					if (r.contains(mouseX, mouseY))
					{
						List<String> info = new ArrayList<>();

						DecimalFormat format = new DecimalFormat("0000");

						info.add(stack.getDisplayName() + String.format(" (#%s)", format.format(Item.REGISTRY.getIDForObject(stack.getItem()))));
						info.add(ChatFormatting.GRAY + String.format("%s %s Stored", NumberFormat.getInstance().format(stack.stackSize), stack.getItem() instanceof ItemBlock ? "Block" + (stack.stackSize > 1 ? "s" : "") : "Item" + (stack.stackSize > 1 ? "s" : "")));
						List<String> tempList = new ArrayList<>();
						stack.getItem().addInformation(stack, Util.player(), tempList, Util.minecraft().gameSettings.advancedItemTooltips);
						info.addAll(tempList.stream().map(s -> ChatFormatting.GRAY + s).collect(Collectors.toList()));
						info.add(ChatFormatting.DARK_GRAY + Item.REGISTRY.getNameForObject(stack.getItem()).toString());
						info.add(String.format("%s%s%s", ChatFormatting.BLUE, ChatFormatting.ITALIC, Util.getMod(stack)));

						drawHoveringText(info, mouseX - left, mouseY - top);
					}
				}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Interface.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

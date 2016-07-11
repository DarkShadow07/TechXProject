package DarkS.TechXProject.node.transport.gui;

import DarkS.TechXProject.api.network.INetworkElement;
import DarkS.TechXProject.client.gui.widget.GuiButtonSlide;
import DarkS.TechXProject.node.transport.TileTransportNode;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.packets.PacketMove;
import DarkS.TechXProject.packets.PacketSetName;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.PartialTicksUtil;
import DarkS.TechXProject.util.Renderer;
import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuiTransportNode extends GuiScreen
{
	private TileTransportNode node;

	private float scale = 1.0f;

	private GuiTextField name;

	private GuiButtonSlide scaleSlider;

	public GuiTransportNode(TileTransportNode node)
	{
		this.node = node;
	}

	@Override
	public void initGui()
	{
		name = new GuiTextField(0, fontRendererObj, 9, 8, 78, 14);
		name.setCanLoseFocus(true);
		name.setFocused(false);

		name.setText(node.getName());

		scaleSlider = new GuiButtonSlide(0, 8, 24, 80, 20, "Scale", 0.0f, 10.0f);

		buttonList.add(scaleSlider);
	}

	@Override
	public void updateScreen()
	{
		name.updateCursorCounter();

		node.setName(name.getText());

		PacketHandler.sendToServer(new PacketSetName(node));

		scale = scaleSlider.getValue();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);

		if (name.textboxKeyTyped(typedChar, keyCode)) return;

		if (keyCode == Keyboard.KEY_E) Util.player().closeScreen();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		ScaledResolution res = new ScaledResolution(Util.minecraft());

		int midX = res.getScaledWidth() / 2, midY = res.getScaledHeight() / 2;

		if (node.hasNetwork())
		{
			List<INetworkElement> validElements = node.getNetwork().getElements().stream().filter(element -> element instanceof TileTransportNode && !element.equals(node)).collect(Collectors.toList());

			for (INetworkElement element : validElements)
			{
				BlockPos pos = ((TileTransportNode) element).getPos();

				int dX = (int) ((pos.getX() - node.getPos().getX()) * scale);
				int dZ = (int) ((pos.getZ() - node.getPos().getZ()) * scale);

				Rectangle r = new Rectangle(midX + dX - 4, midY + dZ - 4, 8, 8);

				if (r.contains(mouseX, mouseY))
				{
					node.setMovePos(pos);
					node.moveEntity(Util.player());

					PacketHandler.sendToServer(new PacketMove(node, Util.player(), pos));

					scaleSlider.playPressSound(mc.getSoundHandler());
				}
			}
		}

		name.mouseClicked(mouseX, mouseY, mouseButton);

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();

		super.drawScreen(mouseX, mouseY, partialTicks);

		name.drawTextBox();

		ScaledResolution res = new ScaledResolution(Util.minecraft());

		int midX = res.getScaledWidth() / 2, midY = res.getScaledHeight() / 2;

		drawCenteredString(fontRendererObj, "Transport Nodes Map", midX, 1, Color.white.getRGB());

		if (!node.hasNetwork()) return;

		List<INetworkElement> validElements = node.getNetwork().getElements().stream().filter(element -> element instanceof TileTransportNode && !element.equals(node)).collect(Collectors.toList());

		for (INetworkElement element : validElements)
		{
			BlockPos pos = ((TileTransportNode) element).getPos();

			int dX = (int) ((pos.getX() - node.getPos().getX()) * scale);
			int dZ = (int) ((pos.getZ() - node.getPos().getZ()) * scale);

			Color color = ((TileTransportNode) element).getColor();

			drawMarker(midX + dX - 4, midY + dZ - 4, mouseX, mouseY, color);
		}

		drawMarker(midX - 4, midY - 4, mouseX, mouseY, node.getColor());

		for (INetworkElement element : validElements)
		{
			BlockPos pos = ((TileTransportNode) element).getPos();

			int dX = (int) ((pos.getX() - node.getPos().getX()) * scale);
			int dZ = (int) ((pos.getZ() - node.getPos().getZ()) * scale);

			List<String> nodeData = new ArrayList<>();
			nodeData.add(((TileTransportNode) element).getName());
			nodeData.add(ChatFormatting.GRAY + String.format("x:%s y:%s z:%s", pos.getX(), pos.getY(), pos.getZ()));

			Rectangle r = new Rectangle(midX + dX - 4, midY + dZ - 4, 8, 8);

			if (r.contains(mouseX, mouseY))
				drawHoveringText(nodeData, mouseX, mouseY);

			List<String> data = new ArrayList<>();
			data.add("You are here - " + node.getName());
			data.add(ChatFormatting.GRAY + String.format("x:%s y:%s z:%s", node.getPos().getX(), node.getPos().getY(), node.getPos().getZ()));

			Rectangle mid = new Rectangle(midX - 4, midY - 4, 8, 8);

			if (mid.contains(mouseX, mouseY))
				drawHoveringText(data, mouseX, mouseY);
		}
	}

	private void drawMarker(int x, int y, int mouseX, int mouseY, Color color)
	{
		GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);

		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icon/marker.png"));

		Rectangle r = new Rectangle(x, y, 8, 8);

		if (r.contains(mouseX, mouseY))
		{
			ScaledResolution res = new ScaledResolution(Util.minecraft());

			int midX = res.getScaledWidth() / 2, midY = res.getScaledHeight() / 2;

			GlStateManager.pushMatrix();

			GlStateManager.translate(x + 4, y + 4, 0);
			GlStateManager.rotate(((node.getWorld().getTotalWorldTime() % 360) + PartialTicksUtil.partialTicks) * 10, 0.0f, 0.0f, 1.0f);

			drawModalRectWithCustomSizedTexture(-4, -4, 0, 0, 8, 8, 8, 8);

			GlStateManager.popMatrix();

			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			GlStateManager.disableTexture2D();

			GlStateManager.glLineWidth(2f);

			Renderer.POS_COLOR.begin(GL11.GL_LINES);

			Renderer.POS_COLOR.addVertex(midX, midY, 0.0d, node.getColor().getRed() / 255.0f, node.getColor().getGreen() / 255.0f, node.getColor().getBlue() / 255.0f, 1.0f);
			Renderer.POS_COLOR.addVertex(x + 4, y + 4, 0.0d, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);

			Renderer.POS_COLOR.draw();

			GlStateManager.enableTexture2D();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		} else
			drawModalRectWithCustomSizedTexture(x, y, 0, 0, 8, 8, 8, 8);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}

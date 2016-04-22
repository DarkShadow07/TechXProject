package DrShadow.TechXProject.machines.teleporter;

import DrShadow.TechXProject.gui.GuiContainerBase;
import DrShadow.TechXProject.items.ItemTeleporterCard;
import DrShadow.TechXProject.packets.PacketHandler;
import DrShadow.TechXProject.packets.PacketTeleportEntity;
import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.io.IOException;

public class GuiTeleporter extends GuiContainerBase
{
	private int top, left;

	private TileTeleporter teleporter;

	public GuiTeleporter(InventoryPlayer inventoryPlayer, TileTeleporter tile)
	{
		super(new ContainerTeleporter(inventoryPlayer, tile));

		teleporter = tile;

		xSize = 176;
		ySize = 247;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		left = (this.width - this.xSize) / 2;
		top = (this.height - this.ySize) / 2;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		for (int i = 0; i < teleporter.getSizeInventory(); i++)
		{
			if (teleporter.getStackInSlot(i) != null)
			{
				Pair<String, Pair<BlockPos, Integer>> teleport = ((ItemTeleporterCard) teleporter.getStackInSlot(i).getItem()).getPos(teleporter.getStackInSlot(i));

				if (!teleport.getRight().getLeft().equals(BlockPos.ORIGIN))
				{
					int x = teleporter.getPos().getX() - teleport.getRight().getLeft().getX();
					int y = teleporter.getPos().getY() - teleport.getRight().getLeft().getY();
					int z = teleporter.getPos().getZ() - teleport.getRight().getLeft().getZ();

					x = x * x;
					y = y * y;
					z = z * z;

					double distance = Math.sqrt(x + y + z);

					if (teleporter.handleDrain(distance, true))
					{
						Rectangle rect = new Rectangle(left + 29, top + 12 + 19 * i, fontRendererObj.getStringWidth(teleport.getLeft()) + 2, 11);

						if (rect.contains(mouseX, mouseY))
						{
							teleporter.handleDrain(distance, false);
							PacketHandler.sendToServer(new PacketTeleportEntity(Util.player(), teleport.getRight().getLeft().add(0, 1, 0), teleport.getRight().getRight()));
							Util.player().closeScreen();
						}
					}
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Teleporter.png");
		mc.getTextureManager().bindTexture(texture);

		for (int i = 0; i < teleporter.getSizeInventory(); i++)
		{
			if (teleporter.getStackInSlot(i) != null)
			{
				Pair<String, Pair<BlockPos, Integer>> teleport = ((ItemTeleporterCard) teleporter.getStackInSlot(i).getItem()).getPos(teleporter.getStackInSlot(i));

				if (!teleport.getRight().getLeft().equals(BlockPos.ORIGIN))
				{
					int x = teleporter.getPos().getX() - teleport.getRight().getLeft().getX();
					int y = teleporter.getPos().getY() - teleport.getRight().getLeft().getY();
					int z = teleporter.getPos().getZ() - teleport.getRight().getLeft().getZ();

					x = x * x;
					y = y * y;
					z = z * z;

					double distance = Math.sqrt(x + y + z);

					Rectangle rect = new Rectangle(left + 29, top + 12 + 19 * i, fontRendererObj.getStringWidth(teleport.getLeft()) + 2, 11);

					if (rect.contains(mouseX, mouseY))
					{
						if (teleporter.handleDrain(distance, true))
						{
							fontRendererObj.drawStringWithShadow(ChatFormatting.GREEN + teleport.getLeft(), 30, 13 + 19 * i, Color.WHITE.getRGB());
						} else
							fontRendererObj.drawStringWithShadow(ChatFormatting.RED + teleport.getLeft(), 30, 13 + 19 * i, Color.WHITE.getRGB());
					} else
					{
						fontRendererObj.drawStringWithShadow(teleport.getLeft(), 30, 13 + 19 * i, Color.WHITE.getRGB());
					}
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/gui/gui_Teleporter.png");
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
	}
}

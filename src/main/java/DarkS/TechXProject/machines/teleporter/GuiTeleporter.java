package DarkS.TechXProject.machines.teleporter;

import DarkS.TechXProject.client.gui.GuiContainerBase;
import DarkS.TechXProject.items.ItemTeleporterCard;
import DarkS.TechXProject.packets.PacketHandler;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Lang;
import DarkS.TechXProject.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
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
		ySize = 231;
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
						Rectangle rect = new Rectangle(left + 7, top + 36 + 11 * i, fontRendererObj.getStringWidth(teleport.getLeft()) + 2, 11);

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

		fontRendererObj.drawString(I18n.format("container.inventory"), 8, 138, 4210752);
		fontRendererObj.drawString(Lang.localize("gui.teleporter"), 8, 6, 4210752);

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

					Rectangle rect = new Rectangle(left + 7, top + 36 + 11 * i, fontRendererObj.getStringWidth(teleport.getLeft()) + 2, 11);

					if (rect.contains(mouseX, mouseY))
					{
						if (teleporter.handleDrain(distance, true))
						{
							fontRendererObj.drawString(ChatFormatting.GREEN + teleport.getLeft(), 8, 37 + 11 * i, Color.white.getRGB());
						} else
							fontRendererObj.drawString(ChatFormatting.RED + teleport.getLeft(), 8, 37 + 11 * i, Color.white.getRGB());
					} else
					{
						fontRendererObj.drawString(teleport.getLeft(), 8, 37 + 11 * i, 4210752);
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

package com.DrShadow.TechXProject.render;

import com.DrShadow.TechXProject.blocks.BlockTeslaTower;
import com.DrShadow.TechXProject.reference.Reference;
import com.DrShadow.TechXProject.render.models.TeslaTowerModel;
import com.DrShadow.TechXProject.tileEntities.TileTeslaTower;
import com.DrShadow.TechXProject.util.ColorUtil;
import com.DrShadow.TechXProject.util.Helper;
import com.DrShadow.TechXProject.util.RenderUtil;
import com.DrShadow.TechXProject.util.Renderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public class TeslaTowerRenderer extends TileEntitySpecialRenderer
{
	private static final String texture = Reference.MOD_ID.toLowerCase() + ":textures/model/TeslaTower.png";

	private TeslaTowerModel model = new TeslaTowerModel();

	float pixel = 1f / 16f;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks, int destroyStage)
	{
		TileTeslaTower tesla = (TileTeslaTower) tile;

		BlockTeslaTower block = (BlockTeslaTower) tile.getBlockType();

		float rotation = 0;

		EnumFacing facing = tile.getWorld().getBlockState(tile.getPos()).getValue(BlockTeslaTower.rotation);
		switch (facing)
		{
			case NORTH:
				rotation = 0;
				break;
			case SOUTH:
				rotation = 2;
				break;
			case EAST:
				rotation = 1;
				break;
			case WEST:
				rotation = 3;
				break;
		}

		GL11.glPushMatrix();

		GL11.glTranslatef((float)x + 0.5f, (float)y + 1.85f, (float)z + 0.5f);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glScalef(1, 1.25f, 1);

		GL11.glRotatef(rotation * 90, 0, 1f, 0);
		RenderUtil.bindTexture(texture);

		model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glTranslatef(-0.5F, -1.5f, -0.5F);
		GL11.glPopMatrix();
	}
}

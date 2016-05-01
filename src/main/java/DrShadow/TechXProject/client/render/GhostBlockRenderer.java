package DrShadow.TechXProject.client.render;

import DrShadow.TechXProject.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GhostBlockRenderer implements IRenderObject
{
	private IBlockState state;
	private BlockPos pos;

	private boolean dead = false;

	public GhostBlockRenderer(IBlockState state, BlockPos pos)
	{
		this.state = state;
		this.pos = pos.add(0, 0, 1);
	}

	@Override
	public void render()
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(pos.getX(), pos.getY(), pos.getZ());

		Util.minecraft().getBlockRendererDispatcher().renderBlockBrightness(state, Util.world().getLightBrightness(pos));

		GlStateManager.popMatrix();
	}

	@Override
	public boolean isDead()
	{
		return dead;
	}

	@Override
	public void setDead()
	{
		dead = true;
	}
}

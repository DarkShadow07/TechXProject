package DrShadow.TechXProject.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Vector2f;

public class PartialTicksUtil
{
	public static float partialTicks = 0;

	public static float[][] calculatePos(float[][] prevPos, float[][] pos)
	{
		if (pos.length != prevPos.length) return null;
		float[][] result = new float[pos.length][0];
		for (int a = 0; a < pos.length; a++) result[a] = calculatePos(prevPos[a], pos[a]);
		return result;
	}

	public static float[] calculatePos(final float[] prevPos, final float[] pos)
	{
		if (pos.length != prevPos.length) return null;
		float[] result = new float[pos.length];
		for (int a = 0; a < pos.length; a++) result[a] = calculatePos(prevPos[a], pos[a]);
		return result;
	}

	public static float calculatePos(final double prevPos, final double pos)
	{
		return (float) (prevPos + (pos - prevPos) * partialTicks);
	}

	public static Vec3d calculatePos(Entity entity)
	{
		return new Vec3d(
				calculatePosX(entity),
				calculatePosY(entity),
				calculatePosZ(entity)
		);
	}

	public static float calculatePosX(Entity entity)
	{
		return calculatePos(entity.prevPosX, entity.posX);
	}

	public static float calculatePosY(Entity entity)
	{
		return calculatePos(entity.prevPosY, entity.posY);
	}

	public static float calculatePosZ(Entity entity)
	{
		return calculatePos(entity.prevPosZ, entity.posZ);
	}

	public static Vector2f calculatePos(Vector2f prevVec, Vector2f vec)
	{
		return new Vector2f(
				calculatePos(prevVec.x, vec.x),
				calculatePos(prevVec.y, vec.y)
		);
	}

	public static Vec3d calculatePos(Vec3d prevPos, Vec3d pos)
	{
		return new Vec3d(
				calculatePos(prevPos.xCoord, pos.xCoord),
				calculatePos(prevPos.yCoord, pos.yCoord),
				calculatePos(prevPos.zCoord, pos.zCoord)
		);
	}

}

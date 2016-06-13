package DarkS.TechXProject.util;

import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class VectorUtil
{
	public static List<Vec3d> dotsOnRay(Vec3d start, Vec3d end, float differenceBetweenDots)
	{
		List<Vec3d> result = new ArrayList<>();

		Vec3d
				difference = start.subtract(end),
				direction = difference.normalize();

		float
				lenght = (float) difference.lengthVector(),
				posMul = differenceBetweenDots;

		result.add(start);
		while (posMul < lenght)
		{
			result.add(multiply(direction, posMul).add(end));
			posMul += differenceBetweenDots;
		}

		result.add(end);

		return result;
	}

	public static Vec3d multiply(Vec3d vec, float value)
	{
		return new Vec3d(vec.xCoord * value, vec.yCoord * value, vec.zCoord * value);
	}

	public static Vec3d multiply(Vec3d vec1, Vec3d vec2)
	{
		return new Vec3d(vec1.xCoord * vec2.xCoord, vec1.yCoord * vec2.yCoord, vec1.zCoord * vec2.zCoord);
	}
}

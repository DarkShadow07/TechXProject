package DrShadow.TechXProject.util;

import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class VectorUtil
{
	public static List<Vec3> dotsOnRay(Vec3 start, Vec3 end, float differenceBetweenDots)
	{
		List<Vec3> result = new ArrayList<>();

		Vec3
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

	public static Vec3 multiply(Vec3 vec, float value)
	{
		return new Vec3(vec.xCoord * value, vec.yCoord * value, vec.zCoord * value);
	}
}

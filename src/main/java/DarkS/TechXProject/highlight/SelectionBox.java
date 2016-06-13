package DarkS.TechXProject.highlight;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SelectionBox
{
	private AxisAlignedBB[] boxes;

	public SelectionBox(AxisAlignedBB... boxes)
	{
		this.boxes = boxes;
	}

	public AxisAlignedBB[] getBoxes()
	{
		return boxes;
	}

	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB box)
	{
		Vec3d hitStart = start.subtract(pos.getX(), pos.getY(), pos.getZ());
		Vec3d hitEnd = end.subtract(pos.getX(), pos.getY(), pos.getZ());

		RayTraceResult result = box.calculateIntercept(hitStart, hitEnd);

		return result == null ? null : new RayTraceResult(result.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), result.sideHit, pos);
	}

	public AxisAlignedBB[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end)
	{
		List<AxisAlignedBB> result = new ArrayList<>();

		for (AxisAlignedBB box : boxes)
		{
			if (rayTrace(pos, start, end, box) == null) continue;

			result.add(box);
		}

		return result.toArray(new AxisAlignedBB[]{});
	}
}

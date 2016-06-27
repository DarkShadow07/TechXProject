package DarkS.TechXProject.highlight;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SelectionBox
{
	private AxisAlignedBB[] boxes;

	private int boxId;

	public SelectionBox(int boxId, AxisAlignedBB... boxes)
	{
		this.boxId = boxId;
		this.boxes = boxes;
	}

	//TODO: move to util?
	public static RayTraceResult rayTraceAll(SelectionBox[] selectionBoxes, BlockPos pos, Vec3d start, Vec3d end)
	{
		for (SelectionBox box : selectionBoxes)
			for (AxisAlignedBB aabb : box.getBoxes())
				if (rayTrace(pos, start, end, aabb) != null)
					return rayTrace(pos, start, end, aabb);

		return null;
	}

	public static RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB box)
	{
		Vec3d hitStart = start.subtract(pos.getX(), pos.getY(), pos.getZ());
		Vec3d hitEnd = end.subtract(pos.getX(), pos.getY(), pos.getZ());

		RayTraceResult result = box.calculateIntercept(hitStart, hitEnd);

		return result == null ? null : new RayTraceResult(result.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), result.sideHit, pos);
	}

	public int getBoxId()
	{
		return boxId;
	}

	public AxisAlignedBB[] getBoxes()
	{
		return boxes;
	}

	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end)
	{
		for (AxisAlignedBB box : getBoxes())
			if (rayTrace(pos, start, end, box) != null)
				return rayTrace(pos, start, end, box);

		return null;
	}

	public SelectionBox rotate(EnumFacing rotation)
	{
		List<AxisAlignedBB> boxes = new ArrayList<>();

		for (AxisAlignedBB box : getBoxes())
			boxes.add(rotate(box, rotation));

		return new SelectionBox(getBoxId(), boxes.toArray(new AxisAlignedBB[0]));
	}

	public AxisAlignedBB rotate(AxisAlignedBB box, EnumFacing rotation)
	{
		double dx1 = 1 - box.minX, dy1 = 1 - box.minY, dz1 = 1 - box.minZ, dx2 = 1 - box.maxX, dy2 = 1 - box.maxY, dz2 = 1 - box.maxZ;

		switch (rotation)
		{
			case NORTH:
				return new AxisAlignedBB(1 - dx1, 1 - dz1, 0 + dy1, 1 - dx2, 1 - dz2, 0 + dy2);
			case SOUTH:
				return new AxisAlignedBB(1 - dx1, 1 - dz1, 1 - dy1, 1 - dx2, 1 - dz2, 1 - dy2);
			case EAST:
				return new AxisAlignedBB(1 - dy1, 1 - dx1, 1 - dz1, 1 - dy2, 1 - dx2, 1 - dz2);
			case WEST:
				return new AxisAlignedBB(0 + dy1, 1 - dx1, 1 - dz1, 0 + dy2, 1 - dx2, 1 - dz2);
			case UP:
				return new AxisAlignedBB(1 - dx1, 1 - dy2, 1 - dz1, 1 - dx2, 1 - dy1, 1 - dz2);
			case DOWN:
				return new AxisAlignedBB(1 - dx1, 0 + dy1, 1 - dz1, 1 - dx2, 0 + dy2, 1 - dz2);
		}
		return box;
	}
}

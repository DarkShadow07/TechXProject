package DarkS.TechXProject.highlight;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public interface IHighlightProvider
{
	SelectionBox[] getSelectionBoxes();

	SelectionBox[] getSelectedBoxes(BlockPos pos, Vec3d start, Vec3d end);

	RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end);
}

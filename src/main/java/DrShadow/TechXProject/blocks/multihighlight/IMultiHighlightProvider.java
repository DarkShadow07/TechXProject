package DrShadow.TechXProject.blocks.multihighlight;

import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public interface IMultiHighlightProvider
{
	List<AxisAlignedBB> getBoxes();

	List<AxisAlignedBB> getActiveBoxes();

	AxisAlignedBB getSelectedBox();
}

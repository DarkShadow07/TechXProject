package DarkS.TechXProject.machines.canvas;

import DarkS.TechXProject.blocks.tile.TileBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TileCanvas extends TileBase
{
	public Map<BlockPos, Color> canvas = new HashMap<>();

	public TileCanvas()
	{

	}

	public void addColor(Color color, float x, float y, float z)
	{
		canvas.put(new BlockPos(x, y, z), color);
	}

	public Color getColor(int x, int y, int z)
	{
		if (canvas.containsKey(new BlockPos(x, y, z)))
			return canvas.get(new BlockPos(x, y, z));

		return Color.white;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		NBTTagList list = new NBTTagList();

		for (Map.Entry<BlockPos, Color> entry : canvas.entrySet())
		{
			NBTTagCompound entryTag = new NBTTagCompound();

			entryTag.setInteger("x", entry.getKey().getX());
			entryTag.setInteger("y", entry.getKey().getY());
			entryTag.setInteger("z", entry.getKey().getZ());

			entryTag.setInteger("color", entry.getValue().hashCode());

			list.appendTag(entryTag);
		}

		tag.setTag("canvas", list);

		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList list = tag.getTagList("canvas", 10);

		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound entryTag = list.getCompoundTagAt(i);

			canvas.put(new BlockPos(entryTag.getInteger("x"), entryTag.getInteger("y"), entryTag.getInteger("z")), new Color(entryTag.getInteger("color")));
		}

		super.readFromNBT(tag);
	}
}

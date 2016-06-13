package DarkS.TechXProject.conduit.item;

import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class ConduitConnection
{
	List<EnumFacing> in = new ArrayList<EnumFacing>();
	List<EnumFacing> out = new ArrayList<EnumFacing>();
	List<EnumFacing> normal = new ArrayList<EnumFacing>();

	public void setNormal(EnumFacing normal)
	{
		in.remove(normal);
		out.remove(normal);

		this.normal.add(normal);
	}

	public void setInput(EnumFacing input)
	{
		out.remove(input);
		normal.remove(input);

		in.add(input);
	}

	public void setOutput(EnumFacing output)
	{
		in.remove(output);
		normal.remove(output);

		out.add(output);
	}

	public boolean isNormal(EnumFacing is)
	{
		return normal.contains(is);
	}

	public boolean isInput(EnumFacing is)
	{
		return in.contains(is);
	}

	public boolean isOutput(EnumFacing is)
	{
		return out.contains(is);
	}

	@Override
	public String toString()
	{
		return String.format("In : %s Out : %s Normal : %s", in.toString(), out.toString(), normal.toString());
	}
}

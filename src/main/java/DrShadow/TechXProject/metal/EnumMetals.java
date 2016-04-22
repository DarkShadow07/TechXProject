package DrShadow.TechXProject.metal;

import net.minecraft.util.IStringSerializable;
import scala.actors.threadpool.Arrays;

import java.util.List;

public enum EnumMetals implements IStringSerializable
{
	COPPER("copper", 2.1f, 0, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET),
	LEAD("lead", 2.6f, 0, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET),
	NICKEL("nickel", 2.9f, 0, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET),
	TIN("tin", 2.4f, 0, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET),
	IRON("iron", 0, 0, EnumMetalType.DUST),
	GOLD("gold", 0, 0, EnumMetalType.DUST),
	COAL("coal", 0, 0, EnumMetalType.DUST),
	DIAMOND("diamond", 0, 0, EnumMetalType.DUST),
	EMERALD("emerald", 0, 0, EnumMetalType.DUST),
	STONE("stone", 0, 0, EnumMetalType.DUST),;

	private String name;
	private float hardness;
	private int level;
	private EnumMetalType[] data;

	EnumMetals(String name, float hardness, int level, EnumMetalType... data)
	{
		this.name = name;
		this.hardness = hardness;
		this.level = level;
		this.data = data;
	}

	public String getName()
	{
		return name;
	}

	public float getHardness()
	{
		return hardness;
	}

	public int getHarvestLevel()
	{
		return level;
	}

	public List<EnumMetalType> getData()
	{
		return Arrays.asList(data);
	}

	public boolean contains(EnumMetalType type)
	{
		return getData().contains(type);
	}

	public boolean registerOreDic()
	{
		return contains(EnumMetalType.VANILLA);
	}

	public boolean registerRecipe()
	{
		return contains(EnumMetalType.RECIPE) || !contains(EnumMetalType.VANILLA);
	}
}

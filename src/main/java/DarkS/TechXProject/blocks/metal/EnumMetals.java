package DarkS.TechXProject.blocks.metal;

import net.minecraft.util.IStringSerializable;
import scala.actors.threadpool.Arrays;

import java.util.List;

public enum EnumMetals implements IStringSerializable
{
	COPPER("copper", 3.1f, 0, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET, EnumMetalType.ORE),
	LEAD("lead", 3.6f, 2, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET, EnumMetalType.ORE),
	NICKEL("nickel", 3.9f, 2, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET, EnumMetalType.ORE),
	TIN("tin", 3.4f, 1, EnumMetalType.BLOCK, EnumMetalType.DUST, EnumMetalType.INGOT, EnumMetalType.NUGGET, EnumMetalType.ORE),
	GRAPHENE("graphene", 9.6f, 3,EnumMetalType.INGOT),
	METEOR("meteor", 18.75f, 3, EnumMetalType.ORE),
	IRON("iron", 0, 0, EnumMetalType.DUST, EnumMetalType.VANILLA),
	GOLD("gold", 0, 0, EnumMetalType.DUST, EnumMetalType.VANILLA),
	COAL("coal", 0, 0, EnumMetalType.DUST, EnumMetalType.VANILLA),
	DIAMOND("diamond", 0, 0, EnumMetalType.DUST, EnumMetalType.VANILLA),
	EMERALD("emerald", 0, 0, EnumMetalType.DUST, EnumMetalType.VANILLA),
	STONE("stone", 0, 0, EnumMetalType.DUST, EnumMetalType.VANILLA),;

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

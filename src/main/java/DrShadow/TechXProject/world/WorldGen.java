package DrShadow.TechXProject.world;

import DrShadow.TechXProject.blocks.metal.BlockMetalBase;
import DrShadow.TechXProject.blocks.metal.EnumMetals;
import DrShadow.TechXProject.init.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.Random;

public class WorldGen implements IWorldGenerator
{
	public static final String GENERATOR = "TechXProjectGeneration";
	public static WorldGen instance = new WorldGen();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateWorld(random, chunkX, chunkZ, world, true);
	}

	public void generateWorld(Random random, int chunkX, int chunkZ, World world, boolean newGen)
	{
		if (!newGen)
		{
			world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
			return;
		}

		if (world.provider.getDimension() == 0)

		{
			addOreSpawn(InitBlocks.metalOre.block.getDefaultState().withProperty(BlockMetalBase.METAL, EnumMetals.COPPER), Blocks.stone.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 8, 10, 35, 75);
			addOreSpawn(InitBlocks.metalOre.block.getDefaultState().withProperty(BlockMetalBase.METAL, EnumMetals.LEAD), Blocks.stone.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 8, 8, 10, 42);
			addOreSpawn(InitBlocks.metalOre.block.getDefaultState().withProperty(BlockMetalBase.METAL, EnumMetals.NICKEL), Blocks.stone.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 4, 4, 5, 20);
			addOreSpawn(InitBlocks.metalOre.block.getDefaultState().withProperty(BlockMetalBase.METAL, EnumMetals.TIN), Blocks.stone.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 8, 8, 18, 60);
		}

	}

	public void addOreSpawn(Block block, Block targetBlock, World world, Random random, int blockXPos, int blockZPos, int veinSize, int chancesToSpawn, int minY, int maxY)
	{
		addOreSpawn(block.getDefaultState(), targetBlock.getDefaultState(), world, random, blockXPos, blockZPos, veinSize, chancesToSpawn, minY, maxY);
	}


	public void addOreSpawn(IBlockState block, IBlockState targetBlock, World world, Random random, int blockXPos, int blockZPos, int veinSize, int chancesToSpawn, int minY, int maxY)
	{
		WorldGenMinable minable = new WorldGenMinable(block, (random.nextInt(veinSize)), state -> state.getBlock() == targetBlock.getBlock());
		for (int i = 0; i < chancesToSpawn; i++)
		{
			int posX = blockXPos + random.nextInt(16);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(16);

			minable.generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}

	@SubscribeEvent
	public void chunkSave(ChunkDataEvent.Save event)
	{
		NBTTagCompound genTag = event.getData().getCompoundTag(GENERATOR);
		if (!genTag.hasKey("generated"))
		{
			genTag.setBoolean("generated", true);
		}
		event.getData().setTag(GENERATOR, genTag);
	}

	@SubscribeEvent
	public void chunkLoad(ChunkDataEvent.Load event)
	{
		int dim = event.getWorld().provider.getDimension();

		boolean regen = false;
		NBTTagCompound tag = (NBTTagCompound) event.getData().getTag(GENERATOR);
		NBTTagList list = null;
		Pair<Integer, Integer> cCoord = Pair.of(event.getChunk().xPosition, event.getChunk().zPosition);

		if (tag != null)
		{
			boolean generated = !tag.hasKey("generated");
			if (generated)
			{
				regen = true;
			}
		} else
		{
			regen = true;
		}

		if (regen)
		{
			ArrayDeque<WorldTickHandler.RetroGenChunkCoord> chunks = WorldTickHandler.chunksToGen.get(dim);

			if (chunks == null)
			{
				WorldTickHandler.chunksToGen.put(dim, new ArrayDeque<>(128));
				chunks = WorldTickHandler.chunksToGen.get(dim);
			}
			if (chunks != null)
			{
				chunks.addLast(new WorldTickHandler.RetroGenChunkCoord(cCoord, list));
				WorldTickHandler.chunksToGen.put(dim, chunks);
			}
		}
	}

}

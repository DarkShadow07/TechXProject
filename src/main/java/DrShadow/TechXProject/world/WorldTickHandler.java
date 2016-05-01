package DrShadow.TechXProject.world;


import DrShadow.TechXProject.reference.Reference;
import DrShadow.TechXProject.util.Logger;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.THashSet;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class WorldTickHandler
{
	public static WorldTickHandler instance = new WorldTickHandler();

	public static TIntObjectHashMap<ArrayDeque<RetroGenChunkCoord>> chunksToGen = new TIntObjectHashMap<>();
	public static TIntObjectHashMap<ArrayDeque<Pair<Integer, Integer>>> chunksToPreGen = new TIntObjectHashMap<>();

	@SubscribeEvent
	public void tickEnd(TickEvent.WorldTickEvent event)
	{
		if (event.side != Side.SERVER)
		{
			return;
		}
		World world = event.world;
		int dim = world.provider.getDimension();

		if (event.phase == TickEvent.Phase.END)
		{
			ArrayDeque<RetroGenChunkCoord> chunks = chunksToGen.get(dim);

			if (chunks != null && !chunks.isEmpty())
			{
				RetroGenChunkCoord r = chunks.pollFirst();
				Pair<Integer, Integer> c = r.coord;
				long worldSeed = world.getSeed();
				Random rand = new Random(worldSeed);
				long xSeed = rand.nextLong() >> 2 + 1L;
				long zSeed = rand.nextLong() >> 2 + 1L;
				rand.setSeed(xSeed * c.getLeft() + zSeed * c.getRight() ^ worldSeed);
				WorldGen.instance.generateWorld(rand, r.coord.getLeft(), r.coord.getRight(), world, false);

				Logger.info(Reference.MOD_NAME + " is Retrogening Chunks! (" + chunks.size() + " left)");

				chunksToGen.put(dim, chunks);
			} else if (chunks != null)
			{
				chunksToGen.remove(dim);
			}
		} else
		{
			Deque<Pair<Integer, Integer>> chunks = chunksToPreGen.get(dim);

			if (chunks != null && !chunks.isEmpty())
			{
				Pair<Integer, Integer> c = chunks.pollFirst();
				world.getChunkFromChunkCoords(c.getLeft(), c.getRight());
			} else if (chunks != null)
			{
				chunksToPreGen.remove(dim);
			}
		}
	}

	public static class RetroGenChunkCoord
	{

		private static final THashSet<String> emptySet = new THashSet<>(0);
		public final Pair<Integer, Integer> coord;
		public final THashSet<String> generatedFeatures;

		public RetroGenChunkCoord(Pair<Integer, Integer> pos, NBTTagList features)
		{

			coord = pos;
			if (features == null)
			{
				generatedFeatures = emptySet;
			} else
			{
				int i = 0;
				int e = features.tagCount();
				generatedFeatures = new THashSet<>(e);
				for (; i < e; ++i)
				{
					generatedFeatures.add(features.getStringTagAt(i));
				}
			}
		}
	}
}

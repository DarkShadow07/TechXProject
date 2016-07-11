package DarkS.TechXProject.world.worldGenerator;

import DarkS.TechXProject.blocks.metal.BlockOreBase;
import DarkS.TechXProject.blocks.metal.EnumMetals;
import DarkS.TechXProject.init.InitBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class MeteorGenerator extends WorldGenerator
{
	private int meteorRad;

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos)
	{
		meteorRad = MathHelper.clamp_int(meteorRad, 8, rand.nextInt(12));

		generateCylinder(worldIn, rand, pos);
		generateExplosion(worldIn, rand, pos);
		generateMeteor(worldIn, rand, pos);

		return true;
	}

	private void generateCylinder(World world, Random rand, BlockPos pos)
	{
		int yLevel = world.getHeight(pos).getY() - 1;

		int r = 18;

		for (int x = pos.getX() - r; x <= pos.getX() + r; x++)
			for (int y = yLevel; y <= (yLevel + r) * 16; y++)
				for (int z = pos.getZ() - r; z <= pos.getZ() + r; z++)
				{
					int dx = pos.getX() - x;
					int dz = pos.getZ() - z;

					dx = dx * dx;
					dz = dz * dz;

					int distance = (int) Math.sqrt(dx + dz);

					if (distance < r)
						world.setBlockToAir(new BlockPos(x, y, z));
					else if (distance <= r && rand.nextInt(100) <= 65)
					{
						if (rand.nextInt(100) <= 1 && !world.isAirBlock(new BlockPos(x, y, z)))
							world.setBlockState(new BlockPos(x, y, z), InitBlocks.meteorStone.block.getDefaultState());
						else if (rand.nextInt(100) <= 5 && !world.isAirBlock(new BlockPos(x, y, z)))
							world.setBlockState(new BlockPos(x, y, z), Blocks.FIRE.getDefaultState());
						else
							world.setBlockToAir(new BlockPos(x, y, z));
					}
				}
	}

	private void generateExplosion(World world, Random rand, BlockPos pos)
	{
		int yLevel = world.getHeight(pos).getY() - 1;

		int r = 18;

		for (int x = pos.getX() - r; x <= pos.getX() + r; x++)
			for (int y = yLevel; y >= yLevel - r; y--)
				for (int z = pos.getZ() - r; z <= pos.getZ() + r; z++)
				{
					int dx = pos.getX() - x;
					int dy = yLevel - y;
					int dz = pos.getZ() - z;

					dx = dx * dx;
					dy = dy * dy;
					dz = dz * dz;

					int distance = (int) Math.sqrt(dx + dy + dz);

					if (distance < r)
						world.setBlockToAir(new BlockPos(x, y, z));
					else if (distance <= r && rand.nextInt(100) <= 65)
					{
						if (rand.nextInt(100) <= 1 && !world.isAirBlock(new BlockPos(x, y, z)))
							world.setBlockState(new BlockPos(x, y, z), InitBlocks.meteorStone.block.getDefaultState());
						else if (rand.nextInt(100) <= 5 && !world.isAirBlock(new BlockPos(x, y, z)))
							world.setBlockState(new BlockPos(x, y, z), Blocks.FIRE.getDefaultState());
						else
							world.setBlockToAir(new BlockPos(x, y, z));
					}
				}
	}

	private void generateMeteor(World world, Random rand, BlockPos pos)
	{
		int yLevel = world.getHeight(pos).getY();

		for (int x = pos.getX() - meteorRad; x <= pos.getX() + meteorRad; x++)
			for (int y = yLevel - meteorRad; y <= yLevel + meteorRad; y++)
				for (int z = pos.getZ() - meteorRad; z <= pos.getZ() + meteorRad; z++)
				{
					int dx = pos.getX() - x;
					int dy = yLevel - y;
					int dz = pos.getZ() - z;

					dx = dx * dx;
					dy = dy * dy;
					dz = dz * dz;

					int distance = (int) Math.sqrt(dx + dy + dz);

					if (distance <= meteorRad * 0.85)
					{
						if (world.isAirBlock(new BlockPos(x, y, z)))
							world.setBlockState(new BlockPos(x, y, z), InitBlocks.meteorStone.block.getDefaultState());
						else if (rand.nextInt(100) <= 90)
							world.setBlockState(new BlockPos(x, y, z), InitBlocks.meteorStone.block.getDefaultState());
					}

					if (rand.nextInt(100) <= 3 && distance < meteorRad * 0.75)
						world.setBlockState(new BlockPos(x, y, z), InitBlocks.metalOre.block.getDefaultState().withProperty(BlockOreBase.METAL, EnumMetals.METEOR));
				}
	}
}

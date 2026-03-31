package aromara.common.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import thaumcraft.common.world.biomes.BiomeHandler;

public class WorldGenVishroomHuge implements IWorldGenerator {

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world,
            IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        int x = (chunkX * 16) + rand.nextInt(16);
        int z = (chunkZ * 16) + rand.nextInt(16);
        int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();

        BlockPos pos = new BlockPos(x, y, z);

        if (world.provider.getDimension() == 0 && world.getBiome(pos).equals(BiomeHandler.MAGICAL_FOREST)) {
            this.placeVishroomHuge(world, pos, world.rand);
        }
    }

    public boolean placeVishroomHuge(World worldIn, BlockPos pos, Random rand)
    {

        if (rand.nextInt(80) != 0)
            return false;

        WorldGenerator worldgenerator = new GenVishroomHuge();

        return worldgenerator.generate(worldIn, rand, pos);
    }

}

package aromara.common.worldgen;

import java.util.Random;

import aromara.common.objects.TCABlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
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
            this.generateOverworld(world, pos, world.rand);
        }
    }

    public boolean generateOverworld(World worldIn, BlockPos pos, Random rand)
    {
        if (rand.nextInt(40) != 0)
            return false;

        //stem => 2-3 blocks wide
        //cap => always 3 blocks wide

        int height = 2 + rand.nextInt(1);
        BlockPos actual = pos;

        IBlockState set = TCABlocks.vishroom_block_stem.getDefaultState();

        space_check:
            for (int x = -1; x < 1; x++) {
                for (int y = -height; y < height; y++) {
                    for (int z = -1; z < 1; z++) {
                        if (!worldIn.getBlockState(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z)).getBlock().isReplaceable(worldIn, pos))
                            return false;
                    }
                }
            }

        stem:
            for (int y = -height; y < height; y++) {
                worldIn.setBlockState(new BlockPos(pos.getX(), pos.getY() + y, pos.getZ()), set);
            }

        actual = new BlockPos(actual.getX(), actual.getY() + height, actual.getZ());

        middle_layer:
            for (int x = -1; x < 1; x++) {
                for (int z = -1; z < 1; z++) {

                    if (z == 0 && x == 0) {
                        continue;
                    }

                    worldIn.setBlockState(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z), set);
                }
            }

        actual = new BlockPos(actual.getX(), actual.getY() + 1, actual.getZ());

        top_layer:
            worldIn.setBlockState(actual, set);
        for (int i = 0; i < 4; i++) {
            worldIn.setBlockState(actual.offset(EnumFacing.byIndex(i)), set);
        }

        return true;
    }

}

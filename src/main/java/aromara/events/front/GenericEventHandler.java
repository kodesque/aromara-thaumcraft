package aromara.events.front;

import aromara.common.objects.TCABlocks;
import aromara.common.tiles.TileServoscrivener;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.tiles.essentia.TileJarFillable;

@Mod.EventBusSubscriber
public class GenericEventHandler {

    @SubscribeEvent
    public static void jarClear(PlayerInteractEvent.RightClickBlock event) {

        BlockPos pos = event.getPos();
        IBlockState state = event.getWorld().getBlockState(pos);

        int radius = 10;

        if (state.getBlock().equals(BlocksTC.jarNormal)) {

            TileJarFillable jar = (TileJarFillable)event.getWorld().getTileEntity(pos);

            Aspect aspect = jar.getAspects().getAspects()[0];
            int amount = jar.getAspects().getAmount(aspect);

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {

                        BlockPos postable = pos.add(x, y, z);

                        IBlockState statetable = event.getWorld().getBlockState(postable);

                        if (statetable.getBlock().equals(TCABlocks.servoscrivener)) {
                            TileServoscrivener tile = (TileServoscrivener)event.getWorld().getTileEntity(postable);

                            int actual = tile.tryAddAspect(aspect, amount);

                            if (actual == 0) {
                                jar.takeFromContainer(aspect, actual);
                            }

                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(
                new ResourceLocation("aromara", "blocks/brainvoid")
                );
    }

    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {

        System.out.println(event.getEntityPlayer().getHeldItemMainhand().getTagCompound());

    }

}

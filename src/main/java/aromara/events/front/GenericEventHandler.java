package aromara.events.front;

import aromara.common.objects.TCABlocks;
import aromara.common.tiles.TileServoscrivener;
import aromara.common.tiles.TileServoscrivener.BlockPosExact;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.blocks.essentia.BlockJar;
import thaumcraft.common.tiles.essentia.TileJarFillable;

@Mod.EventBusSubscriber
public class GenericEventHandler {

    @SubscribeEvent
    public static void jarClear(PlayerInteractEvent.RightClickBlock event) {

        BlockPos pos = event.getPos();
        IBlockState state = event.getWorld().getBlockState(pos);

        int radius = 10;

        if (state.getBlock() instanceof BlockJar &&
                event.getWorld().getTileEntity(pos) instanceof TileJarFillable &&
                event.getEntityPlayer().isSneaking() &&
                event.getEntityPlayer().getHeldItemMainhand().isEmpty()) {

            TileJarFillable jar = (TileJarFillable)event.getWorld().getTileEntity(pos);

            if (jar.getAspects() == null)
                return;

            Aspect aspect = jar.aspect;
            int amount = jar.amount;

            for (BlockPosExact bpe : TileServoscrivener.scriveners) {
                BlockPos posActual = bpe.getPos();
                if (event.getWorld().isBlockLoaded(posActual)) {
                    if (pos.getDistance(posActual.getX(), posActual.getY(), posActual.getZ()) <= radius) {

                        TileServoscrivener tile = (TileServoscrivener)event.getWorld().getTileEntity(posActual);

                        jar.takeFromContainer(aspect, tile.addAspectSmart(aspect, amount));

                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void removeScrivener(BreakEvent event) {
        if (event.getState().getBlock().equals(TCABlocks.servoscrivener)) {
            TileServoscrivener.scriveners.remove(new BlockPosExact(event.getPos(), event.getWorld()));
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

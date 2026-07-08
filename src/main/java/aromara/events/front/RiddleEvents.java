package aromara.events.front;

import aromara.common.objects.TCABlocks;
import aromara.common.tiles.TileServoscrivener;
import aromara.common.tiles.TileServoscrivener.BlockPosExact;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.blocks.essentia.BlockJar;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumcraft.common.tiles.essentia.TileJarFillable;

@Mod.EventBusSubscriber
public class RiddleEvents {

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

                        if (tile.canAddAspect(aspect)) {

                            tile.addAspectSmart(aspect, amount);
                            jar.takeFromContainer(aspect, amount);

                            if (event.getWorld().isRemote) {
                                FXDispatcher.INSTANCE.burst(event.getPos().getX(), event.getPos().getY() + 0.44999998807907104, event.getPos().getZ(), 1.0f);
                            }

                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void debugAspects(PlayerInteractEvent.RightClickBlock event) {

        BlockPos pos = event.getPos();
        IBlockState state = event.getWorld().getBlockState(pos);

        if (state.getBlock().equals(TCABlocks.servoscrivener)) {

            if (event.getWorld().getTileEntity(pos) == null) return;

            TileServoscrivener tile = (TileServoscrivener)event.getWorld().getTileEntity(pos);

            System.out.println(tile.required == null);
            System.out.println(tile.stored == null);
            if (tile.stored != null) {
                System.out.println(tile.stored.visSize());
            }

            if (tile.required != null) {
                for (int i = 0; i < tile.required.size(); i++) {
                    System.out.println(tile.required.getAspects()[i].getName());
                    System.out.println(tile.stored.visSize());
                }
            }

        }
    }

}

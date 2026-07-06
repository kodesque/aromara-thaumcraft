package aromara.events.front;

import aromara.common.objects.TCABlocks;
import aromara.common.tiles.TileServoscrivener;
import aromara.common.tiles.TileServoscrivener.BlockPosExact;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ScrivenerEvents {

    @SubscribeEvent
    public static void removeScrivener(BreakEvent event) {
        if (event.getState().getBlock().equals(TCABlocks.servoscrivener)) {
            TileServoscrivener.scriveners.remove(new BlockPosExact(event.getPos(), event.getWorld()));
        }
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void addScrivenerPlace(BlockEvent.PlaceEvent event) {
        if (event.getState().getBlock().equals(TCABlocks.servoscrivener)) {
            TileServoscrivener.conductCheck(new BlockPosExact(event.getPos(), event.getWorld()));
        }
    }

    @SubscribeEvent
    public static void addScrivenerClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().getBlockState(event.getPos()).getBlock().equals(TCABlocks.servoscrivener)) {
            TileServoscrivener.conductCheck(new BlockPosExact(event.getPos(), event.getWorld()));
        }
    }

}

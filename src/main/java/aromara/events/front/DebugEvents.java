package aromara.events.front;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DebugEvents {

    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {

        System.out.println(event.getEntityPlayer().getHeldItemMainhand().getTagCompound());

    }

}

package aromara.events.front;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.items.tools.ItemThaumometer;

public class GenericEventHandler {

    @SubscribeEvent
    public void GlyphBreak (PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {

        }
    }

}

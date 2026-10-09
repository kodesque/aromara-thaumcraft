package aromara.events.handlers;

import aromara.events.ImbuedToolArmorEvents;
import aromara.events.ModifiedPortalEvents;
import aromara.events.OtherEvents;
import aromara.events.RiddleEvents;
import aromara.events.ScrivenerEvents;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModEventHandler {

    @SubscribeEvent
    public static void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        ImbuedToolArmorEvents.breakToolShimmer(event);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        ImbuedToolArmorEvents.hurtCinder(event);
        ImbuedToolArmorEvents.hurtVishroom(event);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        ModifiedPortalEvents.idleModifiedPortal(event);
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        ModifiedPortalEvents.converseItem(event);
    }

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        ModifiedPortalEvents.dropServoscrivener(event);
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        OtherEvents.crucibleTransform(event);

        RiddleEvents.jarClear(event);
        RiddleEvents.debugAspects(event);

        ScrivenerEvents.addScrivenerClick(event);
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.PlaceEvent event) {
        ScrivenerEvents.addScrivenerPlace(event);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        ScrivenerEvents.removeScrivener(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onTooltipRender(ItemTooltipEvent event) {
        OtherEvents.renderImbuedWith(event);
    }

}

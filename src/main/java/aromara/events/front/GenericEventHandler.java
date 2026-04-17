package aromara.events.front;

import aromara.util.NBTManager;
import aromara.util.NBTManager.TypeAugment;
import aromara.util.NBTManager.EnumGeneralNames;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.items.tools.ItemThaumometer;
import thecodex6824.thaumicaugmentation.api.TAItems;

public class GenericEventHandler {

    @SubscribeEvent
    public void addMemory (PlayerInteractEvent.EntityInteract event) {
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {
            if (event.getEntity() instanceof EntityItem) {
                EntityItem entity = (EntityItem)event.getEntity();
                ItemStack stack = entity.getItem();

                if (stack.getItem().equals(TAItems.ELDRITCH_LOCK_KEY)) {
                    NBTManager.apply(stack, new ValuePair<>(TypeAugment.MAIN, "bro"));
                }
            }
        }
    }

}

package aromara.events.front;

import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.EnumValueNames;
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
                    NBTManager.apply(stack, EnumGroups.MEMORY, EnumValueNames.MAIN);

                    //of course, there should be a value to apply
                }
            }
        }
    }

}

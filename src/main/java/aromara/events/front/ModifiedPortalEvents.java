package aromara.events.front;

import aromara.common.entities.EntityItemComponent;
import aromara.common.objects.TCABlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.entities.monster.cult.EntityCultistPortalLesser;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.EntityUtils;

@Mod.EventBusSubscriber
public class ModifiedPortalEvents {

    @SubscribeEvent
    public static void replaceComponents(EntityJoinWorldEvent event) {

        if (!(event.getEntity() instanceof EntityItem)) return;
        if (event.getEntity() instanceof EntityItemComponent) return;

        EntityItem old = (EntityItem) event.getEntity();

        Item olditem = old.getItem().getItem();

        if (!olditem.equals(ItemsTC.brain) && !olditem.equals(ItemsTC.scribingTools) && !olditem.equals(Items.ENDER_PEARL)) return;

        EntityItemComponent replacement =
                new EntityItemComponent(
                        old.world,
                        old.posX,
                        old.posY,
                        old.posZ,
                        old.getItem()
                        );

        replacement.motionX = old.motionX;
        replacement.motionY = old.motionY;
        replacement.motionZ = old.motionZ;

        replacement.setDefaultPickupDelay();

        event.setCanceled(true);

        old.world.spawnEntity(replacement);
    }

    @SubscribeEvent
    public static void idleModifiedPortal(LivingUpdateEvent event) {

        if (!event.getEntity().world.isRemote) return;

        Entity entity = event.getEntity();
        World w = entity.getEntityWorld();

        float h = w.rand.nextFloat() * 0.33f;

        AxisAlignedBB box = entity.getEntityBoundingBox();
        BlockPos c = new BlockPos(box.getCenter());

        if (entity instanceof EntityCultistPortalLesser && isModified(entity)) {

            if (!event.getEntity().world.isRemote) {

                FXDispatcher.INSTANCE.spark(c.getX() + w.rand.nextFloat(), box.minY + w.rand.nextInt(3), c.getZ() + w.rand.nextFloat(), 3.0f + h * 6.0f, 0.65f + w.rand.nextFloat() * 0.1f, 1.0f, 1.0f, 0.8f);

            } else {
                if (w.getWorldTime() % 40 == 0) {
                    w.playSound(
                            null,
                            entity.getPosition(),
                            SoundsTC.jacobs,
                            SoundCategory.HOSTILE,
                            1F,
                            1F
                            );
                }
            }
        }
    }

    @SubscribeEvent
    public static void dropServoscrivener(LivingDeathEvent event) {

        if (event.getEntity().world.isRemote) return;

        Entity entity = event.getEntity();

        if (entity instanceof EntityCultistPortalLesser && isModified(entity)) {
            EntityUtils.entityDropSpecialItem(entity, new ItemStack(TCABlocks.servoscrivener, 1), 1);

            entity.playSound(SoundsTC.egscreech, 1F, 1F);

            event.getEntity().getEntityWorld().playSound(
                    null,
                    entity.getPosition(),
                    SoundsTC.egscreech,
                    SoundCategory.HOSTILE,
                    1F,
                    1F
                    );
        }

    }

    public static boolean isModified(Entity portal) {
        NBTTagCompound nbt = portal.getEntityData();

        for (int i = 0; i < 3; i++) {
            if (!nbt.hasKey(String.valueOf(i)))
                return false;
        }

        return true;
    }

}

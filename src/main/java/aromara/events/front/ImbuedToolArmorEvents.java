package aromara.events.front;

import java.util.List;

import aromara.common.items.ItemRedolentBundle;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ImbuedToolArmorEvents {

    /* SHIMMERLEAF */

    @SubscribeEvent
    public static void breakToolShimmer(PlayerDestroyItemEvent event) {

        EntityPlayer player = event.getEntityPlayer();
        ItemStack destroyed = event.getOriginal();

        if (destroyed.isEmpty())
            return;

        if (!NBTManager.has(destroyed,
                new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, ItemRedolentBundle.plants[1].getRegistryName().toString())))
            return;

        if (player.getFoodStats().getFoodLevel() <= 0)
            return;

        Item item = destroyed.getItem();

        if (!(item instanceof ItemTool
                || item instanceof ItemSword
                || item instanceof ItemHoe))
            return;

        player.getFoodStats().addStats(-1, 0.0F);

        ItemStack restored = destroyed.copy();
        restored.setCount(1);
        restored.setItemDamage(restored.getMaxDamage() - 1);

        if (event.getHand() == EnumHand.MAIN_HAND) {
            player.setHeldItem(EnumHand.MAIN_HAND, restored);
        } else if (event.getHand() == EnumHand.OFF_HAND) {
            player.setHeldItem(EnumHand.OFF_HAND, restored);
        }
    }

    /* CINDERPEARL */

    @SubscribeEvent
    public static void hurtCinder(LivingHurtEvent event) {

        if (!(event.getEntityLiving() instanceof EntityPlayer))
            return;

        if (!event.getSource().isFireDamage())
            return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        int count = 0;

        for (ItemStack stack : player.getArmorInventoryList()) {
            if (!stack.isEmpty() && NBTManager.has(stack,
                    new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, ItemRedolentBundle.plants[0].getRegistryName().toString()))) {
                count++;
            }
        }

        if (count == 0)
            return;

        AxisAlignedBB area = player.getEntityBoundingBox().grow(10.0D);

        List<EntityMob> mobs = player.world.getEntitiesWithinAABB(EntityMob.class, area);

        for (EntityMob mob : mobs) {
            mob.setFire(count);
        }
    }

    /* VISHROOM */

    @SubscribeEvent
    public static void hurtVishroom(LivingHurtEvent event) {

        if (!(event.getEntityLiving() instanceof EntityPlayer))
            return;

        if (!(event.getSource().getTrueSource() instanceof EntityLivingBase))
            return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        int count = 0;

        for (ItemStack stack : player.getArmorInventoryList()) {
            if (!stack.isEmpty() && NBTManager.has(stack,
                    new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, ItemRedolentBundle.plants[2]))) {
                count++;
            }
        }

        if (count == 0)
            return;

        if (player.getRNG().nextFloat() < 0.05F * count) {
            event.setCanceled(true);
        }
    }

}

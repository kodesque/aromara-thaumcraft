package aromara.events.front;

import java.util.List;

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
    public static void hurtShimmer(LivingHurtEvent event) {

        if (!(event.getEntityLiving() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        int count = 0;

        for (ItemStack stack : player.getArmorInventoryList()) {
            if (!stack.isEmpty() && NBTManager.has(stack,
                    new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, "flower.shimmerleaf"))) {
                count++;
            }
        }

        if (count == 0)
            return;

        event.setAmount((float) (event.getAmount() * Math.pow(0.5D, count)));

        double chance = Math.pow(0.5D, count);

        for (ItemStack stack : player.getArmorInventoryList()) {
            if (!stack.isEmpty()
                    && NBTManager.has(stack,
                            new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, "flower.shimmerleaf"))
                    && player.getRNG().nextDouble() < chance) {

                stack.damageItem(1, player);
            }
        }
    }

    @SubscribeEvent
    public static void breakToolShimmer(PlayerDestroyItemEvent event) {

        EntityPlayer player = event.getEntityPlayer();
        ItemStack destroyed = event.getOriginal();

        if (destroyed.isEmpty())
            return;

        if (!NBTManager.has(destroyed,
                new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, "flower.shimmerleaf")))
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
                    new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, "flower.cinderpearl"))) {
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

    public static void explodeCinder(World world, Entity source, BlockPos pos, int power) {

        Explosion explosion = new Explosion(
                world,
                source,
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D,
                power,
                false,
                false);

        explosion.doExplosionA();

        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(
                source,
                new AxisAlignedBB(pos).grow(power * 2));

        for (Entity entity : entities) {
            if (entity instanceof EntityPlayer) {
                continue;
            }

            double distance = entity.getDistance(
                    pos.getX() + 0.5D,
                    pos.getY() + 0.5D,
                    pos.getZ() + 0.5D);

            if (distance > power * 2) {
                continue;
            }

            double strength = 1.0D - distance / (power * 2);

            entity.attackEntityFrom(DamageSource.causeExplosionDamage(explosion), (float) (power * strength * 4.0F));

            entity.motionX += (entity.posX - (pos.getX() + 0.5D)) * 0.2D * strength;
            entity.motionY += 0.3D * strength;
            entity.motionZ += (entity.posZ - (pos.getZ() + 0.5D)) * 0.2D * strength;
        }

        world.playSound(
                null,
                pos,
                SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.BLOCKS,
                1.0F,
                1.0F);

        world.spawnParticle(
                EnumParticleTypes.EXPLOSION_HUGE,
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D,
                0,
                0,
                0);
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
                    new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, "flower.vishroom"))) {
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

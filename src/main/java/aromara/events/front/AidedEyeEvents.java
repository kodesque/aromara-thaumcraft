package aromara.events.front;

import java.util.List;

import aromara.common.items.ItemAugmentEye;
import aromara.common.objects.TCAItems;
import aromara.root.Main;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.items.tools.ItemThaumometer;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.EntityUtils;
import thecodex6824.thaumicaugmentation.api.impetus.ImpetusAPI;
import thecodex6824.thaumicaugmentation.common.item.ItemEldritchLockKey;

@Mod.EventBusSubscriber
public class AidedEyeEvents {

    public static final int gazeCost = 20;
    public static final int memoryCost = 10;

    @SubscribeEvent
    public static void useSpecialAbility (PlayerInteractEvent.RightClickItem event) {

        if (event.getEntityPlayer().isSneaking()) {
            if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {
                if (NBTManager.has(event.getEntityPlayer().getHeldItemMainhand(), EnumGroups.AUGMENT)) {

                    Entity target = EntityUtils.getPointedEntity(event.getWorld(), event.getEntityPlayer(), 1.0, 15.0, 0.0f, true);

                    if (target != null && target instanceof EntityLiving) {

                        if (ItemAugmentEye.findAndExtract(event.getEntityPlayer(), gazeCost)) {

                            EntityLiving entity = (EntityLiving)target;
                            entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1));

                            event.getEntityPlayer().playSound(
                                    SoundsTC.shock,
                                    2.0F,
                                    1.0F
                                    );

                            event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("message" + "." + Main.MODID + "." + "gaze")
                                    .setStyle(new Style()
                                            .setItalic(true)
                                            .setColor(TextFormatting.DARK_PURPLE)),
                                    true);

                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void addMemory (PlayerInteractEvent.RightClickItem event) {

        if (event.getWorld().isRemote) return;

        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {
            if (NBTManager.has(event.getEntityPlayer().getHeldItemMainhand(), EnumGroups.AUGMENT)) {

                Entity target = EntityUtils.getPointedEntity(event.getWorld(), event.getEntityPlayer(), 1.0, 9.0, 0.0f, true);

                if (target != null) {
                    if (target instanceof EntityItem) {

                        EntityItem entity = (EntityItem)target;
                        ItemStack stack = entity.getItem();

                        if (stack.getItem() instanceof ItemEldritchLockKey) {

                            if (ItemAugmentEye.findAndExtract(event.getEntityPlayer(), memoryCost)) {

                                Integer main = event.getWorld().rand.nextInt(7);
                                Integer sub = event.getWorld().rand.nextInt(3);

                                NBTManager.applySoft(stack,
                                        new ValuePair<>(EnumGroups.MEMORY, EnumGroups.ValuesMemory.MAIN, main),
                                        new ValuePair<>(EnumGroups.MEMORY, EnumGroups.ValuesMemory.SUB, sub));

                                stack.setTranslatableName(new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "key" + "." + stack.getMetadata()).getFormattedText());

                                entity.setItem(stack);

                                event.getWorld().playSound(
                                        null,
                                        event.getPos(),
                                        SoundsTC.wand,
                                        SoundCategory.BLOCKS,
                                        2.0F,
                                        1.0F
                                        );

                                event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("message" + "." + Main.MODID + "." + "reveal")
                                        .setStyle(new Style()
                                                .setItalic(true)
                                                .setColor(TextFormatting.DARK_PURPLE)),
                                        true);

                                for (int i = 0; i < 4; ++i) {
                                    ImpetusAPI.createImpetusParticles(event.getWorld(), event.getEntityPlayer().getPositionVector().add(0, event.getEntityPlayer().height / 2, 0), new Vec3d(entity.getPosition()));
                                }

                                event.setCanceled(true);
                            }
                        }

                    }


                }
            }
        }
    }

    @SubscribeEvent
    public static void renderAugmentTooltip (ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ItemThaumometer && NBTManager.has(stack, EnumGroups.AUGMENT)) {
            tips.add(1,
                    new TextComponentString((new ItemStack(TCAItems.augment_eye)).getDisplayName())
                    .setStyle(new Style()
                            .setColor(TextFormatting.DARK_PURPLE)
                            .setItalic(true))
                    .getFormattedText());
        }
    }

    @SubscribeEvent
    public static void renderMemoryTooltip (ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ItemEldritchLockKey) {

            if (NBTManager.has(stack, EnumGroups.MEMORY)) {
                tips.add(1, new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "memory" + "." + NBTManager.get(stack, EnumGroups.MEMORY, EnumGroups.ValuesMemory.MAIN) + "." + NBTManager.get(stack, EnumGroups.MEMORY, EnumGroups.ValuesMemory.SUB)).getFormattedText());
                tips.remove(2);
            }
        } else if (stack.getItem().equals(TCAItems.seal_printed)) {
            if (NBTManager.has(stack, EnumGroups.MEMORY)) {
                tips.add(1, new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "memory" + "." + NBTManager.get(stack, EnumGroups.MEMORY, EnumGroups.ValuesMemory.MAIN) + "." + NBTManager.get(stack, EnumGroups.MEMORY, EnumGroups.ValuesMemory.SUB)).getFormattedText());
            } else {
                tips.add(1, new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "memory" + "." + "null").getFormattedText());
            }
        }
    }

}

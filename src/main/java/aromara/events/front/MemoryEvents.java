package aromara.events.front;

import java.util.List;

import aromara.common.objects.TCAItems;
import aromara.root.Main;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.items.tools.ItemThaumometer;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.EntityUtils;
import thecodex6824.thaumicaugmentation.common.item.ItemEldritchLockKey;

@Mod.EventBusSubscriber
public class MemoryEvents {

    @SubscribeEvent
    public static void addMemory (PlayerInteractEvent.RightClickItem event) {

        if (event.getWorld().isRemote) return;

        if (event.getEntityPlayer().isSneaking()) {
            if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {
                if (NBTManager.has(event.getEntityPlayer().getHeldItemMainhand(), EnumGroups.AUGMENT)) {

                    Entity target = EntityUtils.getPointedEntity(event.getWorld(), event.getEntityPlayer(), 1.0, 9.0, 0.0f, true);

                    if (target != null) {
                        if (target instanceof EntityItem) {

                            EntityItem entity = (EntityItem)target;
                            ItemStack stack = entity.getItem();

                            if (stack.getItem() instanceof ItemEldritchLockKey) {

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

                                event.setCanceled(true);
                            }

                        }
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public static void renderMemoryTooltip (ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ItemEldritchLockKey || stack.getItem().equals(TCAItems.seal_printed)) {

            if (NBTManager.has(stack, EnumGroups.MEMORY)) {
                tips.add(1, new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "memory" + "." + NBTManager.get(stack, EnumGroups.MEMORY, EnumGroups.ValuesMemory.MAIN) + "." + NBTManager.get(stack, EnumGroups.MEMORY, EnumGroups.ValuesMemory.SUB)).getFormattedText());
            }

            if (stack.getItem() instanceof ItemEldritchLockKey) {
                tips.remove(1);
            }
        }
    }



}

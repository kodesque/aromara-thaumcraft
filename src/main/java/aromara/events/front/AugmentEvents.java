package aromara.events.front;

import java.util.List;

import aromara.common.objects.TCAItems;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.items.tools.ItemThaumometer;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.EntityUtils;
import thecodex6824.thaumicaugmentation.api.TAItems;
import thecodex6824.thaumicaugmentation.api.impetus.CapabilityImpetusStorage;
import thecodex6824.thaumicaugmentation.api.impetus.IImpetusStorage;
import thecodex6824.thaumicaugmentation.api.impetus.ImpetusAPI;

@Mod.EventBusSubscriber
public class AugmentEvents {

    @SubscribeEvent
    public static void useSpecialAbility (PlayerInteractEvent.RightClickItem event) {

        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemThaumometer) {
            if (NBTManager.has(event.getEntityPlayer().getHeldItemMainhand(), EnumGroups.AUGMENT)) {

                Entity target = EntityUtils.getPointedEntity(event.getWorld(), event.getEntityPlayer(), 1.0, 10.0, 0.0f, true);

                if (target != null && target instanceof EntityLiving) {

                    InventoryPlayer inv = event.getEntityPlayer().inventory;

                    for (int i = 0; i < inv.getSizeInventory(); i++) {
                        ItemStack stack = inv.getStackInSlot(i);
                        if (stack.getItem().equals(TAItems.GAUNTLET)) {
                            if (stack.hasCapability(CapabilityImpetusStorage.IMPETUS_STORAGE, null)) {
                                IImpetusStorage storage = stack.getCapability(CapabilityImpetusStorage.IMPETUS_STORAGE, null);

                                if (storage.canExtract()) {

                                    ImpetusAPI.tryExtractFully(storage, 5);

                                    EntityLiving entity = (EntityLiving)target;
                                    entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1));

                                    event.getEntityPlayer().playSound(
                                            SoundsTC.shock,
                                            2.0F,
                                            1.0F
                                            );
                                }
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
                    new TextComponentString((new ItemStack(TCAItems.augment)).getDisplayName())
                    .setStyle(new Style()
                            .setColor(TextFormatting.DARK_PURPLE)
                            .setItalic(true))
                    .getFormattedText());
        }
    }

}

package aromara.common.items;

import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thecodex6824.thaumicaugmentation.api.augment.CapabilityAugmentableItem;
import thecodex6824.thaumicaugmentation.api.impetus.CapabilityImpetusStorage;
import thecodex6824.thaumicaugmentation.api.impetus.IImpetusStorage;
import thecodex6824.thaumicaugmentation.api.impetus.ImpetusAPI;
import thecodex6824.thaumicaugmentation.common.item.ItemRiftEnergyCasterAugment;
import thecodex6824.thaumicaugmentation.common.item.ItemTieredCasterGauntlet;

public class ItemAugmentEye extends ItemTCABase {

    public ItemAugmentEye(String name, String... variants) {
        super(name, variants);

        this.setMaxStackSize(1);
    }

    public static boolean findAndExtract(EntityPlayer player, int amount) {

        InventoryPlayer inv = player.inventory;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem() instanceof ItemTieredCasterGauntlet) {

                ItemStack[] augments = stack.getCapability(CapabilityAugmentableItem.AUGMENTABLE_ITEM, null).getAllAugments();

                for (ItemStack element : augments) {
                    if (element.getItem() instanceof ItemRiftEnergyCasterAugment) {

                        IImpetusStorage storage = element.getCapability(CapabilityImpetusStorage.IMPETUS_STORAGE, null);
                        if (ImpetusAPI.tryExtractFully(storage, amount))
                            return true;
                    }
                }
            }
        }
        return false;
    }

}

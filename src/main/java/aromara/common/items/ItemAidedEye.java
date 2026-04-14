package aromara.common.items;

import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import aromara.util.NBTUtil.EnumSubtype;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemAidedEye extends ItemTCABase {

    public ItemAidedEye(String name, String... variants) {
        super(name, variants);

        this.setMaxStackSize(1);
    }

    public static ItemStack applyAugment(ItemStack thaumometer) {

        ItemStack copy = thaumometer.copy();
        NBTTagCompound copyTag = copy.getOrCreateSubCompound(Main.MODID);

        if (hasAugment(thaumometer)) {
            copyTag.setString(EnumSubtype.INTERNAL.get(), TCAItems.augment.getRegistryName().toString());
        }

        return copy;
    }

    public static ItemStack removeAugment(ItemStack thaumometer) {

        ItemStack copy = thaumometer.copy();
        NBTTagCompound copyTag = copy.getOrCreateSubCompound(Main.MODID);

        if (hasAugment(thaumometer)) {
            copyTag.removeTag(EnumSubtype.INTERNAL.get());
        }

        return copy;
    }

    public static boolean hasAugment(ItemStack thaumometer) {

        NBTTagCompound nbt = thaumometer.getSubCompound(Main.MODID);

        return nbt != null
                && nbt.getCompoundTag(EnumSubtype.AUGMENT.get()) != null
                && nbt.getCompoundTag(EnumSubtype.AUGMENT.get()).getString(EnumSubtype.INTERNAL.get())
                .equals(TCAItems.augment.getRegistryName().toString());
    }

}

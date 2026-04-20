package aromara.common.items;

import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemAidedEye extends ItemTCABase {

    public ItemAidedEye(String name, String... variants) {
        super(name, variants);

        this.setMaxStackSize(1);
    }

}

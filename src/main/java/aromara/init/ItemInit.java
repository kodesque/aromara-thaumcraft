package aromara.init;

import aromara.common.items.ItemsTCA;
import aromara.common.templates.ItemTCABase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.ItemTCBase;

public class ItemInit {

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {
        iForgeRegistry.register((ItemsTCA.vishroom_soup = new ItemTCABase("vishroom_soup")));
    }

}

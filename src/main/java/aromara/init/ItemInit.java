package aromara.init;

import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.ItemTCBase;

public class ItemInit {

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {
        iForgeRegistry.register((TCAItems.alchemical_clay = new ItemTCABase("alchemical_clay")));
        iForgeRegistry.register((TCAItems.ceramic_phial = new ItemTCABase("ceramic_phial")));
        iForgeRegistry.register((TCAItems.liquid_tallow = new ItemTCABase("liquid_tallow")));
    }

}

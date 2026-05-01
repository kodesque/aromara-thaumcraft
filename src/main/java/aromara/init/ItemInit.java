package aromara.init;

import aromara.common.items.ItemDebug;
import aromara.common.items.ItemRedolentBundle;
import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(TCAItems.debug = new ItemDebug("debug"));

        iForgeRegistry.register((TCAItems.scent_phial = new ItemTCABase("scent_phial")));
        iForgeRegistry.register((TCAItems.liquid_tallow = new ItemTCABase("liquid_tallow")));

        iForgeRegistry.register((TCAItems.redolent_bundle = new ItemRedolentBundle("redolent_bundle", "raw", "dry")));


    }

}

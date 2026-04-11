package aromara.init;

import aromara.common.items.ItemIcon;
import aromara.common.items.ItemRedolentBundle;
import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(TCAItems.icon = new ItemIcon("icon"));

        iForgeRegistry.register((TCAItems.alchemical_clay = new ItemTCABase("alchemical_clay")));
        iForgeRegistry.register((TCAItems.ceramic_phial = new ItemTCABase("ceramic_phial")));
        iForgeRegistry.register((TCAItems.liquid_tallow = new ItemTCABase("liquid_tallow")));

        iForgeRegistry.register(TCAItems.impetus_resonator = new ItemTCABase("impetus_resonator"));
        iForgeRegistry.register(TCAItems.causality_shackles = new ItemTCABase("causality_shackles"));

        iForgeRegistry.register((TCAItems.redolent_bundle = new ItemRedolentBundle("redolent_bundle", "raw", "dry")));


    }

}

package aromara.init;

import aromara.common.items.ItemDebug;
import aromara.common.items.ItemRedolentBundle;
import aromara.common.items.ItemResearchBrief;
import aromara.common.items.ItemScentPhial;
import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(TCAItems.debug = new ItemDebug("debug"));

        iForgeRegistry.register((TCAItems.scent_phial = new ItemScentPhial("scent_phial")));
        iForgeRegistry.register((TCAItems.heater = new ItemTCABase("heater")));

        iForgeRegistry.register((TCAItems.parchment = new ItemTCABase("parchment")));
        iForgeRegistry.register((TCAItems.research_hint = new ItemTCABase("research_hint")));
        iForgeRegistry.register((TCAItems.research_brief = new ItemResearchBrief("research_brief")));

        iForgeRegistry.register((TCAItems.redolent_bundle = new ItemRedolentBundle("redolent_bundle", "raw", "dry")));


    }

}

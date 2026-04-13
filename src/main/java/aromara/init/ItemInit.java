package aromara.init;

import aromara.common.items.ItemIcon;
import aromara.common.items.ItemRedolentBundle;
import aromara.common.items.ItemTablet;
import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(TCAItems.icon = new ItemIcon("icon"));

        iForgeRegistry.register(TCAItems.causality_shackles = new ItemTCABase("causality_shackles"));
        iForgeRegistry.register(TCAItems.glyph_tablet = new ItemTablet("glyph_tablet"));
        iForgeRegistry.register(TCAItems.perspective = new ItemTCABase("perspective"));

        iForgeRegistry.register(TCAItems.seal_printed = new ItemTCABase("seal_printed", "tablet", "sigil", "tome", "blade"));;

        iForgeRegistry.register(TCAItems.redolent_bundle = new ItemRedolentBundle("redolent_bundle", "raw", "dry"));


    }

}

package aromara.init;

import aromara.common.items.ItemAidedEye;
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
        iForgeRegistry.register(TCAItems.augment = new ItemAidedEye("augment"));

        iForgeRegistry.register(TCAItems.seal_printed = new ItemTCABase("seal_printed", "tablet", "sigil", "tome", "blade"));
        iForgeRegistry.register(TCAItems.pure_shard = new ItemTCABase("pure_shard"));
        iForgeRegistry.register(TCAItems.destabilized_amber = new ItemTCABase("destabilized_amber"));

        iForgeRegistry.register(TCAItems.alchemical_insulator = new ItemTCABase("alchemical_insulator"));
        iForgeRegistry.register(TCAItems.cerebral_pearls = new ItemTCABase("cerebral_pearls"));
        iForgeRegistry.register(TCAItems.gray_matter = new ItemTCABase("gray_matter"));
        iForgeRegistry.register(TCAItems.mind_manufactured = new ItemTCABase("mind_manufactured"));

        iForgeRegistry.register(TCAItems.redolent_bundle = new ItemRedolentBundle("redolent_bundle", "raw", "dry"));


    }

}

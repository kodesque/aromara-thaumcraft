package aromara.common.items;

import aromara.common.objects.TCABlocks;
import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemIcon extends ItemTCABase {

    public ItemIcon(String name, String... variants) {
        super(name, variants);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != Main.TABTCA) return;

        items.clear();

        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.arcane_brazier)));
        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.pressing_stone)));

        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.vishroom_block_cap)));
        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.vishroom_block_stem)));

        items.add(new ItemStack (TCAItems.alchemical_clay));
        items.add(new ItemStack (TCAItems.ceramic_phial));
        items.add(new ItemStack(TCAItems.liquid_tallow));

        for (Item plant : ItemRedolentBundle.plants) {
            items.add(ItemRedolentBundle.getBundleDried(ItemRedolentBundle.getBundleFromComponent(plant)));
        }

        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.thaumostatic_supressor)));
        items.add(new ItemStack (TCAItems.impetus_resonator));
        items.add(new ItemStack (TCAItems.causality_shackles));
    }

}

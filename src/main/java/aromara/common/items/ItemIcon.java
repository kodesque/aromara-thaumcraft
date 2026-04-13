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
        for (Item plant : ItemRedolentBundle.plants) {
            items.add(ItemRedolentBundle.getBundleDried(ItemRedolentBundle.getBundleFromComponent(plant)));
        }

        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack (TCAItems.seal_printed);
            stack.setItemDamage(i);
            items.add((stack));
        }

        items.add(new ItemStack (TCAItems.glyph_tablet));
        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.thaumostatic_supressor)));
        items.add(new ItemStack (TCAItems.perspective));
        items.add(new ItemStack (TCAItems.causality_shackles));
    }

}

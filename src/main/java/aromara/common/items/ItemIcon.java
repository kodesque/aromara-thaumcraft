package aromara.common.items;

import aromara.common.objects.TCABlocks;
import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import aromara.init.ItemInit;
import aromara.root.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import thaumcraft.api.blocks.BlocksTC;

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

        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.vishroom_block)));

        items.add(new ItemStack (TCAItems.alchemical_clay));
        items.add(new ItemStack (TCAItems.ceramic_phial));
        items.add(new ItemStack(TCAItems.liquid_tallow));

        items.add(ItemRedolentBundle.getExample(false));
        items.add(ItemRedolentBundle.getExample(true));
    }

}

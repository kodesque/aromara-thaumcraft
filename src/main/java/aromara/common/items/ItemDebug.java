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

public class ItemDebug extends ItemTCABase {

    public ItemDebug(String name, String... variants) {
        super(name, variants);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != Main.TABTCA && tab != CreativeTabs.SEARCH) return;

        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.servoscrivener)));
        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.arcane_brazier)));
        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.candle_vat)));
        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.pressing_stone)));

        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.vishroom_block_cap)));
        items.add(new ItemStack (Item.getItemFromBlock(TCABlocks.vishroom_block_stem)));
        items.add(new ItemStack (TCAItems.parchment));

        for (Item plant : ItemRedolentBundle.plants) {
            items.add(ItemRedolentBundle.getBundleDried(ItemRedolentBundle.getBundleFromComponent(plant)));
        }

        items.add(new ItemStack(TCAItems.heater));
        items.add(new ItemStack (TCAItems.scent_phial));
    }

}

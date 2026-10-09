package aromara.root;

import aromara.common.items.ItemRedolentBundle;
import aromara.common.items.ItemScentPhial;
import aromara.init.TCABlocks;
import aromara.init.TCAItems;
import aromara.util.NBTManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TCACreativeTab extends CreativeTabs {

    public static final String[] RESEARCH = new String[]{"TCA_SCENTBURNING", "TCA_SCENTBOILING"};

    public TCACreativeTab(String label) {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(TCAItems.debug);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> list) {

        list.add(new ItemStack(Item.getItemFromBlock(TCABlocks.servoscrivener)));
        list.add(new ItemStack(Item.getItemFromBlock(TCABlocks.arcane_brazier)));
        list.add(new ItemStack(Item.getItemFromBlock(TCABlocks.candle_vat)));
        list.add(new ItemStack(Item.getItemFromBlock(TCABlocks.pressing_stone)));

        list.add(new ItemStack(Item.getItemFromBlock(TCABlocks.vishroom_block_cap)));
        list.add(new ItemStack(Item.getItemFromBlock(TCABlocks.vishroom_block_stem)));
        list.add(new ItemStack(TCAItems.parchment));

        list.add(new ItemStack(TCAItems.heater));
        list.add(new ItemStack(TCAItems.scent_phial));

        for (Item plant : ItemRedolentBundle.plants) {
            list.add(ItemRedolentBundle.getBundleFromComponent(plant));
        }

        for (Item plant : ItemRedolentBundle.plants) {
            list.add(ItemRedolentBundle.getBundleDried(ItemRedolentBundle.getBundleFromComponent(plant)));
        }

        for (Item plant : ItemRedolentBundle.plants) {
            ItemStack stack = new ItemStack(TCAItems.scent_phial);
            NBTManager.apply(stack, new NBTManager.ValuePair<>(NBTManager.EnumGroups.OIL, NBTManager.EnumGroups.Oil.TYPE, plant.getRegistryName().toString()));
            list.add(stack);
        }

        ItemStack rancid = new ItemStack(TCAItems.scent_phial);
        NBTManager.apply(rancid, new NBTManager.ValuePair<>(NBTManager.EnumGroups.OIL, NBTManager.EnumGroups.Oil.RANCID, true));
        list.add(rancid);

        for (String research : TCACreativeTab.RESEARCH) {
            ItemStack brief = new ItemStack(TCAItems.research_brief);
            NBTManager.apply(brief, new NBTManager.ValuePair<>(NBTManager.EnumGroups.KNOWLEDGE, NBTManager.EnumGroups.Knowledge.NAME, research));
            list.add(brief);
        }
    }
}

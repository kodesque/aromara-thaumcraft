package aromara.common.templates;

import aromara.root.Main;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.IThaumcraftItems;

public class ItemTCABase extends Item implements IThaumcraftItems {

    protected String BASE_NAME;
    protected String[] VARIANTS;
    protected int[] VARIANTS_META;

    public ItemTCABase(String name, String... variants) {

        this.setRegistryName(Main.MODID, name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.TABTCA);
        this.setNoRepair();
        this.setHasSubtypes(variants.length > 1);
        this.BASE_NAME = name;
        if (variants.length == 0) {
            this.VARIANTS = new String[] { name };
        }
        else {
            this.VARIANTS = variants;
        }
        this.VARIANTS_META = new int[this.VARIANTS.length];
        for (int m = 0; m < this.VARIANTS.length; ++m) {
            this.VARIANTS_META[m] = m;
        }
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        if (this.hasSubtypes && itemStack.getMetadata() < this.VARIANTS.length && this.VARIANTS[itemStack.getMetadata()] != this.BASE_NAME)
            return String.format(super.getUnlocalizedNameInefficiently(itemStack) + ".%s", this.VARIANTS[itemStack.getMetadata()]);
        return super.getUnlocalizedNameInefficiently(itemStack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == Main.TABTCA || tab == CreativeTabs.SEARCH) {
            if (!this.getHasSubtypes()) {
                super.getSubItems(tab, items);
            }
            else {
                for (int meta = 0; meta < this.VARIANTS.length; ++meta) {
                    items.add(new ItemStack(this, 1, meta));
                }
            }
        }
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public String[] getVariantNames() {
        return this.VARIANTS;
    }

    @Override
    public int[] getVariantMeta() {
        return this.VARIANTS_META;
    }

    @Override
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }

    @Override
    public ModelResourceLocation getCustomModelResourceLocation(String variant) {
        if (variant.equals(this.BASE_NAME))
            return new ModelResourceLocation("aromara:" + this.BASE_NAME);
        return new ModelResourceLocation("aromara:" + this.BASE_NAME, variant);
    }

}

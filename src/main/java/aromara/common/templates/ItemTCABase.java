package aromara.common.templates;

import aromara.root.Aromara;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemTCBase;

public class ItemTCABase extends ItemTCBase {

    public ItemTCABase(String name, String... variants) {
        super(name, variants);

        this.setRegistryName(name);
        this.setCreativeTab(Aromara.TABTCA);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ModelResourceLocation getCustomModelResourceLocation(String variant) {
        if (variant.equals(this.BASE_NAME))
            return new ModelResourceLocation("aromara:" + this.BASE_NAME);
        return new ModelResourceLocation("aromara:" + this.BASE_NAME, variant);
    }

}

package aromara.common.items;

import java.util.List;

import javax.annotation.Nullable;

import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IWarpingGear;
import thecodex6824.thaumicaugmentation.api.TAMaterials;

public class ItemTablet extends ItemTCABase implements IWarpingGear {

    TextComponentTranslation desc = new TextComponentTranslation("tooltip" + "." + Main.MODID + "." + "glyph_tablet");

    public ItemTablet(String name, String... variants) {
        super(name, variants);

        this.setMaxStackSize(1);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return TAMaterials.RARITY_ELDRITCH;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(this.desc.getFormattedText());
    }

    @Override
    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 5;
    }

}

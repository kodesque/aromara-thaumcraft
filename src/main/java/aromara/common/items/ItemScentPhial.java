package aromara.common.items;

import java.util.List;

import javax.annotation.Nullable;

import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemScentPhial extends ItemTCABase {

    TextComponentTranslation base = new TextComponentTranslation("tooltip." + Main.MODID + ".oil" + ".base");
    TextComponentTranslation rancid = new TextComponentTranslation("tooltip." + Main.MODID + ".oil" + ".rancid");


    public ItemScentPhial(String name, String... variants) {
        super(name, variants);

        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);

        if (nbt != null) {

            for (Item plant : ItemRedolentBundle.plants) {
                if (NBTManager.has(stack, new ValuePair<>( EnumGroups.OIL, EnumGroups.Oil.TYPE, plant.getRegistryName().toString()))) {
                    tooltip.add(TextFormatting.DARK_PURPLE + this.base.getFormattedText() + " " + TextFormatting.DARK_PURPLE + new ItemStack(plant).getDisplayName());
                    return;
                }
            }

            tooltip.add(TextFormatting.DARK_PURPLE + this.rancid.getFormattedText());
        }
    }
}

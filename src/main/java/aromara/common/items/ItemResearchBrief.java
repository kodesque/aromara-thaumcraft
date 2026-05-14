package aromara.common.items;

import java.util.List;

import javax.annotation.Nullable;

import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemResearchBrief extends ItemTCABase {

    public ItemResearchBrief(String name, String... variants) {
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

            String name = NBTManager.get(stack, EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.NAME);

            if (NBTManager.has(stack, new ValuePair<>(EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.DONE, true))) {
                tooltip.add(TextFormatting.GOLD + new TextComponentTranslation("research" + "." + name + "." + "title").getFormattedText());
                tooltip.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC + new TextComponentTranslation("research" + "." + name + "." + "title" + "." + "subtitle").getFormattedText());
            } else {
                tooltip.add(TextFormatting.GOLD + new TextComponentTranslation("research" + "." + name + "-" + "H" + "." + "title").getFormattedText());
            }
        }
    }

}

package aromara.common.items;

import java.util.List;

import javax.annotation.Nullable;

import aromara.common.objects.TCAItems;
import aromara.common.templates.ItemTCABase;
import aromara.root.Main;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumFunc;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.blocks.BlocksTC;

public class ItemRedolentBundle extends ItemTCABase{

    public static String key = "redolent_bundle";

    public static Item[] plants = {
            Item.getItemFromBlock(BlocksTC.cinderpearl),
            Item.getItemFromBlock(BlocksTC.shimmerleaf),
            Item.getItemFromBlock(BlocksTC.vishroom)
    };

    public ItemRedolentBundle(String name, String... variants) {
        super(name, variants);
        this.setMaxStackSize(1);
    }

    public static ItemStack getBundleFromComponent(@Nullable Item item) {

        ItemStack stack = new ItemStack(TCAItems.redolent_bundle);

        if (item == null) return null;

        return NBTManager.mutatePairs(stack, EnumFunc.APPLY, new ValuePair<>(EnumGroups.SCENTCRUDE, EnumGroups.Crude.MAIN, item.getRegistryName().toString()));
    }

    public static ItemStack getComponentFromBundle(ItemStack stack) {

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);

        if (nbt != null) {

            for (Item plant : plants) {
                if (NBTManager.get(stack, EnumGroups.SCENTCRUDE, EnumGroups.Crude.MAIN).equals(plant.getRegistryName().toString()))
                    return new ItemStack(plant);
            }
        }

        return new ItemStack(Items.AIR);
    }

    public static ItemStack getBundleDried(ItemStack stack) {

        return NBTManager.mutateMeta(stack, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);

        if (nbt != null) {

            for (Item plant : plants) {
                if (NBTManager.get(stack, EnumGroups.SCENTCRUDE, EnumGroups.Crude.MAIN).equals(plant.getRegistryName().toString())) {
                    tooltip.add(TextFormatting.DARK_PURPLE + new ItemStack(plant).getDisplayName());
                }
            }

        }
    }

}

package aromara.common.recipes;

import aromara.common.objects.TCAItems;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeToolArmorImbue extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public static final String id = "tool_armor_imbue";

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {

        ItemStack tool = ItemStack.EMPTY;
        ItemStack oil = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (this.isToolArmor(stack)) {
                if (!tool.isEmpty())
                    return false;
                tool = stack;
                continue;
            }

            if (stack.getItem() == TCAItems.scent_phial && NBTManager.has(stack, EnumGroups.OIL)) {
                if (!oil.isEmpty())
                    return false;
                oil = stack;
                continue;
            }

            return false;
        }

        return !tool.isEmpty() && !oil.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {

        ItemStack tool = ItemStack.EMPTY;
        ItemStack oil = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (this.isToolArmor(stack)) {
                tool = stack;
            } else if (stack.getItem() == TCAItems.scent_phial) {
                oil = stack;
            }
        }

        if (tool.isEmpty() || oil.isEmpty())
            return ItemStack.EMPTY;

        ItemStack result = tool.copy();

        String type = NBTManager.get(oil, EnumGroups.OIL, EnumGroups.Oil.TYPE);

        if (type != null) {
            NBTManager.apply(result,
                    new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, type));
        } else if (NBTManager.has(oil,
                new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.RANCID, true))) {
            NBTManager.apply(result,
                    new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.RANCID, true));
        }

        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public boolean isToolArmor(ItemStack stack) {

        Item item = stack.getItem();

        return item instanceof ItemTool
                || item instanceof ItemSword
                || item instanceof ItemHoe
                || item instanceof ItemArmor;
    }
}

package aromara.common.recipes;

import aromara.common.items.ItemAidedEye;
import aromara.common.objects.TCAItems;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumFunc;
import aromara.util.NBTManager.EnumGeneralNames;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.tools.ItemThaumometer;

public class RecipeAugmentAdd extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public static String id = "augment_add";

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {

        boolean hasThaumometer = false;
        boolean hasAugment = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {

                if (stack.getItem() instanceof ItemThaumometer) {
                    if (hasThaumometer || NBTManager.has(stack, EnumGroups.AUGMENT))
                        return false;
                    hasThaumometer = true;

                } else if (stack.getItem().equals(TCAItems.augment)) {
                    if (hasAugment)
                        return false;
                    hasAugment = true;

                } else
                    return false;
            }
        }

        return hasThaumometer && hasAugment;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {

        ItemStack thaumometer = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemThaumometer) {
                    thaumometer = stack;
                    break;
                }
            }
        }

        return NBTManager.mutatePairs(thaumometer, EnumFunc.APPLY, new ValuePair<>(EnumGroups.AUGMENT, EnumGroups.ValuesAugment.MAIN, true));
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if ((stack.getItem() instanceof ItemThaumometer && NBTManager.has(stack, EnumGroups.AUGMENT)) || stack.getItem().equals(TCAItems.augment)) {
                result.set(i, ItemStack.EMPTY);
            }
        }

        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ItemsTC.thaumometer);
    }

}

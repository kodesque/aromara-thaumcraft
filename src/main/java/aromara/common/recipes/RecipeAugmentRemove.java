package aromara.common.recipes;

import aromara.common.items.ItemAugmentEye;
import aromara.common.objects.TCAItems;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.common.items.tools.ItemThaumometer;

public class RecipeAugmentRemove extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public static String id = "augment_remove";

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {

        boolean hasThaumometer = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                if (!hasThaumometer && stack.getItem() instanceof ItemThaumometer && NBTManager.has(stack, EnumGroups.AUGMENT)) {
                    hasThaumometer = true;
                } else {
                    hasThaumometer = false;
                    break;
                }
            }
        }

        return hasThaumometer;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {

        return new ItemStack(TCAItems.augment_eye);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof ItemThaumometer && NBTManager.has(stack, EnumGroups.AUGMENT)) {
                result.set(i, NBTManager.mutateGroup(stack, EnumGroups.AUGMENT));
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
        return new ItemStack(TCAItems.augment_eye);
    }

}

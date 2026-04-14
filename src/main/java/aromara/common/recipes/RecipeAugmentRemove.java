package aromara.common.recipes;

import aromara.common.items.ItemAidedEye;
import aromara.common.objects.TCAItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.common.items.tools.ItemThaumometer;

public class RecipeAugmentRemove extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {

        int search = 0;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                if (search != 1 && stack.getItem() instanceof ItemThaumometer && ItemAidedEye.hasAugment(stack)) {
                    search = 1;
                } else {
                    search = 0;
                    break;
                }
            }
        }

        return search == 1;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {

        return new ItemStack(TCAItems.augment);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof ItemThaumometer) {
                result.set(i, ItemAidedEye.removeAugment(stack));
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
        return new ItemStack(TCAItems.augment);
    }

}

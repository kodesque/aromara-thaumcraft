package aromara.common.recipes;

import aromara.common.items.ItemAidedEye;
import aromara.common.objects.TCAItems;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.EnumGeneralNames;
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

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {

        int search = 0;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                if (search != 1 && stack.getItem() instanceof ItemThaumometer && !NBTManager.has(stack, EnumGroups.AUGMENT)) {
                    search = 1;
                } else if (search != 2 && stack.getItem().equals(TCAItems.augment)) {
                    search = 2;
                } else {
                    search = 0;
                    break;
                }
            }
        }

        return search == 2;
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

        return NBTManager.apply(thaumometer, EnumGroups.AUGMENT, new ValuePair(EnumGeneralNames.MAIN.getName(), TCAItems.augment.getRegistryName().toString()));
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof ItemThaumometer || stack.getItem().equals(TCAItems.augment)) {
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

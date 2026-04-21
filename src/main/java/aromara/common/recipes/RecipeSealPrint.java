package aromara.common.recipes;

import aromara.common.objects.TCAItems;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumFunc;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.common.golems.seals.ItemSealPlacer;
import thaumcraft.common.items.tools.ItemThaumometer;
import thecodex6824.thaumicaugmentation.common.item.ItemEldritchLockKey;

public class RecipeSealPrint extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public static String id = "seal_print";

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {

        boolean hasSeal = false;
        boolean hasKey = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {

                if (stack.getItem() instanceof ItemEldritchLockKey) {
                    if (!hasKey && NBTManager.has(stack, EnumGroups.MEMORY)) {
                        hasKey = true;
                    }

                } else if (stack.getItem() instanceof ItemSealPlacer && stack.getMetadata() == 0) {
                    if (!hasSeal) {
                        hasSeal = true;
                    }

                } else
                    return false;
            }
        }

        return hasSeal && hasKey;

    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {

        ItemStack seal = null;
        ItemStack key = null;
        ItemStack result = new ItemStack(TCAItems.seal_printed);

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemSealPlacer && stack.getMetadata() == 0) {
                    seal = stack;
                } else if (stack.getItem() instanceof ItemEldritchLockKey) {
                    key = stack;
                }
            }
        }

        if (seal != null && key != null) {
            result = NBTManager.mutateMeta(NBTManager.mutatePairs(result, EnumFunc.APPLY,
                    new ValuePair<>(EnumGroups.MEMORY, EnumGroups.ValuesMemory.MAIN,
                            NBTManager.get(key, EnumGroups.MEMORY, EnumGroups.ValuesMemory.MAIN)),
                    new ValuePair<>(EnumGroups.MEMORY, EnumGroups.ValuesMemory.SUB,
                            NBTManager.get(key, EnumGroups.MEMORY, EnumGroups.ValuesMemory.SUB))), key.getMetadata());
        }

        return result;

    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {

                if (stack.getItem() instanceof ItemEldritchLockKey) {
                    result.set(i, stack.copy());
                }
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
        return new ItemStack(TCAItems.seal_printed);
    }

}

package aromara.init;

import aromara.common.items.ItemRedolentBundle;
import aromara.common.objects.TCAItems;
import aromara.root.Main;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.config.ConfigRecipes;

public class RecipeInit {

    public static void initializeArcaneRecipes(IForgeRegistry<IRecipe> iForgeRegistry) {

        ResourceLocation baseGroup = new ResourceLocation(Main.MODID, "base");

        for (int i = 0; i < ItemRedolentBundle.plants.length; i++) {
            ThaumcraftApi.addArcaneCraftingRecipe(
                    new ResourceLocation("aromara:redolent_bundle" + "-" + i),
                    new ShapelessArcaneRecipe(
                            baseGroup,
                            "TCA_CRUDESCENTS",
                            25,
                            new AspectList().add(Aspect.ORDER, 2).add(Aspect.EARTH, 2),
                            ItemRedolentBundle.getBundleFromComponent(ItemRedolentBundle.plants[i]),
                            new Object[] {
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(ItemsTC.fabric)
                            }
                            )
                    );
        }




    }
}

package aromara.init;

import aromara.common.items.ItemRedolentBundle;
import aromara.common.objects.TCABlocks;
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
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.items.ItemsTC;

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
                            new AspectList().add(Aspect.ORDER, 1).add(Aspect.EARTH, 1),
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

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:arcane_brazier"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_CRUDESCENTS",
                        50,
                        new AspectList().add(Aspect.FIRE, 2),
                        Item.getItemFromBlock(TCABlocks.arcane_brazier),
                        "PPP",
                        "OSO",
                        "OLO",
                        'P',
                        new ItemStack(Blocks.STONE_SLAB),
                        'O',
                        new ItemStack(Blocks.OBSIDIAN),
                        'S',
                        "stone",
                        'L',
                        new ItemStack(BlocksTC.logGreatwood)
                        )
                );

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:pressing_stone"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_CRUDESCENTS",
                        50,
                        null,
                        new ItemStack(Item.getItemFromBlock(TCABlocks.pressing_stone)),
                        "SRS",
                        "PNP",
                        "SRS",
                        'P',
                        "plateIron",
                        'N',
                        "nitor",
                        'S',
                        new ItemStack(Item.getItemFromBlock(BlocksTC.stoneArcane)),
                        'R',
                        new ItemStack(ItemsTC.visResonator)
                        )
                );



    }
}

package aromara.init;

import aromara.common.items.ItemRedolentBundle;
import aromara.common.objects.TCABlocks;
import aromara.common.objects.TCAItems;
import aromara.root.Main;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

    public static void initWorkbench(IForgeRegistry<IRecipe> iForgeRegistry) {

        ResourceLocation baseGroup = new ResourceLocation(Main.MODID, "base");

        for (int i = 0; i < ItemRedolentBundle.plants.length; i++) {
            ThaumcraftApi.addArcaneCraftingRecipe(
                    new ResourceLocation("aromara:redolent_bundle" + "-" + i),
                    new ShapelessArcaneRecipe(
                            baseGroup,
                            "TCA_SCENTBURNING",
                            25,
                            new AspectList().add(Aspect.ORDER, 1).add(Aspect.EARTH, 1),
                            ItemRedolentBundle.getBundleFromComponent(ItemRedolentBundle.plants[i]),
                            new Object[] {
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(ItemRedolentBundle.plants[i]),
                                    new ItemStack(Items.STRING)
                            }
                            )
                    );
        }

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:arcane_brazier"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_SCENTBURNING",
                        50,
                        new AspectList().add(Aspect.FIRE, 2),
                        TCABlocks.arcane_brazier,
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
                        "TCA_SCENTBURNING",
                        50,
                        null,
                        new ItemStack(TCABlocks.pressing_stone),
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

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:scent_phial"),
                new ShapelessArcaneRecipe(
                        baseGroup,
                        "TCA_SCENTBOILING",
                        10,
                        null,
                        new ItemStack(TCAItems.scent_phial, 2),
                        new Object[] {
                                new ItemStack(ItemsTC.tallow),
                                new ItemStack(Items.CLAY_BALL),
                                new ItemStack(ItemsTC.salisMundus),
                                new ItemStack(ItemsTC.phial)
                        }
                        )
                );

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:heater"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_SCENTBOILING",
                        50,
                        null,
                        new ItemStack(TCAItems.heater),
                        " C ",
                        "PHP",
                        " E ",
                        'P',
                        "plateIron",
                        'C',
                        new ItemStack(BlocksTC.crystalFire),
                        'E',
                        new ItemStack(ItemsTC.nuggets, 1, 10),
                        'H',
                        new ItemStack(Blocks.TRIPWIRE_HOOK)
                        )
                );

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:parchment"),
                new ShapelessArcaneRecipe(
                        baseGroup,
                        "TCA_RESEARCHER",
                        10,
                        new AspectList().add(Aspect.ENTROPY, 2),
                        new ItemStack(TCAItems.parchment, 1),
                        new Object[] {
                                new ItemStack(Items.SUGAR),
                                new ItemStack(ItemsTC.nuggets, 1, 5),
                                new ItemStack(ItemsTC.nuggets, 1, 9),
                                new ItemStack(Items.LEATHER)
                        }
                        )
                );
    }

    public static void initInfusion(IForgeRegistry<IRecipe> iForgeRegistry) {

    }

    public static void initCrucible(IForgeRegistry<IRecipe> iForgeRegistry) {

    }
}

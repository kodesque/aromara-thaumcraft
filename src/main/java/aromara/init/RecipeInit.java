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
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.casters.ItemFocus;
import thaumcraft.common.items.casters.foci.FocusEffectRift;
import thecodex6824.thaumicaugmentation.api.TABlocks;
import thecodex6824.thaumicaugmentation.api.TAItems;
import thecodex6824.thaumicaugmentation.common.item.foci.FocusEffectVoidShield;
import thecodex6824.thaumicaugmentation.common.item.foci.FocusEffectWard;

public class RecipeInit {

    public static void initializeArcaneRecipes(IForgeRegistry<IRecipe> iForgeRegistry) {

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
                        "TCA_SCENTBURNING",
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

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:pressing_stone"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_SCENTBURNING",
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

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:perspective"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_SCENTBURNING",
                        25,
                        new AspectList().add(Aspect.AIR, 2),
                        new ItemStack(TCAItems.augment),
                        "TRT",
                        "QIQ",
                        "TET",
                        'T',
                        "plateThaumium",
                        'R',
                        new ItemStack(ItemsTC.morphicResonator),
                        'Q',
                        new ItemStack(ItemsTC.quicksilver),
                        'I',
                        new ItemStack(TAItems.MATERIAL, 1, 5),
                        'E',
                        new ItemStack(Items.ENDER_EYE)
                        )
                );

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:glyph_tablet"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_SCENTBURNING",
                        25,
                        new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5),
                        new ItemStack(TCAItems.glyph_tablet),
                        "PPP",
                        "PBP",
                        "PPP",
                        'P',
                        new ItemStack(TCAItems.seal_printed),
                        'B',
                        new ItemStack(ItemsTC.brain)
                        )
                );

        ItemStack focus_1 = new ItemStack(TAItems.FOCUS_ANCIENT);
        FocusPackage fp_1 = new FocusPackage();
        FocusEffectWard ward = new FocusEffectWard();
        fp_1.addNode(ward);
        ItemFocus.setPackage(focus_1, fp_1);

        ItemStack focus_2 = new ItemStack(TAItems.FOCUS_ANCIENT);
        FocusPackage fp_2 = new FocusPackage();
        FocusEffectRift rift = new FocusEffectRift();
        fp_2.addNode(rift);
        ItemFocus.setPackage(focus_2, fp_2);

        ItemStack focus_3 = new ItemStack(TAItems.FOCUS_ANCIENT);
        FocusPackage fp_3 = new FocusPackage();
        FocusEffectVoidShield shield = new FocusEffectVoidShield();
        fp_3.addNode(shield);
        ItemFocus.setPackage(focus_3, fp_3);

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("aromara:causality_shackles"),
                new InfusionRecipe("TCA_SCENTBURNING",
                        new ItemStack (TCAItems.causality_shackles),
                        8,
                        new AspectList().add(Aspect.ENERGY, 32).add(Aspect.TRAP, 32).add(Aspect.DESIRE, 32),
                        new ItemStack(ItemsTC.baubles, 1, 3),
                        focus_1,
                        new ItemStack(ItemsTC.curio, 1, 2),
                        new ItemStack(Item.getItemFromBlock(TABlocks.BARS)),
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        focus_2,
                        new ItemStack(ItemsTC.curio, 1, 2),
                        new ItemStack(Item.getItemFromBlock(TABlocks.BARS)),
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        focus_3,
                        new ItemStack(ItemsTC.curio, 1, 2),
                        new ItemStack(Item.getItemFromBlock(TABlocks.BARS)),
                        Ingredient.fromItem(ItemsTC.primordialPearl))
                );

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("aromara:thaumostatic_supressor"),
                new InfusionRecipe("TCA_SCENTBURNING",
                        new ItemStack (Item.getItemFromBlock(TCABlocks.thaumostatic_supressor)),
                        5,
                        new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.MECHANISM, 16),
                        new ItemStack(Item.getItemFromBlock(BlocksTC.brainBox)),
                        new ItemStack(BlocksTC.stoneEldritchTile),
                        new ItemStack(TAItems.MATERIAL, 1, 5),
                        new ItemStack(ItemsTC.mechanismComplex),
                        new ItemStack (Items.NETHER_STAR),
                        new ItemStack (ItemsTC.alumentum),
                        new ItemStack(BlocksTC.stoneEldritchTile),
                        new ItemStack(TAItems.MATERIAL, 1, 5),
                        new ItemStack(ItemsTC.mechanismComplex),
                        new ItemStack (ItemsTC.alumentum),
                        new ItemStack(BlocksTC.metalBlockVoid),
                        new ItemStack(TAItems.MATERIAL, 1, 5),
                        new ItemStack (ItemsTC.alumentum))
                );;



    }
}

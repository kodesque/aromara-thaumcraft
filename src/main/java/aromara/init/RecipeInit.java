package aromara.init;

import aromara.common.items.ItemRedolentBundle;
import aromara.common.objects.TCABlocks;
import aromara.common.objects.TCAItems;
import aromara.root.Main;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.crafting.CrucibleRecipe;
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
                        "TCA_LENSAUGMENTATION",
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

        ItemStack[] sealStacks = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(TCAItems.seal_printed);
            stack.setItemDamage(i);
            sealStacks[i] = stack;
        }

        ThaumcraftApi.addInfusionCraftingRecipe(
                new ResourceLocation("aromara:glyph_tablet"),
                new InfusionRecipe("TCA_ANCIENTS",
                        new ItemStack (TCAItems.glyph_tablet),
                        8,
                        new AspectList().add(Aspect.MIND, 50).add(Aspect.FIRE, 50).add(Aspect.ELDRITCH, 100),
                        new ItemStack(Item.getItemFromBlock(BlocksTC.jarBrain)),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.curio, 1, 1),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.scribingTools),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.curio, 1, 1),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.scribingTools),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.curio, 1, 1),
                        Ingredient.fromStacks(sealStacks),
                        new ItemStack(ItemsTC.scribingTools))
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
                new InfusionRecipe("TCA_VOIDNODE",
                        new ItemStack (TCAItems.causality_shackles),
                        6,
                        new AspectList().add(Aspect.ENERGY, 32).add(Aspect.TRAP, 32).add(Aspect.DESIRE, 32),
                        new ItemStack(ItemsTC.baubles, 1, 3),
                        focus_1,
                        new ItemStack(ItemsTC.curio, 1, 2),
                        new ItemStack(TABlocks.BARS),
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        focus_2,
                        new ItemStack(ItemsTC.curio, 1, 2),
                        new ItemStack(TABlocks.BARS),
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        focus_3,
                        new ItemStack(ItemsTC.curio, 1, 2),
                        new ItemStack(TABlocks.BARS),
                        Ingredient.fromItem(ItemsTC.primordialPearl))
                );

        ThaumcraftApi.addArcaneCraftingRecipe(
                new ResourceLocation("aromara:thaumostatic_supressor"),
                new ShapedArcaneRecipe(
                        baseGroup,
                        "TCA_VOIDNODE",
                        100,
                        new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.ORDER, 5),
                        new ItemStack(TCABlocks.thaumostatic_supressor),
                        "ASA",
                        "MBM",
                        "EVE",
                        'S',
                        new ItemStack(Items.NETHER_STAR),
                        'A',
                        new ItemStack(ItemsTC.alumentum),
                        'M',
                        new ItemStack(ItemsTC.mechanismComplex),
                        'E',
                        new ItemStack(BlocksTC.stoneEldritchTile),
                        'V',
                        new ItemStack(BlocksTC.metalBlockVoid),
                        'B',
                        new ItemStack(BlocksTC.brainBox)
                        )
                );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("aromara:pure_shard"),
                new CrucibleRecipe("TCA_ANCIENTS",
                        new ItemStack(TCAItems.pure_shard, 2),
                        new ItemStack(TABlocks.STRANGE_CRYSTAL),
                        new AspectList().merge(Aspect.ENTROPY, 30).merge(Aspect.EXCHANGE, 25).merge(Aspect.VOID, 30))
                );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("aromara:pure_shard-1"),
                new CrucibleRecipe("TCA_ANCIENTS",
                        new ItemStack(TCAItems.pure_shard, 2),
                        new ItemStack(TCAItems.pure_shard),
                        new AspectList().merge(Aspect.AURA, 5).merge(Aspect.CRYSTAL, 5).merge(Aspect.COLD, 5))
                );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("aromara:pale_stone"),
                new CrucibleRecipe("TCA_ANCIENTS",
                        new ItemStack(TCABlocks.pale_stone),
                        new ItemStack(TCAItems.pure_shard),
                        new AspectList().merge(Aspect.CRYSTAL, 10).merge(Aspect.METAL, 15).merge(Aspect.ALCHEMY, 10))
                );

        ThaumcraftApi.addCrucibleRecipe(
                new ResourceLocation("aromara:destabilized_amber"),
                new CrucibleRecipe("TCA_ANCIENTS",
                        new ItemStack(TCAItems.destabilized_amber),
                        "gemAmber",
                        new AspectList().merge(Aspect.AVERSION, 10).merge(Aspect.LIGHT, 30).merge(Aspect.FIRE, 30))
                );
    }
}

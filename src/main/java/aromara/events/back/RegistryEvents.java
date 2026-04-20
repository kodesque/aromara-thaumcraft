package aromara.events.back;

import aromara.common.recipes.RecipeAugmentAdd;
import aromara.common.recipes.RecipeAugmentRemove;
import aromara.common.recipes.RecipeSealPrint;
import aromara.init.BlockInit;
import aromara.init.ItemInit;
import aromara.init.RecipeInit;
import aromara.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryEvents {

    //SOURCE: thaumcraft.Registrar

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //        ConfigBlocks.initBlocks(event.getRegistry());
        //        ConfigBlocks.initTileEntities();
        //        ConfigBlocks.initMisc();
        BlockInit.initBlocks();
    }

    //    @SideOnly(Side.CLIENT)
    //    @SubscribeEvent(priority = EventPriority.LOWEST)
    //    public static void registerBlocksClient(RegistryEvent.Register<Block> event) {
    //        ProxyBlock.setupBlocksClient(event.getRegistry());
    //    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //        ConfigItems.preInitSeals();
        ItemInit.initItems(event.getRegistry());
        //        ConfigItems.initMisc();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        RecipeInit.initializeArcaneRecipes(event.getRegistry());

        event.getRegistry().register(new RecipeAugmentAdd().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentAdd.id)));
        event.getRegistry().register(new RecipeAugmentRemove().setRegistryName(new ResourceLocation(Main.MODID, RecipeAugmentRemove.id)));
        event.getRegistry().register(new RecipeSealPrint().setRegistryName(new ResourceLocation(Main.MODID, RecipeSealPrint.id)));
    }

    //    @SideOnly(Side.CLIENT)
    //    @SubscribeEvent(priority = EventPriority.LOWEST)
    //    public static void registerItemsClient(RegistryEvent.Register<Item> event) {
    //        ConfigItems.initModelsAndVariants();
    //    }

    //    @SubscribeEvent
    //    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
    //        ConfigEntities.initEntities(event.getRegistry());
    //    }

    //    @SubscribeEvent
    //    public static void registerVanillaRecipes(RegistryEvent.Register<IRecipe> event) {
    //        ModConfig.modCompatibility();
    //        ConfigRecipes.initializeNormalRecipes(event.getRegistry());
    //        ConfigRecipes.initializeArcaneRecipes(event.getRegistry());
    //        ConfigRecipes.initializeInfusionRecipes();
    //        ConfigRecipes.initializeAlchemyRecipes();
    //        ConfigRecipes.initializeCompoundRecipes();
    //    }

    //    @SubscribeEvent
    //    public static void registerPotions(RegistryEvent.Register<Potion> event) {
    //        PotionFluxTaint.instance = new PotionFluxTaint(true, 6697847).setRegistryName("fluxTaint");
    //        PotionVisExhaust.instance = new PotionVisExhaust(true, 6702199).setRegistryName("visExhaust");
    //        PotionInfectiousVisExhaust.instance = new PotionInfectiousVisExhaust(true, 6706551).setRegistryName("infectiousVisExhaust");
    //        PotionUnnaturalHunger.instance = new PotionUnnaturalHunger(true, 4482611).setRegistryName("unnaturalHunger");
    //        PotionWarpWard.instance = new PotionWarpWard(false, 14742263).setRegistryName("warpWard");
    //        PotionDeathGaze.instance = new PotionDeathGaze(true, 6702131).setRegistryName("deathGaze");
    //        PotionBlurredVision.instance = new PotionBlurredVision(true, 8421504).setRegistryName("blurredVision");
    //        PotionSunScorned.instance = new PotionSunScorned(true, 16308330).setRegistryName("sunScorned");
    //        PotionThaumarhia.instance = new PotionThaumarhia(true, 6702199).setRegistryName("thaumarhia");
    //        event.getRegistry().register(PotionFluxTaint.instance);
    //        event.getRegistry().register(PotionVisExhaust.instance);
    //        event.getRegistry().register(PotionInfectiousVisExhaust.instance);
    //        event.getRegistry().register(PotionUnnaturalHunger.instance);
    //        event.getRegistry().register(PotionWarpWard.instance);
    //        event.getRegistry().register(PotionDeathGaze.instance);
    //        event.getRegistry().register(PotionBlurredVision.instance);
    //        event.getRegistry().register(PotionSunScorned.instance);
    //        event.getRegistry().register(PotionThaumarhia.instance);
    //    }

    //    @SubscribeEvent
    //    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
    //        BiomeHandler.MAGICAL_FOREST = new BiomeGenMagicalForest(new Biome.BiomeProperties("Magical Forest").setBaseHeight(0.2f).setHeightVariation(0.3f).setTemperature(0.8f).setRainfall(0.4f));
    //        event.getRegistry().register(BiomeHandler.MAGICAL_FOREST);
    //        BiomeHandler.EERIE = new BiomeGenEerie(new Biome.BiomeProperties("Eerie").setBaseHeight(0.125f).setHeightVariation(0.4f).setTemperature(0.8f).setRainDisabled());
    //        event.getRegistry().register(BiomeHandler.EERIE);
    //        BiomeHandler.ELDRITCH = new BiomeGenEldritch(new Biome.BiomeProperties("Outer Lands").setBaseHeight(0.125f).setHeightVariation(0.15f).setTemperature(0.8f).setRainfall(0.2f));
    //        event.getRegistry().register(BiomeHandler.ELDRITCH);
    //        BiomeHandler.registerBiomes();
    //        if (ModConfig.CONFIG_WORLD.generateMagicForest) {
    //            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BiomeHandler.MAGICAL_FOREST, ModConfig.CONFIG_WORLD.biomeMagicalForestWeight));
    //            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(BiomeHandler.MAGICAL_FOREST, ModConfig.CONFIG_WORLD.biomeMagicalForestWeight));
    //        }
    //    }

    //    @SubscribeEvent
    //    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    //        SoundsTC.registerSounds(event);
    //        SoundsTC.registerSoundTypes();
    //    }

}

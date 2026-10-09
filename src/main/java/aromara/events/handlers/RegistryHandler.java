package aromara.events.handlers;

import aromara.common.recipes.RecipeToolArmorImbue;
import aromara.init.TCAEntities;
import aromara.init.TCARecipes;
import aromara.init.TCABlocks;
import aromara.init.TCAItems;
import aromara.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static void registerEntities() {
        TCAEntities.preInitEntities();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        TCABlocks.initBlocks();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TCAItems.initItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        TCARecipes.initWorkbench(event.getRegistry());
        TCARecipes.initInfusion(event.getRegistry());
        TCARecipes.initCrucible(event.getRegistry());

        event.getRegistry().register(new RecipeToolArmorImbue().setRegistryName(new ResourceLocation(Main.MODID, RecipeToolArmorImbue.id)));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(
                new ResourceLocation("aromara", "blocks/brainvoid")
                );
    }


}

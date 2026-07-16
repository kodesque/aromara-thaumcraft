package aromara.events.back;

import aromara.common.recipes.RecipeToolArmorImbue;
import aromara.init.BlockInit;
import aromara.init.EntityInit;
import aromara.init.ItemInit;
import aromara.init.RecipeInit;
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
public class RegistryEvents {

    public static void registerEntities() {
        EntityInit.preInitEntities();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BlockInit.initBlocks();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ItemInit.initItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        RecipeInit.initWorkbench(event.getRegistry());
        RecipeInit.initInfusion(event.getRegistry());
        RecipeInit.initCrucible(event.getRegistry());

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

package aromara.events.back;

import aromara.init.BlockInit;
import aromara.init.ItemInit;
import aromara.init.RecipeInit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryEvents {

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
    }

}

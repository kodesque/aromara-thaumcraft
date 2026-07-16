package aromara.events.back;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class TextureHandlerEvents {

    //    @SubscribeEvent
    //    public static void onTextureStitch(TextureStitchEvent.Pre event) {
    //        if (FMLCommonHandler.instance().getSide().isClient()) {
    //            event.getMap().registerSprite(
    //                    new ResourceLocation("aromara", "blocks/brainvoid")
    //                    );
    //        }
    //    }

}

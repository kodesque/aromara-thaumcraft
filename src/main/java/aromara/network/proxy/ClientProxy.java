package aromara.network.proxy;

import aromara.client.renderer.tiles.RendererPressingStone;
import aromara.common.tiles.TilePressingStone;
import aromara.root.Main;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(Main.MODID);
        //        ClientRegistry.bindTileEntitySpecialRenderer(TilePressingStone.class, new RendererPressingStone());
    }

}

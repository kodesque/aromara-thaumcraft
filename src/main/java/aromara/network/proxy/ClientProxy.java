package aromara.network.proxy;

import aromara.client.renderer.tiles.RendererArcaneBrazier;
import aromara.client.renderer.tiles.RendererCandleVat;
import aromara.client.renderer.tiles.RendererPressingStone;
import aromara.client.renderer.tiles.RendererServoscrivener;
import aromara.common.tiles.TileArcaneBrazier;
import aromara.common.tiles.TileCandleVat;
import aromara.common.tiles.TilePressingStone;
import aromara.common.tiles.TileServoscrivener;
import aromara.root.Main;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(Main.MODID);

        ClientRegistry.bindTileEntitySpecialRenderer(TilePressingStone.class, new RendererPressingStone());
        ClientRegistry.bindTileEntitySpecialRenderer(TileArcaneBrazier.class, new RendererArcaneBrazier());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCandleVat.class, new RendererCandleVat());
        ClientRegistry.bindTileEntitySpecialRenderer(TileServoscrivener.class, new RendererServoscrivener());
    }

}

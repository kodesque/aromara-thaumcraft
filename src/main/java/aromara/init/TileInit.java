package aromara.init;

import aromara.common.blocks.BlockArcaneBrazier;
import aromara.common.blocks.BlockPressingStone;
import aromara.common.tiles.TileArcaneBrazier;
import aromara.common.tiles.TilePressingStone;
import aromara.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileInit {

    public static void preInitTiles() {
        GameRegistry.registerTileEntity(TileArcaneBrazier.class, new ResourceLocation(Main.MODID, BlockArcaneBrazier.id));
        GameRegistry.registerTileEntity(TilePressingStone.class, new ResourceLocation(Main.MODID, BlockPressingStone.id));
    }

}

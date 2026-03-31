package aromara.common.blocks;

import aromara.common.templates.BlockTCABase;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTranslucent extends BlockTCABase {

    public BlockTranslucent(Material material, String name) {
        super(material, name);
        // TODO Auto-generated constructor stub
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

}

package aromara.client.renderer.tiles;

import aromara.common.tiles.TileServoscrivener;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import thaumcraft.common.blocks.IBlockFacingHorizontal;

public class RendererServoscrivener extends TileEntitySpecialRenderer<TileServoscrivener> {

    private final ResourceLocation MODEL_LOCATION =
            new ResourceLocation("aromara", "models/block/brainvoid.obj");

    private IBakedModel bakedModel;

    private IBakedModel getModel() {

        if (this.bakedModel != null)
            return this.bakedModel;
        else {
            try {

                IModel model = OBJLoader.INSTANCE.loadModel(this.MODEL_LOCATION);

                this.bakedModel = model.bake(
                        model.getDefaultState(),
                        DefaultVertexFormats.ITEM,
                        location -> Minecraft.getMinecraft()
                        .getTextureMapBlocks()
                        .getAtlasSprite(location.toString())
                        );

                return this.bakedModel;

            } catch (Exception e) {
                throw new RuntimeException("[TCA] Failed to load model indirectly:", e);
            }
        }
    }

    @Override
    public void render(TileServoscrivener te, double x, double y, double z,
            float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();

        double time = te.getWorld().getTotalWorldTime() + partialTicks;

        double offset = Math.sin(time * 0.05) * 0.2;

        GlStateManager.translate(x, y, z);

        IBlockState state = te.getWorld().getBlockState(te.getPos());

        EnumFacing facing = state.getValue(IBlockFacingHorizontal.FACING);

        this.renderBrain(facing, offset);

        GlStateManager.popMatrix();
    }

    private void renderBrain(EnumFacing facing, double offset) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(0.5, 0.5 + 1 * offset, 0.5);


        switch (facing) {

            case NORTH:
                break;

            case SOUTH:
                GlStateManager.rotate(180F, 0F, 1F, 0F);
                break;

            case WEST:
                GlStateManager.rotate(90F, 0F, 1F, 0F);
                break;

            case EAST:
                GlStateManager.rotate(-90F, 0F, 1F, 0F);
                break;
            default:
                break;
        }

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

        Minecraft.getMinecraft()
        .getTextureManager()
        .bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        renderItem.renderItem(new ItemStack(Items.DIAMOND), this.getModel());

        GlStateManager.popMatrix();

    }


}

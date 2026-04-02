package aromara.client.renderer.tiles;

import aromara.common.tiles.TileArcaneBrazier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class RendererArcaneBrazier extends TileEntitySpecialRenderer<TileArcaneBrazier> {

    @Override
    public void render(TileArcaneBrazier te, double x, double y, double z,
            float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);

        if (!te.getSyncedStackInSlot(1).isEmpty()) {
            this.renderFlower(te.getSyncedStackInSlot(1));
        }

        GlStateManager.popMatrix();
    }

    private void renderFlower(ItemStack stack) {

        GlStateManager.pushMatrix();

        GlStateManager.scale(0.6f,0.6f,0.6f);

        GlStateManager.translate(0.5/0.6, 1/0.6, 0.5/0.6);

        Minecraft.getMinecraft()
        .getRenderItem()
        .renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.popMatrix();

    }

}

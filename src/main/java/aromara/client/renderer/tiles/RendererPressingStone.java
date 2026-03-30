package aromara.client.renderer.tiles;

import aromara.common.blocks.BlockPressingStone;
import aromara.common.tiles.TilePressingStone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class RendererPressingStone extends TileEntitySpecialRenderer<TilePressingStone> {

    @Override
    public void render(TilePressingStone te, double x, double y, double z,
            float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();

        boolean is_down = te.getWorld().getBlockState(te.getPos()).getValue(BlockPressingStone.IS_DOWN);

        GlStateManager.translate(x, y, z);

        if (!te.getSyncedStackInSlot(0).isEmpty()) {
            this.renderCrystal(is_down, te.getSyncedStackInSlot(0));
        }
        if (!te.getSyncedStackInSlot(1).isEmpty()) {
            this.renderFlower(te.getSyncedStackInSlot(1));
        }

        GlStateManager.popMatrix();
    }

    private void renderCrystal(boolean is_down, ItemStack stack) {

        GlStateManager.pushMatrix();

        GlStateManager.scale(0.6f,0.6f,0.6f);

        if (is_down) {
            GlStateManager.translate(0.5/0.6, 0.5, 0.5/0.6);
        } else {
            GlStateManager.translate(0.5/0.6, 1/0.6, 0.5/0.6);
        }

        Minecraft.getMinecraft()
        .getRenderItem()
        .renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.popMatrix();

    }

    private void renderFlower(ItemStack stack) {

        GlStateManager.pushMatrix();

        GlStateManager.scale(0.6f,0.6f,0.6f);

        GlStateManager.translate(0.5/0.6, 0.1, 0.5/0.6);
        GlStateManager.rotate(90f, 1f, 0f, 0f);

        Minecraft.getMinecraft()
        .getRenderItem()
        .renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.popMatrix();

    }

}

package aromara.client.renderer.tiles;

import java.awt.Color;

import aromara.common.blocks.BlockThaumostaticSupressor;
import aromara.common.objects.TCABlocks;
import aromara.common.tiles.TileThaumostaticSupressor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.FocusEngine;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXZap;

public class RendererThaumostaticSupressor extends TileEntitySpecialRenderer<TileThaumostaticSupressor>{

    //TODO: this should actually be +0.8 because of how long the antenna is

    @Override
    public void render(TileThaumostaticSupressor te, double x, double y, double z,
            float partialTicks, int destroyStage, float alpha) {

        IBlockState state = te.getWorld().getBlockState(te.getPos());

        if (state.getBlock().equals(TCABlocks.thaumostatic_supressor)) return;
        if (!state.getValue(BlockThaumostaticSupressor.UPPER_PART)) return;

        EnumFacing facing = state.getValue(BlockThaumostaticSupressor.FACING);

        Vec3i X = null;
        Vec3i Z = null;

        switch (facing) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
            case UP:
            case DOWN:

            default:
        }

        BlockPos actualStart = X != null && Z != null ? new BlockPos(te.getPos().getX(), te.getPos().getY()/0.5, te.getPos().getZ()) : new BlockPos(0,0,0);

        GlStateManager.pushMatrix();



        GlStateManager.popMatrix();
    }

    public void shootBolt(Trajectory trajectory) {
        float range = 16.0f;
        Vec3d end = trajectory.direction.normalize();
        end = end.scale(range);
        end = end.add(trajectory.source);
        ray = getPackage().world.rayTraceBlocks(trajectory.source, end);
        if (ray != null) {
            end = ray.hitVec;
        }

        int r = 0;
        int g = 0;
        int b = 0;
        for (FocusEffect ef : getPackage().getFocusEffects()) {
            Color c = new Color(FocusEngine.getElementColor(ef.getKey()));
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
        }
        r /= getPackage().getFocusEffects().length;
        g /= getPackage().getFocusEffects().length;
        b /= getPackage().getFocusEffects().length;
        Color c2 = new Color(r, g, b);
        PacketHandler.INSTANCE.sendToAllAround(new PacketFXZap(trajectory.source, end, c2.getRGB(), /* POWER */ 2 * 0.66f), new NetworkRegistry.TargetPoint(this.getWorld().provider.getDimension(), trajectory.source.x, trajectory.source.y, trajectory.source.z, 64.0));
        return true;
    }

}

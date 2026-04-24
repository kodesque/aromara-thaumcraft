package aromara.common.tiles;

import java.util.Deque;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import thaumcraft.common.tiles.TileThaumcraftInventory;
import thecodex6824.thaumicaugmentation.ThaumicAugmentation;
import thecodex6824.thaumicaugmentation.api.TAConfig;
import thecodex6824.thaumicaugmentation.api.impetus.node.CapabilityImpetusNode;
import thecodex6824.thaumicaugmentation.api.impetus.node.ConsumeResult;
import thecodex6824.thaumicaugmentation.api.impetus.node.IImpetusNode;
import thecodex6824.thaumicaugmentation.api.impetus.node.NodeHelper;
import thecodex6824.thaumicaugmentation.api.impetus.node.prefab.SimpleImpetusConsumer;

public class TileThaumostaticSupressor extends TileThaumcraftInventory{

    //FXArc or FXBolt or FXZap for the ray effect
    //FXSonic or FXBlockWard for the ripple effect near the rift itself

    protected SimpleImpetusConsumer consumer;
    public boolean isWorking;
    public static String isWorkingKey = "isWorking";

    public TileThaumostaticSupressor() {
        super(0);
        this.consumer = new SimpleImpetusConsumer(1, 0);
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {

            long cost = 1;
            ConsumeResult consume = this.consumer.consume(cost, true);
            if (consume.energyConsumed == cost) {
                consume = this.consumer.consume(cost, false);
                this.isWorking = true;
                NodeHelper.syncAllImpetusTransactions(consume.paths.keySet());
                for (Map.Entry<Deque<IImpetusNode>, Long> entry : consume.paths.entrySet()) {
                    NodeHelper.damageEntitiesFromTransaction(entry.getKey(), entry.getValue());
                }
            } else {
                this.isWorking = false;
            }

            this.markDirty();
        }
    }

    @Override
    public void onLoad() {
        ThaumicAugmentation.proxy.registerRenderableImpetusNode(this.consumer);
    }

    @Override
    public void invalidate() {
        if (!this.world.isRemote) {
            NodeHelper.syncDestroyedImpetusNode(this.consumer);
        }

        this.consumer.destroy();
        ThaumicAugmentation.proxy.deregisterRenderableImpetusNode(this.consumer);
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {
        this.consumer.unload();
        ThaumicAugmentation.proxy.deregisterRenderableImpetusNode(this.consumer);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean(isWorkingKey, this.isWorking);

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.consumer.deserializeNBT(compound.getCompoundTag("node"));
        this.isWorking = compound.getBoolean(isWorkingKey);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setTag("node", this.consumer.serializeNBT());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        NodeHelper.tryConnectNewlyLoadedPeers(this.consumer, this.world);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityImpetusNode.IMPETUS_NODE
                ? true : super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityImpetusNode.IMPETUS_NODE)
            return CapabilityImpetusNode.IMPETUS_NODE.cast(this.consumer);
        else
            return super.getCapability(capability, facing);
    }

}

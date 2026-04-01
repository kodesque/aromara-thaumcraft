package aromara.common.tiles;

import aromara.common.blocks.BlockArcaneBrazier;
import aromara.common.items.ItemRedolentBundle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TileArcaneBrazier extends TileThaumcraftInventory {

    public int burningTime;
    public static String burningTimeKey = "burningTime";
    public int burningTimeMax = 3600;

    //slot 0 is for coal, slot 1 is for flowers

    public enum EnumEffect {
        SHIMMER,
        CINDER,
        VIS
    }

    EnumEffect effect;

    public TileArcaneBrazier() {
        super(2);
        this.syncedSlots = new int[] {0, 1};
        this.burningTime = 0;
        this.burningTimeMax = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.burningTime = nbttagcompound.getShort(burningTimeKey);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort(burningTimeKey, (short) this.burningTime);
        return nbttagcompound;
    }

    public void ignite() {
        IBlockState state = this.world.getBlockState(this.pos);
        this.world.setBlockState(this.pos, state.withProperty(BlockArcaneBrazier.STATUS, 2));
        Item comp = ItemRedolentBundle.getComponentFromBundle(this.getStackInSlot(1)).getItem();

        if (comp.equals(Item.getItemFromBlock(BlocksTC.shimmerleaf))) {
            this.effect = EnumEffect.SHIMMER;
        } else if (comp.equals(Item.getItemFromBlock(BlocksTC.cinderpearl))) {
            this.effect = EnumEffect.CINDER;
        } else if (comp.equals(Item.getItemFromBlock(BlocksTC.vishroom))) {
            this.effect = EnumEffect.VIS;
        }

    }

    @Override
    public void update() {
        super.update();

        //        if (this.effect = )
    }

    public int getStatus() {
        return this.world.getBlockState(this.pos).getValue(BlockArcaneBrazier.STATUS);
    }

}

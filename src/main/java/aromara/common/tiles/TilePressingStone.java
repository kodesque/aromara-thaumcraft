package aromara.common.tiles;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TilePressingStone extends TileThaumcraftInventory {

    public int dryingTime;
    public static String dryingTimeKey = "dryingTime";
    public int maxDryingTime;
    public static String maxDryingTimeKey = "maxDryingTime";

    public int idleTicks;
    public int activeTicks;
    public boolean isChanging;

    //slot 0 is for nuggets, slot 1 is for flowers, 2 is for crystals

    public TilePressingStone() {
        super(3);
        this.dryingTime = 0;
        this.maxDryingTime = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.dryingTime = nbttagcompound.getShort(dryingTimeKey);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort(dryingTimeKey, (short) this.dryingTime);
        return nbttagcompound;
    }

    @Override
    public void update() {
        super.update();
        if (!this.world.isRemote) {
            if (this.dryingTime > 0) {
                --this.dryingTime;
            }
            if (this.maxDryingTime <= 0) {
                this.maxDryingTime = 1;
            }
            if (this.dryingTime > this.maxDryingTime) {
                this.dryingTime = this.maxDryingTime;
            }
        }
    }

    public int calcDryingTime() {

        ItemStack stack = this.getStackInSlot(0);
        Item item = stack.getItem();

        if (item == Item.getItemFromBlock(Blocks.RED_FLOWER))
            return 6000;
        else if (item == Item.getItemFromBlock(Blocks.DOUBLE_PLANT))
            return 0;
        //TODO
        else if (item == Item.getItemFromBlock(Blocks.CACTUS))
            return 24000;

        return 0;
    }

}

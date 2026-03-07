package aromara.common.tiles;

import aromara.common.blocks.BlockPressingStone;
import aromara.common.items.ItemRedolentBundle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TilePressingStone extends TileThaumcraftInventory {

    public int dryingTime;
    public static String dryingTimeKey = "dryingTime";
    public int maxDryingTime;
    public static String maxDryingTimeKey = "maxDryingTime";

    public int idleTicks;
    public int activeTicks;
    public boolean isChanging;

    public AspectList toPull;

    //slot 0 is for nuggets, slot 1 is for flowers

    //TODO: don't forget to change drying time to 3600, ignore for now

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

    public void sendDown() {
        if (this.maxDryingTime <= 0) {
            ItemStack flower = this.getStackInSlot(1);
            ItemStack shards = this.getStackInSlot(0);
            if (!flower.isEmpty() && !shards.isEmpty() && !this.isDown()) {
                this.maxDryingTime = 400;
                this.dryingTime = this.maxDryingTime;
                this.toPull = AspectHelper.getObjectAspects(ItemRedolentBundle.getComponentFromBundle(flower));
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockPressingStone.IS_DOWN, true));
            }
        }
    }

    public boolean isDown() {
        return this.world.getBlockState(this.pos).getValue(BlockPressingStone.IS_DOWN);
    }

    @Override
    public void update() {
        super.update();

        if (!this.world.isRemote) {


            if (this.dryingTime <= 0 && this.isDown()) {
                ItemStack insides = this.getStackInSlot(1);
                this.setInventorySlotContents(1, ItemRedolentBundle.getBundleDried(insides));
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockPressingStone.IS_DOWN, false));
                this.maxDryingTime = 0;
            }

            if (this.dryingTime > 0) {
                --this.dryingTime;

                if (this.toPull != null && this.toPull.getAspects().length > 0 && this.world.rand.nextDouble() == 0.5D) {
                    ItemStack crystal = ThaumcraftApiHelper.makeCrystal(this.toPull.getAspects()[0]);
                    InventoryUtils.ejectStackAt(this.world, this.pos, EnumFacing.UP, crystal);
                    this.toPull.remove(this.toPull.getAspects()[0]);
                    this.decrStackSize(0, 1);

                    if (this.world.rand.nextDouble() == 0.2D) {
                        AuraHelper.polluteAura(this.world, this.pos, 1F, true);
                    }
                }
            }

            if (this.dryingTime > this.maxDryingTime) {
                this.dryingTime = this.maxDryingTime;
            }

        }
    }

}

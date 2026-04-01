package aromara.common.tiles;

import aromara.common.blocks.BlockPressingStone;
import aromara.common.items.ItemRedolentBundle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TilePressingStone extends TileThaumcraftInventory {

    public int dryingTime;
    public static String dryingTimeKey = "dryingTime";
    public int dryingTimeMax = 3600;

    public AspectList toPull;

    //slot 0 is for nuggets, slot 1 is for flowers

    public TilePressingStone() {
        super(2);
        this.syncedSlots = new int[] {0, 1};
        this.dryingTimeMax = 0;
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
        if (this.dryingTimeMax <= 0) {
            ItemStack flower = this.getStackInSlot(1);
            ItemStack shards = this.getStackInSlot(0);
            if (!flower.isEmpty() && !shards.isEmpty() && !this.isDown()) {
                this.dryingTime = this.dryingTimeMax;
                this.toPull = AspectHelper.getObjectAspects(ItemRedolentBundle.getComponentFromBundle(flower));
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockPressingStone.IS_DOWN, true));

                for (int j = 0; j < 1 * 8; ++j)
                {
                    float f = this.world.rand.nextFloat() * ((float)Math.PI * 2F);
                    float f1 = this.world.rand.nextFloat() * 0.5F + 0.5F;
                    float f2 = MathHelper.sin(f) * 0.5F * f1;
                    float f3 = MathHelper.cos(f) * 0.5F * f1;
                    World world = this.world;
                    EnumParticleTypes enumparticletypes = EnumParticleTypes.SLIME;
                    double d0 = this.pos.getX() + (double)f2;
                    double d1 = this.pos.getZ() + (double)f3;
                    world.spawnParticle(enumparticletypes, d0, this.pos.getY(), d1, 0.0D, 0.0D, 0.0D);
                }

                //                this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
                //TODO: add your own custom sound later on
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
            }

            if (this.dryingTime > 0) {
                --this.dryingTime;

                if (this.toPull != null && this.toPull.getAspects().length > 0 && this.world.rand.nextInt(100) == 1) {
                    Aspect toExtract = this.toPull.getAspects()[this.world.rand.nextInt(this.toPull.getAspects().length)];
                    ItemStack crystal = ThaumcraftApiHelper.makeCrystal(toExtract);
                    InventoryUtils.ejectStackAt(this.world, this.pos, EnumFacing.UP, crystal);
                    this.toPull.remove(toExtract, 1);
                    this.decrStackSize(0, 1);

                    if (this.world.rand.nextInt(5) == 1) {
                        AuraHelper.polluteAura(this.world, this.pos, 1F, true);
                    }
                }
            }

            if (this.dryingTime > this.dryingTimeMax) {
                this.dryingTime = this.dryingTimeMax;
            }

        }
    }

}

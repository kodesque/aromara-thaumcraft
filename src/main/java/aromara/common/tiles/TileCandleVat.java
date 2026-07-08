package aromara.common.tiles;

import aromara.common.blocks.BlockCandleVat;
import aromara.common.items.ItemRedolentBundle;
import aromara.common.objects.TCAItems;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumFunc;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.IBlockEnabled;
import thaumcraft.common.lib.utils.BlockStateUtils;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TileCandleVat extends TileThaumcraftInventory implements IAspectContainer, IEssentiaTransport {

    Aspect sucking;
    public AspectList stored;
    public static String storedKey = "storedKey";

    public int boilingTime;
    public static String boilingTimeKey = "boilingTime";

    String effect;
    public static String effectKey = "effect";

    public int boilingTimeMax = 2400; /* two minutes between each boiling stage */
    public int boilingTimeBreakdown = 200;
    public int essentiaMax = 30;
    public Aspect typeAllowed = Aspect.FIRE;

    /* 0 -> empty, 1 -> flesh, 2 -> impure, 3 -> liquid, 4 -> rancid 5 -> imbued */

    public TileCandleVat() {
        super(1);
        this.syncedSlots = new int[] {0};

        this.sucking = null;
        this.stored = new AspectList();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.boilingTime = nbttagcompound.getShort(boilingTimeKey);
        this.effect = nbttagcompound.getString(effectKey);
        AspectList list = new AspectList();
        list.readFromNBT(nbttagcompound, storedKey);
        this.stored = list;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort(boilingTimeKey, (short) this.boilingTime);
        nbttagcompound.setString(effectKey, this.effect == null ? "" : this.effect);
        this.stored.writeToNBT(nbttagcompound, storedKey);
        return nbttagcompound;
    }

    public boolean attemptMixIn(ItemStack stack) {

        Item item = stack.getItem();

        if (item.equals(Item.getItemFromBlock(BlocksTC.fleshBlock)) && this.getStatus() == 0) {
            this.setStatus(1);
            this.boilingTime = this.boilingTimeMax;
            return true;
        } else if (item.equals(ItemsTC.salisMundus) && this.getStatus() == 2) {
            this.setStatus(3);
            this.boilingTime = this.boilingTimeMax;
            return true;
        } else if (item.equals(TCAItems.redolent_bundle) && this.getStatus() == 3) {
            this.setInventorySlotContents(0, stack);
            this.boilingTime = this.boilingTimeMax;
            return true;
        }

        return false;

    }

    public ItemStack attemptScoop(ItemStack bucket) {
        if (this.getStatus() == 4) {
            //            ItemStack result = NBTManager.mutatePairs(bucket, EnumFunc.APPLYSOFT, new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, this.effect.toString()));

            NBTManager.apply(bucket, new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.RANCID, true));

            this.effect = null;
            this.setStatus(0);
            return bucket;
        }

        if (this.getStatus() == 5) {
            //            ItemStack result = NBTManager.mutatePairs(bucket, EnumFunc.APPLYSOFT, new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.RANCID, true));

            NBTManager.apply(bucket, new ValuePair<>(EnumGroups.OIL, EnumGroups.Oil.TYPE, this.effect.toString()));

            this.effect = null;
            this.setStatus(0);
            return bucket;
        }

        return new ItemStack(Items.AIR);
    }

    @Override
    public void update() {
        super.update();

        if (!this.world.isRemote) {

            if (this.isOn()) {

                if (this.stored.getAmount(this.typeAllowed) < this.essentiaMax) {
                    this.sucking = this.typeAllowed;
                    this.fill();
                } else {
                    this.sucking = null;
                }

                if (this.stored.getAmount(this.typeAllowed) > 0 && this.getStatus() != 0) {

                    this.boilingTime--;

                    if (this.world.getWorldTime() % 160 == 0) {
                        this.stored.remove(this.typeAllowed, 1);
                    }

                    if (this.boilingTime == 0) {
                        if (this.getStatus() == 1) {
                            this.setStatus(2);
                        }
                        if (this.getStatus() == 3 && !this.getStackInSlot(0).isEmpty()) {
                            this.setStatus(5);

                            Item comp = ItemRedolentBundle.getComponentFromBundle(this.getStackInSlot(0)).getItem();
                            this.effect = comp.getRegistryName().toString();
                            this.removeStackFromSlot(0);
                        }
                    }

                    if (this.boilingTime < -this.boilingTimeBreakdown) {
                        this.setStatus(4);
                        this.decrStackSize(0, 1);

                        this.boilingTime = 0;
                        return;
                    }

                    if (this.boilingTime > this.boilingTimeMax) {
                        this.boilingTime = this.boilingTimeMax;
                    }

                }

            }
        }
    }

    void fill() {
        EnumFacing facing = BlockStateUtils.getFacing(this.getBlockMetadata());
        TileEntity te = null;
        IEssentiaTransport ic = null;
        for (int y = 0; y <= 1; ++y) {
            for (EnumFacing dir : EnumFacing.VALUES) {
                if (dir != facing) {
                    te = ThaumcraftApiHelper.getConnectableTile(this.world, this.pos.up(y), dir);
                    if (te != null) {
                        ic = (IEssentiaTransport)te;
                        if (ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) < this.getSuctionAmount(null) && this.getSuctionAmount(null) >= ic.getMinimumSuction()) {
                            int ess = ic.takeEssentia(this.sucking, 1, dir.getOpposite());
                            if (ess > 0) {
                                this.addToContainer(this.sucking, ess);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isConnectable(EnumFacing face) {

        return face == EnumFacing.DOWN;
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        return face == EnumFacing.DOWN;
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        return false;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {
    }

    @Override
    public Aspect getSuctionType(EnumFacing loc) {
        return this.sucking;
    }

    @Override
    public int getSuctionAmount(EnumFacing loc) {
        return (this.sucking != null) ? 128 : 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, EnumFacing face) {
        return (this.canOutputTo(face) && this.takeFromContainer(aspect, amount)) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, EnumFacing face) {
        return this.canInputFrom(face) ? (amount - this.addToContainer(aspect, amount)) : 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing face) {
        return this.stored.getAmount(this.typeAllowed) > 0 ? this.typeAllowed : null;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {
        return this.stored.getAmount(this.typeAllowed);
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public AspectList getAspects() {
        return this.stored;
    }

    @Override
    public void setAspects(AspectList aspects) {
        this.stored = aspects;
    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return tag == this.typeAllowed;
    }

    @Override
    public int addToContainer(Aspect tt, int am) {
        if (tt != this.typeAllowed) return am;

        int storedAmount = this.stored.getAmount(tt);
        int space = this.essentiaMax - storedAmount;

        if (space <= 0) return am;

        int add = Math.min(space, am);
        this.stored.add(tt, add);

        this.syncTile(false);
        this.markDirty();

        return am - add;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {

        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {

        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tt, int am) {
        return this.stored.getAmount(tt) >= am;
    }

    @Override
    public int containerContains(Aspect tt) {
        return this.stored.getAmount(tt);
    }

    public void setStatus(int status) {

        IBlockState state = this.world.getBlockState(this.pos);

        if (state.getValue(BlockCandleVat.STATUS) != status) {
            this.world.setBlockState(this.pos, state.withProperty(BlockCandleVat.STATUS, status));
        }
    }

    public int getStatus() {
        return this.world.getBlockState(this.pos).getValue(BlockCandleVat.STATUS);
    }

    public boolean isOn() {
        return this.world.getBlockState(this.pos).getValue(IBlockEnabled.ENABLED);
    }

}

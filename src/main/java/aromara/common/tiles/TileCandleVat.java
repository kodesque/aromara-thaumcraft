package aromara.common.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TileCandleVat extends TileThaumcraftInventory implements IAspectContainer, IEssentiaTransport {

    public int boilingTime;
    public static String boilingTimeKey = "boilingTime";

    public int boilingTimeMax = 2400; /* two minutes between each boiling stage */

    public TileCandleVat(int size) {
        super(0);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.boilingTime = nbttagcompound.getShort(boilingTimeKey);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort(boilingTimeKey, (short) this.boilingTime);
        return nbttagcompound;
    }

    public void attemptMixIn(ItemStack stack) {

    }

    @Override
    public void update() {
        super.update();

        if (!this.world.isRemote) {

        }
    }

    @Override
    public boolean isConnectable(EnumFacing face) {

        return false;
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
    public Aspect getSuctionType(EnumFacing face) {

        return null;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {

        return 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, EnumFacing face) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, EnumFacing face) {

        return 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing face) {

        return null;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {

        return 0;
    }

    @Override
    public int getMinimumSuction() {

        return 0;
    }

    @Override
    public AspectList getAspects() {

        return null;
    }

    @Override
    public void setAspects(AspectList aspects) {


    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {

        return false;
    }

    @Override
    public int addToContainer(Aspect tag, int amount) {

        return 0;
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
    public boolean doesContainerContainAmount(Aspect tag, int amount) {

        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {

        return false;
    }

    @Override
    public int containerContains(Aspect tag) {

        return 0;
    }

}

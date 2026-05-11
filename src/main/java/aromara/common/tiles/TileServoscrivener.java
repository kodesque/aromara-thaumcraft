package aromara.common.tiles;

import java.util.List;

import aromara.common.blocks.BlockArcaneBrazier;
import aromara.common.blocks.BlockServoscrivener;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumFunc;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.ValuePair;
import aromara.util.ResearchAppends;
import aromara.util.RiddleHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TileServoscrivener extends TileThaumcraftInventory{

    public AspectList required;
    public static String requiredKey = "required";

    public AspectList stored;
    public static String storedKey = "stored";

    public boolean started;
    public int delay;
    public int maxDelay = 30;

    public TileServoscrivener() {
        super(1);
        this.syncedSlots = new int[] {0};

        this.stored = new AspectList();
        this.required = new AspectList();

        this.delay = 0;
        this.started = false;
    };

    public void annul() {
        this.stored = new AspectList();
        this.required = new AspectList();
        this.started = false;
        this.delay = 0;
    }

    public int tryAddAspect(Aspect aspect, int amount) {

        for (int i = 0; i < this.required.getAspects().length; i++) {

            if (this.required.getAspects()[i].equals(aspect)) {
                int required = this.required.getAmount(this.required.getAspects()[i]);

                if (required == 0)
                    return -1;
                else {

                    this.stored.add(this.required.getAspects()[i], Math.max(required, amount));

                    return Math.max(required, amount);
                }
            }
        }
        return -1;
    }

    @Override
    public void update() {
        super.update();

        if (!this.world.isRemote) {

            if (!this.getStackInSlot(0).isEmpty()) {
                this.setStatus(true);
            } else {
                this.setStatus(false);
            }

            if (this.required != null && this.stored != null) {

                if (this.required.size() > 0) {

                    if (!this.started) {
                        if (this.delay != this.maxDelay) {
                            this.delay++;
                        } else if (this.delay == this.maxDelay) {
                            List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.pos).grow(5));

                            for (EntityPlayer player : players) {
                                this.inform(player);
                            }
                            this.started = true;
                        }
                    }

                    if (this.required.visSize() == this.stored.visSize()) {

                        ItemStack stack = this.getStackInSlot(0);

                        this.setInventorySlotContents(0, NBTManager.mutatePairs(stack, EnumFunc.APPLY, new ValuePair<>(EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.DONE, true)));
                        this.annul();

                    }
                }
            }
        }
    }

    public void startResearch() {
        ItemStack stack = this.getStackInSlot(0);

        String research = NBTManager.get(stack, EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.NAME);

        AspectList list = ResearchAppends.getList(research);
        this.required = list;
    }

    public void inform(EntityPlayer player) {

        String key = NBTManager.get(this.getStackInSlot(0), EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.NAME);

        TextComponentTranslation message = new TextComponentTranslation("riddle" + "." + key + "." + "text");

        player.sendMessage(RiddleHandler.process(message));
    }

    public void endResearch() {

        ItemStack stack = this.getStackInSlot(0);

        NBTManager.apply(stack, new ValuePair<>(EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.DONE, true));

        this.setInventorySlotContents(0, stack);

    }

    public void setStatus(boolean status) {

        IBlockState state = this.world.getBlockState(this.pos);

        if (state.getValue(BlockServoscrivener.HAS_PAPER) != status) {
            this.world.setBlockState(this.pos, state.withProperty(BlockServoscrivener.HAS_PAPER, status));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        AspectList list = new AspectList();
        list.readFromNBT(nbttagcompound, storedKey);
        this.stored = list;
        AspectList required = new AspectList();
        required.readFromNBT(nbttagcompound, requiredKey);
        this.required = required;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        this.stored.writeToNBT(nbttagcompound, storedKey);
        this.required.writeToNBT(nbttagcompound, requiredKey);
        return nbttagcompound;
    }

}

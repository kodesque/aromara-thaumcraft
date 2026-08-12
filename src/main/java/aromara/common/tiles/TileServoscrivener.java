package aromara.common.tiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import aromara.common.blocks.BlockServoscrivener;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumFunc;
import aromara.util.NBTManager.EnumGroups;
import aromara.util.NBTManager.INBTGroupValues;
import aromara.util.NBTManager.ValuePair;
import aromara.util.ResearchAppends;
import aromara.util.RiddleHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TileServoscrivener extends TileThaumcraftInventory {

    public static final ArrayList<BlockPosExact> scriveners = new ArrayList<BlockPosExact>();

    public AspectList required;
    public static final String requiredKey = "required";

    public AspectList stored;
    public static final String storedKey = "stored";

    public List<Integer> chosenIndices;
    public static final String chosenIndicesKey = "chosenIndices";

    public boolean started;
    public int delay;
    public static final int maxDelay = 40;

    public TileServoscrivener() {
        super(1);
        this.syncedSlots = new int[] {0};

        this.stored = new AspectList();
        this.required = new AspectList();

        this.delay = 0;
        this.started = false;
    };

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
                        if (this.delay != TileServoscrivener.maxDelay) {
                            this.delay++;
                        } else if (this.delay == TileServoscrivener.maxDelay) {
                            List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.pos).grow(5));

                            for (EntityPlayer player : players) {
                                this.inform(player);
                            }
                            this.started = true;
                        }
                    }
                }
            }

            if ((this.required == null || this.required.visSize() == 0) && (this.stored != null && this.stored.visSize() != 0)) {

                this.endResearch();
            }
        }
    }

    /* ESSENTIA HANDLING */

    public void addAspectSmart(Aspect aspect, int amount) {

        int required = this.required.getAmount(aspect);

        if (amount >= required) {

            int index = -1;
            Aspect[] aspects = this.required.getAspects();

            for (int i = 0; i < aspects.length; i++) {
                if (aspects[i] == aspect) {
                    index = i;
                    break;
                }
            }

            this.stored.add(aspect, required);
            this.required.remove(aspect, required);

            if (index != -1) {
                this.chosenIndices.remove(index);
            }

        } else {

            this.stored.add(aspect, amount);
            this.required.remove(aspect, amount);
        }
    }

    public boolean canAddAspect(Aspect aspect) {
        for (int i = 0; i < this.required.getAspects().length; i++) {

            if (this.required.getAspects()[i].equals(aspect)) {
                int required = this.required.getAmount(this.required.getAspects()[i]);

                if (required != 0)
                    return true;
            }
        }
        return false;
    }

    /* RESEARCH HANDLING */

    public void startResearch(World world) {
        ItemStack stack = this.getStackInSlot(0);

        String research = NBTManager.get(stack, EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.NAME);

        AspectList list = ResearchAppends.getList(research);


        List<Integer> chosen = RiddleHandler.roll(list, world);
        Collections.sort(chosen);

        AspectList actual = new AspectList();

        for (int i = 0; i < 3; i++) {

            Aspect aspect = list.getAspects()[chosen.get(i)];

            actual.add(aspect, list.getAmount(aspect));
        }

        world.playSound (
                null,
                this.pos,
                SoundsTC.write,
                SoundCategory.BLOCKS,
                2.0F,
                1.0F
                );

        this.required = actual;
        this.chosenIndices = chosen;
    }

    public void endResearch() {

        ItemStack stack = this.getStackInSlot(0);

        NBTManager.apply(stack, new ValuePair<>(EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.DONE, true));

        this.removeStackFromSlot(0);

        this.world.playSound (
                null,
                this.pos,
                SoundsTC.learn,
                SoundCategory.BLOCKS,
                3.0F,
                1.0F
                );

        InventoryUtils.ejectStackAt(this.world, this.pos, EnumFacing.UP, stack);

        this.annul();
    }

    public void annul() {
        this.stored = new AspectList();
        this.required = new AspectList();
        this.chosenIndices = null;
        this.started = false;
        this.delay = 0;
    }

    public void inform(EntityPlayer player) {

        String key = NBTManager.get(this.getStackInSlot(0), EnumGroups.KNOWLEDGE, EnumGroups.Knowledge.NAME);

        TextComponentTranslation message = new TextComponentTranslation("riddle" + "." + key + "." + "text");

        player.getEntityWorld().playSound(
                null,
                this.pos,
                SoundsTC.chant,
                SoundCategory.BLOCKS,
                0.5F,
                5.0F
                );

        player.sendMessage(new TextComponentString(RiddleHandler.process(message, this.chosenIndices, this.required)));
    }

    /* LIST STATUS HANDLING */

    public static void conductCheck(BlockPosExact pos) {

        if (!scriveners.contains(pos)) {
            scriveners.add(pos);
        }

    }

    public static class BlockPosExact {
        BlockPos pos;
        World world;

        public BlockPosExact(BlockPos pos, World world) {
            this.pos = pos;
            this.world = world;
        }

        public BlockPos getPos() {
            return this.pos;
        }

        public World getWorld() {
            return this.world;
        }

    }

    /* NBT & STATE HANDLING */

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

        NBTTagList tagList = nbttagcompound.getTagList(chosenIndicesKey, Constants.NBT.TAG_INT);
        this.chosenIndices = readIntList(tagList);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        if (this.stored != null) {
            this.stored.writeToNBT(nbttagcompound, storedKey);
        }
        if (this.required != null) {
            this.required.writeToNBT(nbttagcompound, requiredKey);
        }
        if (this.chosenIndices != null) {
            nbttagcompound.setTag(chosenIndicesKey, writeIntList(this.chosenIndices));
        }
        return nbttagcompound;
    }

    public static NBTTagList writeIntList(List<Integer> list) {

        NBTTagList tagList = new NBTTagList();

        for (Integer i : list) {
            tagList.appendTag(new NBTTagInt(i));
        }

        return tagList;
    }

    public static List<Integer> readIntList(NBTTagList tagList) {

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < tagList.tagCount(); i++) {
            result.add(tagList.getIntAt(i));
        }

        return result;
    }

}

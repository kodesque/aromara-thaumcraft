package aromara.common.tiles;

import java.util.List;

import aromara.common.blocks.BlockArcaneBrazier;
import aromara.common.items.ItemRedolentBundle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.IPlayerWarp.EnumWarpType;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.entities.monster.mods.ChampionModifier;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TileArcaneBrazier extends TileThaumcraftInventory {

    public int burningTime;
    public static String burningTimeKey = "burningTime";

    //0 -> cinderpearl, 1 -> shimmerleaf, 2 -> vishroom

    String effect;
    public static String effectKey = "effect";

    //combustion rate of 1 coal in ticks

    public int factor = 100;
    public int burningTimeMax;

    //slot 0 is for coal, slot 1 is for flowers

    public TileArcaneBrazier() {
        super(2);
        this.syncedSlots = new int[] {0, 1};
        this.burningTimeMax = 64 * this.factor;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.burningTime = nbttagcompound.getShort(burningTimeKey);
        this.effect = nbttagcompound.getString(effectKey);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort(burningTimeKey, (short) this.burningTime);
        nbttagcompound.setString(effectKey, this.effect == null ? "" : this.effect);
        return nbttagcompound;
    }

    public void ignite() {
        Item comp = ItemRedolentBundle.getComponentFromBundle(this.getStackInSlot(1)).getItem();
        this.effect = comp.getRegistryName().toString();
        this.burningTime = this.getStackInSlot(0).getCount() * this.factor;
        this.removeStackFromSlot(1);
    }

    @Override
    public void update() {
        super.update();

        if (!this.world.isRemote) {

            if (this.burningTime > 0 && this.effect != null) {
                this.setStatus(2);
            }
            else if (!this.getStackInSlot(0).isEmpty()) {
                this.setStatus(1);
            }
            else {
                this.setStatus(0);
            }

            if (this.burningTime > 0) {
                --this.burningTime;

                if (this.burningTime % this.factor == 0) {

                    this.applyEffect();
                    this.decrStackSize(0, 1);
                }
            }
        }

        if (this.burningTime > this.burningTimeMax) {
            this.burningTime = this.burningTimeMax;
        }

    }

    public void applyEffect() {

        if (this.effect == null) return;

        Item item = Item.getByNameOrId(this.effect);

        AxisAlignedBB box = new AxisAlignedBB(this.pos).grow(10);

        if (item.equals(ItemRedolentBundle.plants[0])) {

            this.applyCinder(box);

        } else if (item.equals(ItemRedolentBundle.plants[1])) {

            this.applyShimmer(box);

        } else if (item.equals(ItemRedolentBundle.plants[2])) {

            this.applyShroom(box);

        }

    }

    public void applyShroom(AxisAlignedBB box) {

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(null, box);

        EntityLivingBase target = null;
        EntityLiving attacker = null;
        boolean paired = false;

        for (int u = 0; u < list.size(); u++) {
            if (list.get(u) instanceof EntityLiving && list.get(u) instanceof IMob && !paired) {

                attacker = (EntityLiving) list.get(u);

                for (int i = u; i < list.size(); i++) {
                    if (list.get(i) instanceof EntityLivingBase && list.get(i) instanceof IMob) {
                        target = (EntityLivingBase) list.get(i);
                        paired = true;
                    }
                }

            } else if (list.get(u) instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)list.get(u);
                if (this.world.rand.nextInt(10) == 1) {
                    if (player.hasCapability(ThaumcraftCapabilities.WARP, null)) {
                        IPlayerWarp cap = player.getCapability(ThaumcraftCapabilities.WARP, null);
                        cap.add(EnumWarpType.NORMAL, 1);
                    }
                }
            }
        }

        if ((attacker != null && attacker.isEntityAlive()) && (target != null && target.isEntityAlive())) {
            attacker.setAttackTarget(target);
        }



    }

    public void applyShimmer(AxisAlignedBB box) {

        List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, box);

        for (EntityPlayer player : list) {
            if (player != null && player.getFoodStats().getFoodLevel() < 20) {
                player.getFoodStats().addStats(1, 0);
                AuraHelper.drainVis(this.world, this.pos, 10, false);
            }
        }
    }

    public void applyCinder(AxisAlignedBB box) {

        AuraHelper.drainFlux(this.world, this.pos, 1, false);

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(null, box);

        for (Entity entity : list) {

            if (entity instanceof EntityLivingBase) {

                EntityLivingBase base = (EntityLivingBase)entity;
                IAttributeInstance attr = base.getEntityAttribute(ThaumcraftApiHelper.CHAMPION_MOD);

                if (entity instanceof ITaintedMob || (attr != null && attr.getModifier(ChampionModifier.mods[13].attributeMod.getID()) != null)) {
                    entity.setFire(10);
                }
            }
        }

        if (this.world.isRaining()) {

            if (this.world.rand.nextInt(10) == 1) {
                for (int a = 0; a < 10; ++a) {
                    int xx = (int)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f);
                    int zz = (int)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f);
                    BlockPos p = this.pos.add(xx, 0, zz);
                    if (this.world.rand.nextBoolean()) {
                        if (this.world.isBlockNormalCube(p.down(), false) && this.world.getBlockState(p).getBlock().isReplaceable(this.world, p) && this.world.canSeeSky(p)) {
                            this.world.setBlockState(p, BlocksTC.fluxGoo.getDefaultState());
                        }
                        else {
                            p = p.down();
                            if (this.world.isBlockNormalCube(p.down(), false) && this.world.getBlockState(p).getBlock().isReplaceable(this.world, p) && this.world.canSeeSky(p)) {
                                this.world.setBlockState(p, BlocksTC.fluxGoo.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    public void setStatus(int status) {

        IBlockState state = this.world.getBlockState(this.pos);

        if (state.getValue(BlockArcaneBrazier.STATUS) != status) {
            this.world.setBlockState(this.pos, state.withProperty(BlockArcaneBrazier.STATUS, status));
        }
    }

}

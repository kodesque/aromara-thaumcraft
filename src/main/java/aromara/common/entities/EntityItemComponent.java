package aromara.common.entities;

import java.util.List;

import aromara.util.NBTManager.EnumGroups;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.entities.monster.cult.EntityCultistPortalLesser;
import thaumcraft.common.lib.SoundsTC;

public class EntityItemComponent extends EntityItem {

    public static String id = "item_component";

    public EntityItemComponent(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
        this.setPosition(par2, par4, par6);
        this.setItem(par8ItemStack);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.motionY = 0.20000000298023224;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }

    public EntityItemComponent(World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.ticksExisted % 10 == 0) {

            AxisAlignedBB box = this.getEntityBoundingBox();

            List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, box);

            if (!entities.isEmpty()) {

                for (Entity entity : entities) {
                    if (entity instanceof EntityCultistPortalLesser) {

                        EntityCultistPortalLesser portal = (EntityCultistPortalLesser)entity;

                        NBTTagCompound compound = portal.getEntityData();
                        int toset = -1;

                        if (this.getItem().getItem().equals(ItemsTC.brain)) {
                            toset = 0;
                        } else if (this.getItem().getItem().equals(ItemsTC.scribingTools)) {
                            toset = 1;
                        } else if (this.getItem().getItem().equals(Items.ENDER_PEARL)) {
                            toset = 2;
                        }

                        if (toset != -1 && !compound.hasKey(String.valueOf(String.valueOf(toset)))) {
                            compound.setBoolean(String.valueOf(toset), true);

                            this.setDead();
                        }

                        break;
                    }
                }
            }

        }
    }

}

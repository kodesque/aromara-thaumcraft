package aromara.common.tiles;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import thaumcraft.common.tiles.TileThaumcraftInventory;

public class TileThaumostaticSupressor extends TileThaumcraftInventory{

    //if fuse is an itemstack, its values shouldn't be stored separately

    //you can take the arcane bore beam logic and recolor it for your purposes
    //or make some kind of a fat lightning
    //the caster lightnint!!

    //FXArc or FXBolt or FXZap
    //I can simply copy this stuff from the FocusMediumBolt execute method
    //and put it into the update method with a small timer
    //bravo!

    //FXSonic or FXBlockWard for the ripple effect near the rift itself

    public TileThaumostaticSupressor() {
        super(1);
        this.syncedSlots = new int[] {0};
    }

}

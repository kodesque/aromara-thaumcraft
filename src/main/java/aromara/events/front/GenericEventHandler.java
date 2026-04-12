package aromara.events.front;

import aromara.common.objects.TCAItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.SoundsTC;
import thecodex6824.thaumicaugmentation.api.TABlocks;
import thecodex6824.thaumicaugmentation.api.block.property.ITAStoneType;

public class GenericEventHandler {

    @SubscribeEvent
    public void GlyphBreak (PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntityPlayer().getHeldItemMainhand().getItem().equals(ItemsTC.thaumometer)
                && event.getWorld().getBlockState(event.getPos()).equals(TABlocks.STONE.getDefaultState().withProperty(ITAStoneType.STONE_TYPE, ITAStoneType.StoneType.ANCIENT_GLYPHS))) {
            event.getWorld().destroyBlock(event.getPos(), false);

            event.getWorld().playSound(
                    null,
                    event.getPos(),
                    SoundsTC.urnbreak,
                    SoundCategory.BLOCKS,
                    2.0F,
                    1.0F
                    );

            if (event.getWorld().rand.nextInt(10) == 1) {
                if (!event.getEntityPlayer().addItemStackToInventory(new ItemStack(TCAItems.glyph_piece))) {
                    event.getEntityPlayer().dropItem(new ItemStack(TCAItems.glyph_piece), false);
                }
            }
        }
    }

}

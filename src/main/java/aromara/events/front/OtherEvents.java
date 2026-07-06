package aromara.events.front;

import aromara.common.objects.TCABlocks;
import aromara.common.objects.TCAItems;
import aromara.common.tiles.TileServoscrivener;
import aromara.common.tiles.TileServoscrivener.BlockPosExact;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.blocks.BlocksTC;

@Mod.EventBusSubscriber
public class OtherEvents {

    @SubscribeEvent
    public static void crucibleTransform(PlayerInteractEvent.RightClickBlock event) {

        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand();

        if (block.equals(BlocksTC.crucible) && stack.getItem().equals(TCAItems.heater)) {
            world.destroyBlock(pos, false);
            world.setBlockState(pos, TCABlocks.candle_vat.getDefaultState());

            stack.shrink(1);

            event.setCanceled(false);
        }
    }

}

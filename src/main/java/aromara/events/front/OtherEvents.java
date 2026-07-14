package aromara.events.front;

import java.util.List;

import aromara.common.items.ItemRedolentBundle;
import aromara.common.items.ItemScentPhial;
import aromara.common.objects.TCABlocks;
import aromara.common.objects.TCAItems;
import aromara.common.recipes.RecipeToolArmorImbue;
import aromara.root.Main;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

    @SubscribeEvent
    public static void renderImbuedWith(ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);

        if (RecipeToolArmorImbue.isToolArmor(stack)) {
            if (nbt != null) {

                for (Item plant : ItemRedolentBundle.plants) {
                    if (NBTManager.get(stack, EnumGroups.OIL, EnumGroups.Oil.TYPE).equals(plant.getRegistryName().toString())) {
                        tips.add(TextFormatting.DARK_PURPLE + ItemScentPhial.base.getFormattedText() + " " + TextFormatting.DARK_PURPLE + new ItemStack(plant).getDisplayName());
                        return;
                    }
                }

            }
        }
    }

}

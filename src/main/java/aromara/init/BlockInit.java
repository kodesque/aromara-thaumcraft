package aromara.init;

import aromara.common.blocks.BlockArcaneBrazier;
import aromara.common.blocks.BlockPressingStone;
import aromara.common.blocks.BlockVishroomCap;
import aromara.common.blocks.BlockVishroomStem;
import aromara.common.objects.TCABlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.Thaumcraft;

public class BlockInit {

    public static void initBlocks() {
        TCABlocks.arcane_brazier = registerBlock(new BlockArcaneBrazier());
        TCABlocks.pressing_stone = registerBlock(new BlockPressingStone());

        TCABlocks.vishroom_block_cap = registerBlock(new BlockVishroomCap());
        TCABlocks.vishroom_block_stem = registerBlock(new BlockVishroomStem());
    }

    private static Block registerBlock(Block block, ItemBlock itemBlock) {
        ForgeRegistries.BLOCKS.register(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
        Thaumcraft.proxy.registerModel(itemBlock);
        return block;
    }

    private static Block registerBlock(Block block) {
        return registerBlock(block, new ItemBlock(block));
    }

}

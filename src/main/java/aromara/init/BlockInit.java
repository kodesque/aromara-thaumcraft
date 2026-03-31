package aromara.init;

import aromara.common.blocks.BlockArcaneBrazier;
import aromara.common.blocks.BlockPressingStone;
import aromara.common.blocks.BlockTranslucent;
import aromara.common.objects.TCABlocks;
import aromara.common.templates.BlockTCABase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.Thaumcraft;

public class BlockInit {

    public static void initBlocks() {
        TCABlocks.arcane_brazier = registerBlock(new BlockArcaneBrazier());
        TCABlocks.pressing_stone = registerBlock(new BlockPressingStone());
        //        TCABlocks.vishroom_block = registerBlock(new BlockVishroomHuge());

        TCABlocks.vishroom_test_head = registerBlock(new BlockTranslucent(Material.PLANTS, "vishroom_test_head"));
        TCABlocks.vishroom_test_stem = registerBlock(new BlockTCABase(Material.PLANTS, "vishroom_test_stem"));
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

package aromara.init;

import aromara.common.blocks.BlockArcaneBrazier;
import aromara.common.blocks.BlockPressingStone;
import aromara.common.blocks.BlockThaumostaticSupressor;
import aromara.common.blocks.BlockVishroomCap;
import aromara.common.blocks.BlockVishroomStem;
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

        TCABlocks.thaumostatic_supressor = registerBlock(new BlockThaumostaticSupressor());

        TCABlocks.pale_stone = registerBlock(new BlockTCABase(Material.ROCK, "pale_stone").setHardness(2));

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

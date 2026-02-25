package aromara.common.blocks;

import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TilePressingStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.IBlockEnabled;

public class BlockPressingStone extends BlockTCADevice implements IBlockEnabled{

    public static String id = "pressing_stone";

    public BlockPressingStone() {
        super(Material.ROCK, TilePressingStone.class, id);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand,
            EnumFacing facing,
            float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            TilePressingStone tile = (TilePressingStone) world.getTileEntity(pos);
            ItemStack held = player.getHeldItem(hand);

            ItemStack give;

            if (!held.isEmpty()) {

                if (held.getItem().equals(ItemsTC.casterBasic) && !state.getValue(IBlockEnabled.ENABLED)) {
                    tile.isChanging = true;
                } else if (held.getItem().equals(ItemsTC.nuggets) && held.getMetadata() == 9) {
                    if (tile.getStackInSlot(0).isEmpty()) {
                        tile.setInventorySlotContents(0, held.copy());
                        held.shrink(held.getCount());
                    }
                } else {
                    if (tile.getStackInSlot(1).isEmpty()) {
                        tile.setInventorySlotContents(1, held.copy());
                        held.shrink(held.getCount());
                    }
                }
            } else {
                if (!tile.getStackInSlot(1).isEmpty()) {
                    give = tile.getStackInSlot(1);
                    tile.removeStackFromSlot(1);
                    if (!player.addItemStackToInventory(give)) {
                        player.dropItem(give, false);
                    }
                } else if (!tile.getStackInSlot(0).isEmpty()) {
                    give = tile.getStackInSlot(0);
                    tile.removeStackFromSlot(0);
                    if (!player.addItemStackToInventory(give)) {
                        player.dropItem(give, false);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

}

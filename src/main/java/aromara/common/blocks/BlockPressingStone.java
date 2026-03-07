package aromara.common.blocks;

import aromara.common.objects.TCAItems;
import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TilePressingStone;
import aromara.root.Main;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.devices.BlockVisBattery;
import thaumcraft.common.lib.SoundsTC;

public class BlockPressingStone extends BlockTCADevice {

    public static PropertyBool IS_DOWN = PropertyBool.create("is_down");

    public static String id = "pressing_stone";

    public BlockPressingStone() {
        super(Material.ROCK, TilePressingStone.class, id);

        this.setDefaultState(this.getDefaultState().withProperty(IS_DOWN, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockPressingStone.IS_DOWN);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(BlockPressingStone.IS_DOWN, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockPressingStone.IS_DOWN) ? 1 : 0;
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

                if (held.getItem().equals(ItemsTC.casterBasic) && !state.getValue(IS_DOWN)) {
                    tile.isChanging = true;
                    tile.sendDown();
                    world.playSound(null, pos, SoundsTC.craftstart, SoundCategory.BLOCKS, 0.5f, 1.0f);
                } else if (held.getItem().equals(ItemsTC.nuggets) && held.getMetadata() == 9) {
                    if (tile.getStackInSlot(0).isEmpty()) {
                        tile.setInventorySlotContents(0, held.copy());
                        held.shrink(held.getCount());
                    }
                } else if (held.getItem().equals(TCAItems.redolent_bundle) && held.getMetadata() == 1 && held.getSubCompound(Main.MODID) != null) {
                    if (tile.getStackInSlot(1).isEmpty()) {
                        tile.setInventorySlotContents(1, held.copy());
                        held.shrink(held.getCount());
                    }
                }
            } else if (!state.getValue(IS_DOWN)){
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

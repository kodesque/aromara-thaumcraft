package aromara.common.blocks;

import java.util.List;

import javax.annotation.Nullable;

import aromara.common.objects.TCAItems;
import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TileArcaneBrazier;
import aromara.common.tiles.TileCandleVat;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.IBlockEnabled;
import thaumcraft.common.entities.EntitySpecialItem;

public class BlockCandleVat extends BlockTCADevice implements IBlockEnabled{

    public static final String id = "candle_vat";

    /* 0 -> empty, 1 -> flesh, 2 -> impure, 3 -> liquid, 4 -> rancid 5 -> imbued */

    public static PropertyInteger STATUS = PropertyInteger.create("status", 0, 5);

    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockCandleVat() {
        super(Material.IRON, TileCandleVat.class, id);

        this.setDefaultState(this.blockState.getBaseState().withProperty(STATUS, 0).withProperty(IBlockEnabled.ENABLED, true));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand,
            EnumFacing facing,
            float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            TileCandleVat tile = (TileCandleVat) world.getTileEntity(pos);
            ItemStack held = player.getHeldItem(hand);

            if (!held.isEmpty()) {

                if (held.getItem().equals(TCAItems.scent_phial)) {

                    ItemStack stack = tile.attemptScoop(held);

                    if (!player.addItemStackToInventory(stack)) {
                        player.dropItem(stack, false);
                    }

                    return true;

                }
            }
        }

        return true;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!world.isRemote) {
            TileCandleVat tile = (TileCandleVat)world.getTileEntity(pos);
            if (tile != null && entity instanceof EntityItem && !(entity instanceof EntitySpecialItem)) {
                ItemStack stack = ((EntityItem)entity).getItem();
                Item item = stack.getItem();

                /* what about actual entities?*/

                if (item.equals(Item.getItemFromBlock(BlocksTC.fleshBlock)) ||
                        item.equals(ItemsTC.salisMundus) ||
                        (item.equals(TCAItems.redolent_bundle) && stack.getMetadata() == 1)) {

                    if (tile.attemptMixIn(stack)) {
                        entity.setDead();
                    }
                }
            }
        }
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

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockCandleVat.STATUS, IBlockEnabled.ENABLED);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(BlockCandleVat.STATUS, meta & 0b111)
                .withProperty(BlockCandleVat.ENABLED, (meta & 0b1000) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(BlockCandleVat.STATUS);

        if (state.getValue(BlockCandleVat.ENABLED)) {
            meta |= 0b1000;
        }

        return meta;
    }



}

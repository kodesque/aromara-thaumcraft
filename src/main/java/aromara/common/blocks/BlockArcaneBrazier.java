package aromara.common.blocks;

import aromara.common.objects.TCAItems;
import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TileArcaneBrazier;
import aromara.root.Main;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.casters.ICaster;
import thaumcraft.common.items.casters.ItemCaster;
import thaumcraft.common.items.casters.ItemFocus;
import thaumcraft.common.items.casters.foci.FocusEffectFire;

public class BlockArcaneBrazier extends BlockTCADevice {

    public static String id = "arcane_brazier";

    public static PropertyInteger STATUS = PropertyInteger.create("status", 0, 2);

    public BlockArcaneBrazier() {
        super(Material.ROCK, TileArcaneBrazier.class, id);

        this.setDefaultState(this.getDefaultState().withProperty(STATUS, 0));
    }

    //shpuld be ignitable only with a fire-bearing lense

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand,
            EnumFacing facing,
            float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            TileArcaneBrazier tile = (TileArcaneBrazier) world.getTileEntity(pos);
            ItemStack held = player.getHeldItem(hand);

            ItemStack give;

            if (!held.isEmpty()) {

                if (held.getItem() instanceof ICaster) {
                    if (!tile.getStackInSlot(0).isEmpty() && !tile.getStackInSlot(1).isEmpty()) {
                        ItemCaster caster = (ItemCaster) held.getItem();
                        ItemStack focusStack = caster.getFocusStack(held);
                        FocusPackage pack = ItemFocus.getPackage(focusStack);

                        if (focusStack == null || pack == null)
                            return false;

                        FocusEffect[] effects = pack.getFocusEffects();

                        for (FocusEffect effect : effects) {
                            if (effect instanceof FocusEffectFire) {
                                tile.ignite();
                            }
                        }
                    }
                } else if (held.getItem().equals(Items.COAL)) {
                    if (tile.getStackInSlot(0).isEmpty()) {
                        tile.setInventorySlotContents(0, held.copy());
                        held.shrink(held.getCount());

                        world.setBlockState(pos, state.withProperty(BlockArcaneBrazier.STATUS, 1));
                    }
                } else if (held.getItem().equals(TCAItems.redolent_bundle) && held.getMetadata() == 1 && held.getSubCompound(Main.MODID) != null) {
                    if (tile.getStackInSlot(1).isEmpty()) {
                        tile.setInventorySlotContents(1, held.copy());
                        held.shrink(held.getCount());
                    }
                }
            } else if (state.getValue(STATUS) != 2) {

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

                    world.setBlockState(pos, state.withProperty(BlockArcaneBrazier.STATUS, 0));
                }

            }

            tile.markDirty();
            tile.syncTile(true);
        }

        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockArcaneBrazier.STATUS);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(BlockArcaneBrazier.STATUS, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockArcaneBrazier.STATUS);
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

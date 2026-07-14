package aromara.common.blocks;

import java.util.Random;

import aromara.common.objects.TCAItems;
import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TileServoscrivener;
import aromara.util.NBTManager;
import aromara.util.NBTManager.EnumGroups;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.common.blocks.IBlockFacingHorizontal;

public class BlockServoscrivener extends BlockTCADevice implements IBlockFacingHorizontal{

    public static final String id = "servoscrivener";

    public static PropertyBool HAS_PAPER = PropertyBool.create("has_paper");

    public BlockServoscrivener() {
        super(Material.IRON, TileServoscrivener.class, id);

        this.setDefaultState(this.blockState.getBaseState().withProperty(HAS_PAPER, false));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand,
            EnumFacing facing,
            float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            TileServoscrivener tile = (TileServoscrivener) world.getTileEntity(pos);
            ItemStack held = player.getHeldItem(hand);

            ItemStack give;

            if (!held.isEmpty()) {

                if (held.getItem().equals(TCAItems.research_brief) && NBTManager.has(held, EnumGroups.KNOWLEDGE)) {
                    if (tile.getStackInSlot(0).isEmpty()) {
                        tile.setInventorySlotContents(0, held.copy());
                        held.shrink(held.getCount());

                        tile.startResearch(world);
                    }
                }

            } else if (!tile.getStackInSlot(0).isEmpty() && player.isSneaking()) {
                give = tile.getStackInSlot(0);
                tile.removeStackFromSlot(0);
                tile.annul();
                if (!player.addItemStackToInventory(give)) {
                    player.dropItem(give, false);
                }
            } else {
                if (state.getValue(HAS_PAPER)) {
                    tile.inform(player);
                }
            }

            tile.markDirty();
            tile.syncTile(true);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(HAS_PAPER)) {

            for (int i = 0; i < 4; i++) {
                FXDispatcher.INSTANCE.blockRunes(pos.getX(), pos.getY() + 0.25, pos.getZ(), 0.3f + world.rand.nextFloat() * 0.7f, 0.0f, 0.3f + world.rand.nextFloat() * 0.7f, 15, 0.03f);
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

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HAS_PAPER, IBlockFacingHorizontal.FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        boolean active = (meta & 4) != 0;

        return this.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(HAS_PAPER, active);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(HAS_PAPER)) {
            meta |= 4;
        }

        return meta;
    }

}

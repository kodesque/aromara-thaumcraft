package aromara.common.blocks;

import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TileArcaneBrazier;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockArcaneBrazier extends BlockTCADevice {

    public static String id = "arcane_brazier";

    public static PropertyBool IS_FULL = PropertyBool.create("is_full");

    public BlockArcaneBrazier() {
        super(Material.ROCK, TileArcaneBrazier.class, id);

        this.setDefaultState(this.getDefaultState().withProperty(IS_FULL, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockArcaneBrazier.IS_FULL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(BlockArcaneBrazier.IS_FULL, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockArcaneBrazier.IS_FULL) ? 1 : 0;
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

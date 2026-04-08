package aromara.common.blocks;

import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TileThaumostaticSupressor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import thaumcraft.common.blocks.IBlockFacing;

public class BlockThaumostaticSupressor extends BlockTCADevice implements IBlockFacing {

    public static PropertyBool UPPER_PART = PropertyBool.create("upper_part");

    public static String name = "thaumostatic_supressor";

    public BlockThaumostaticSupressor() {
        super(Material.IRON, TileThaumostaticSupressor.class, name);
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, UPPER_PART);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(UPPER_PART, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(UPPER_PART) ? 1 : 0;
    }



}

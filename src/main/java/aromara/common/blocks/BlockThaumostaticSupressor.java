package aromara.common.blocks;

import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TileThaumostaticSupressor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.IBlockFacing;

public class BlockThaumostaticSupressor extends BlockTCADevice implements IBlockFacing {

    public static PropertyBool UPPER_PART = PropertyBool.create("upper_part");

    public static String id = "thaumostatic_supressor";

    public BlockThaumostaticSupressor() {
        super(Material.IRON, TileThaumostaticSupressor.class, id);

        this.setDefaultState(this.getDefaultState().withProperty(UPPER_PART, false));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    //the reason why this doesn't work as it should is cuz OBJ cannot save the right UV coords for some reason
    //splitting textures is required

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
        return new BlockStateContainer(this, new IProperty[] {FACING, UPPER_PART});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byIndex(meta & 7); // 0-7
        boolean active = (meta & 8) != 0;

        return this.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(UPPER_PART, active);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;

        meta |= state.getValue(FACING).getIndex();

        if (state.getValue(UPPER_PART)) {
            meta |= 8;
        }

        return meta;
    }



}

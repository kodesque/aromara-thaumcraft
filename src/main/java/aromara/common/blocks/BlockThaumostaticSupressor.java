package aromara.common.blocks;

import aromara.common.templates.BlockTCADevice;
import aromara.common.tiles.TileThaumostaticSupressor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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

    //TODO: how one does even get enough data?

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock().isReplaceable(worldIn, pos.offset(EnumFacing.UP));
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (state.getValue(UPPER_PART)) {
            worldIn.destroyBlock(pos.offset(state.getValue(FACING).getOpposite()), false);
        } else {
            worldIn.destroyBlock(pos.offset(state.getValue(FACING)), false);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {

        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.offset(state.getValue(FACING)), state.withProperty(UPPER_PART, true));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    //    @Override
    //    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    //        return BlockFaceShape.UNDEFINED;
    //    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, UPPER_PART});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byIndex(meta & 7);
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

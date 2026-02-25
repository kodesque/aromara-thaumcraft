package aromara.common.templates;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.Thaumcraft;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.blocks.BlockTCTile;
import thaumcraft.common.lib.utils.InventoryUtils;

public class BlockTCATile extends BlockTCABase implements ITileEntityProvider{

    protected Class<? extends TileEntity> tileClass;
    protected static boolean keepInventory;
    protected static boolean spillEssentia;

    public BlockTCATile(Material mat, Class<? extends TileEntity> tc, String name) {
        super(mat, name);
        this.setHardness(2.0f);
        this.setResistance(20.0f);
        this.tileClass = tc;
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if (this.tileClass == null)
            return null;
        try {
            return this.tileClass.newInstance();
        }
        catch (InstantiationException e) {
            Thaumcraft.log.catching(e);
        }
        catch (IllegalAccessException e2) {
            Thaumcraft.log.catching(e2);
        }
        return null;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        InventoryUtils.dropItems(worldIn, pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity != null && tileentity instanceof IEssentiaTransport && BlockTCATile.spillEssentia && !worldIn.isRemote) {
            int ess = ((IEssentiaTransport)tileentity).getEssentiaAmount(EnumFacing.UP);
            if (ess > 0) {
                AuraHelper.polluteAura(worldIn, pos, ess, true);
            }
        }
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    static {
        BlockTCATile.keepInventory = false;
        BlockTCATile.spillEssentia = true;
    }
}

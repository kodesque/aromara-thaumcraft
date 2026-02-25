package aromara.common.templates;

import aromara.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTCABase extends Block{

    public BlockTCABase(Material material, String name) {
        super(material);
        this.setRegistryName(Main.MODID, name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.TABTCA);
    }

    public BlockTCABase(Material mat, String name, SoundType st) {
        this(mat, name);
        this.setSoundType(st);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1, 0));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

}

package aromara.root;

import aromara.common.objects.TCAItems;
import aromara.common.worldgen.WorldGenVishroomHuge;
import aromara.init.EntityInit;
import aromara.init.ResearchInit;
import aromara.init.TileInit;
import aromara.network.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.research.ScanBlockState;
import thaumcraft.api.research.ScanEntity;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;

@Mod(modid = Main.MODID, dependencies = "required-after:thaumcraft", version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MODID = "aromara";
    public static final String NAME = "Aromara Thaumcraft";
    public static final String VERSION = "0.1.4-ALPHA";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "aromara.network.proxy.ClientProxy", serverSide = "aromara.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        proxy.preInit(event);

        TileInit.preInitTiles();
        EntityInit.preInitEntities();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        ResearchInit.initResearch();

        GameRegistry.registerWorldGenerator(new WorldGenVishroomHuge(), 3);

        ScanningManager.addScannableThing(new ScanEntity("!HOG", EntityPig.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!BLAZE", EntityBlaze.class, true));
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    public static CreativeTabs TABTCA = new CreativeTabs("tabAromara") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(TCAItems.debug);
        }
    };
}

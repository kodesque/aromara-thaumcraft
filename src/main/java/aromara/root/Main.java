package aromara.root;

import aromara.common.objects.TCAItems;
import aromara.common.worldgen.WorldGenVishroomHuge;
import aromara.init.TileInit;
import aromara.network.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanBlockState;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;
import thecodex6824.thaumicaugmentation.api.TABlocks;
import thecodex6824.thaumicaugmentation.api.TAItems;
import thecodex6824.thaumicaugmentation.api.block.property.IAltarBlock;
import thecodex6824.thaumicaugmentation.api.block.property.IObeliskType;
import thecodex6824.thaumicaugmentation.api.block.property.IObeliskType.ObeliskType;

@Mod(modid = Main.MODID, dependencies = "required-after:thaumcraft; required-after:thaumicaugmentation", version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MODID = "aromara";
    public static final String NAME = "Aromara Thaumcraft";
    public static final String VERSION = "0.0.1-INDEV";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "aromara.network.proxy.ClientProxy", serverSide = "aromara.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        TileInit.initTiles();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        ResearchCategories.registerCategory(
                "SCENTMIXING",
                "FIRSTSTEPS",
                new AspectList().add(Aspect.ALCHEMY, 5),
                new ResourceLocation(Main.MODID + ":textures/research/" + "basescentmixing" + ".png"),
                new ResourceLocation(Main.MODID + ":textures/research/" + "background.png"),
                new ResourceLocation(Main.MODID + ":textures/research/" + "background_overlay.png")
                );

        registerResearchLocation(new ResourceLocation("aromara:research/scentmixing"));

        GameRegistry.registerWorldGenerator(new WorldGenVishroomHuge(), 3);

        ScanningManager.addScannableThing(new ScanBlockState("!EYES", TABlocks.CAPSTONE.getDefaultState().withProperty(IObeliskType.OBELISK_TYPE, ObeliskType.ELDRITCH).withProperty(
                IAltarBlock.ALTAR, true), true));
        ScanningManager.addScannableThing(new ScanItem("!KEY", new ItemStack (TAItems.ELDRITCH_LOCK_KEY)));
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    public static CreativeTabs TABTCA = new CreativeTabs("tabAromara") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(TCAItems.icon);
        }
    };

    public static void registerResearchLocation(ResourceLocation loc) {
        if (!CommonInternals.jsonLocs.containsKey(loc.toString())) {
            CommonInternals.jsonLocs.put(loc.toString(), loc);
        }
    }
}

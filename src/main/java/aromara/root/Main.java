package aromara.root;

import aromara.common.objects.TCAItems;
import aromara.common.worldgen.WorldGenVishroomHuge;
import aromara.events.front.GenericEventHandler;
import aromara.init.TileInit;
import aromara.network.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(modid = Main.MODID, dependencies = "required-after:thaumcraft", version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MODID = "aromara";
    public static final String NAME = "Aromara Thaumcraft";
    public static final String VERSION = "0.0.1-INDEV";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "aromara.network.proxy.ClientProxy", serverSide = "aromara.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new GenericEventHandler());

        TileInit.initTiles();
        proxy.preInit(event);

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        ResearchCategories.registerCategory(
                "SCENTMIXING",
                "FIRSTSTEPS",
                new AspectList().add(Aspect.ALCHEMY, 5).add(Aspect.SENSES, 5),
                new ResourceLocation(Main.MODID + ":textures/research/" + "scentmixing" + ".png"),
                new ResourceLocation(Main.MODID + ":textures/research/" + "background.png")
                );

        GameRegistry.registerWorldGenerator(new WorldGenVishroomHuge(), 3);

        registerResearchLocation(new ResourceLocation("aromara:research/scentmixing"));
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

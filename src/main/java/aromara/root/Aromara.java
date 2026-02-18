package aromara.root;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import aromara.events.front.GenericEventHandler;
import aromara.network.proxy.CommonProxy;

@Mod(modid = Aromara.MODID, dependencies = "required-after:thaumcraft", version = Aromara.VERSION, name = Aromara.NAME)
public class Aromara {
    public static final String MODID = "aromara";
    public static final String NAME = "Aromara Thaumcraft";
    public static final String VERSION = "0.0.1-INDEV";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "aromara.network.proxy.ClientProxy", serverSide = "aromara.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void load(FMLInitializationEvent event) {
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // NO-OP
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new GenericEventHandler());

    }

    public static CreativeTabs TABTCA = new CreativeTabs("tabAromara") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };
}

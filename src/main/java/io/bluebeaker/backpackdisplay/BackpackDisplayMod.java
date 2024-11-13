package io.bluebeaker.backpackdisplay;

import org.apache.logging.log4j.Logger;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BackpackDisplayMod.MODID, name = BackpackDisplayMod.NAME, version = BackpackDisplayMod.VERSION,clientSideOnly = true)
public class BackpackDisplayMod
{
    public static final String MODID = "backpackdisplay";
    public static final String NAME = "BackpackDisplay";
    public static final String VERSION = "1.4";
    private static BackpackDisplayMod INSTANCE;
    public MinecraftServer server;

    private static Logger logger;
    
    public BackpackDisplayMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BPDTooltip.class);
        BackpackDisplayMod.INSTANCE=this;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        BPDConfigHelper.updateConfig();
        ClientRegistry.registerKeyBinding(Keybind.keyShowContents);
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
            BPDConfigHelper.updateConfig();
        }
    }
    public static Logger getLogger(){
        return logger;
    }
    public static void logInfo(String log){
        if (logger!=null)
        logger.info(log);
    }
    public static void logError(String log){
        if (logger!=null)
        logger.error(log);
    }
}

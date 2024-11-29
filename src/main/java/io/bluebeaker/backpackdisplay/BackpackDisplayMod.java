package io.bluebeaker.backpackdisplay;

import org.apache.logging.log4j.Logger;

import io.bluebeaker.backpackdisplay.section.DisplaySectionDummy;
import io.bluebeaker.backpackdisplay.section.DisplaySectionItem;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,clientSideOnly = true)
public class BackpackDisplayMod
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
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
        SectionsManager.addSection(new DisplaySectionItem());
        
        // Add dummy sections for debugging priority
        // SectionsManager.addSection(new DisplaySectionDummy("dummy"));
        // SectionsManager.addSection(new DisplaySectionDummy("dummy2"));
    }

    @EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event){
        SectionsManager.updateConfig();
        SectionsManager.sortSections();
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

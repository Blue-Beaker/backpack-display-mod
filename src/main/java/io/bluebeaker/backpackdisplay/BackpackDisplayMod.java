package io.bluebeaker.backpackdisplay;

import org.apache.logging.log4j.Logger;

import mezz.jei.gui.TooltipRenderer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BackpackDisplayMod.MODID, name = BackpackDisplayMod.NAME, version = BackpackDisplayMod.VERSION,clientSideOnly = true)
public class BackpackDisplayMod
{
    public static final String MODID = "backpackdisplay";
    public static final String NAME = "BackpackDisplay";
    public static final String VERSION = "1.0";
    private static BackpackDisplayMod INSTANCE;
    public MinecraftServer server;

    private static Logger logger;
    
    public BackpackDisplayMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BackpackDisplayTooltip.class);
        BackpackDisplayMod.INSTANCE=this;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event) {
        BackpackDisplayRegistry.updateFromConfig();
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
            BackpackDisplayRegistry.updateFromConfig();
        }
    }

    public static void logInfo(String log){
        if (logger!=null)
        logger.info(log);
    }
}

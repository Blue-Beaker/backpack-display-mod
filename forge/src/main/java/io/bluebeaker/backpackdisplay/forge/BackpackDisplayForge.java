package io.bluebeaker.backpackdisplay.forge;

import dev.architectury.platform.forge.EventBuses;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.Keybind;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BackpackDisplayMod.MOD_ID)
public class BackpackDisplayForge {
    public BackpackDisplayForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(BackpackDisplayMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BackpackDisplayMod.init();
    }
    @SubscribeEvent
    public void init(FMLCommonSetupEvent event){
//        MinecraftForge.EVENT_BUS.register(BPDTooltipForge.class);
    }
    @SubscribeEvent
    public void registerBindings(RegisterKeyMappingsEvent event){
        event.register(Keybind.keyShowContents);
    }
}

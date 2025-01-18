package io.bluebeaker.backpackdisplay.forge;

import dev.architectury.platform.forge.EventBuses;
import io.bluebeaker.backpackdisplay.BPDConfig;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.Keybind;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BackpackDisplayMod.MOD_ID)
public class BackpackDisplayForge {
    public BackpackDisplayForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(BackpackDisplayMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BackpackDisplayMod.init();
        BackpackDisplayForge.registerConfigScreen();
    }
    @SubscribeEvent
    public void init(FMLCommonSetupEvent event){
    }
    @SubscribeEvent
    public void registerBindings(RegisterKeyMappingsEvent event){
        event.register(Keybind.keyShowContents);
    }

    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> AutoConfig.getConfigScreen(BPDConfig.class, parent).get()));
    }
}

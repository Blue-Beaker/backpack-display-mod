package io.bluebeaker.backpackdisplay.forge;

import dev.architectury.platform.forge.EventBuses;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BackpackDisplayMod.MOD_ID)
public class BackpackDisplayForge {
    public BackpackDisplayForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(BackpackDisplayMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BackpackDisplayMod.init();
    }
}

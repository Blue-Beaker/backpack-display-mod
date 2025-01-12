package io.bluebeaker.backpackdisplay.fabric;

import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import net.fabricmc.api.ModInitializer;

public class BackpackDisplayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BackpackDisplayMod.init();
    }
}

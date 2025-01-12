package io.bluebeaker.backpackdisplay.utils;

import dev.architectury.platform.Platform;

public class EnvironmentUtils {
    public static boolean isCraftTweakerLoaded() {
        return Platform.isModLoaded("crafttweaker");
    }
}

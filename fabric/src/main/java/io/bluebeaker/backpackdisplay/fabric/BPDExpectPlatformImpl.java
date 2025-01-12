package io.bluebeaker.backpackdisplay.fabric;

import io.bluebeaker.backpackdisplay.BPDExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class BPDExpectPlatformImpl {
    /**
     * This is our actual method to {@link BPDExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}

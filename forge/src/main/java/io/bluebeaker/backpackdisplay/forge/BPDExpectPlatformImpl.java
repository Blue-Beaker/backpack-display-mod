package io.bluebeaker.backpackdisplay.forge;

import io.bluebeaker.backpackdisplay.BPDExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class BPDExpectPlatformImpl {
    /**
     * This is our actual method to {@link BPDExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}

package io.bluebeaker.backpackdisplay;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;

public class Keybind {
    public static KeyMapping keyShowContents = new KeyMapping("key.show_backpack_contents",InputConstants.Type.SCANCODE,44,"Backpack Display");
}

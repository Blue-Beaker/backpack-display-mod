package io.bluebeaker.backpackdisplay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;

public class Keybind {
    public static KeyBinding keyShowContents = new KeyBinding("key.show_backpack_contents",KeyConflictContext.GUI, 44, "Backpack Display");
    static enum NullKeyConflictContext implements IKeyConflictContext{
        GUI {
            @Override
            public boolean isActive() {
                return Minecraft.getMinecraft().currentScreen != null;
            }
    
            @Override
            public boolean conflicts(IKeyConflictContext other) {
                return false;
            }
    
        }
    }
}

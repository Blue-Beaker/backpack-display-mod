package io.bluebeaker.backpackdisplay;

import io.bluebeaker.backpackdisplay.BPDConfig.Colors;

public class BPDConfigHelper {
    
    public static int backgroundColor = 0xF0100010;
    public static int borderColorStart = 0x505000FF;

    public static void updateConfig(){
        updateColor();
        BPDRegistry.updateFromConfig();
    }

    public static void updateColor(){
        Colors colors = BPDConfig.colors;
        backgroundColor=colors.backgroundColor.getColor();
        borderColorStart=colors.borderColor.getColor();
    }
}

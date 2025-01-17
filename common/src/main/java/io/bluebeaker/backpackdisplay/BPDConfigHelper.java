package io.bluebeaker.backpackdisplay;

import io.bluebeaker.backpackdisplay.BPDConfig.Visuals;
import io.bluebeaker.backpackdisplay.section.fluid.BPDRegistryFluid;
import io.bluebeaker.backpackdisplay.section.item.BPDRegistryItems;

public class BPDConfigHelper {
    
    public static int backgroundColor = 0xF0100010;
    public static int borderColorStart = 0x505000FF;

    public static void updateConfig(){
        updateColor();
        BPDRegistryItems.updateFromConfig();
        BPDRegistryFluid.updateFromConfig();
        SectionsManager.updateFromConfig();
    }

    public static void updateColor(){
        Visuals visuals = ConfigProvider.getInstance().visuals;
        backgroundColor= visuals.backgroundColor.getColor();
        borderColorStart= visuals.borderColor.getColor();
    }
}

package io.bluebeaker.backpackdisplay.api;

import io.bluebeaker.backpackdisplay.SectionsManager;

public class Sections {
    /**Use this to add your custom section to the tooltip. */
    public static void addSection(IDisplaySection newSection){
        SectionsManager.addSection(newSection);
    }
}

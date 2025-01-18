package io.bluebeaker.backpackdisplay;

import org.jetbrains.annotations.Nullable;

public class ConfigProvider {
    @Nullable
    private static BPDConfig INSTANCE = null;

    public static boolean isConfigLoaded(){
        return INSTANCE!=null;
    }

    public static BPDConfig getConfig(){
        if(INSTANCE==null)
            throw new NullPointerException();
        return INSTANCE;
    }

    public static void setConfig(BPDConfig config){
        INSTANCE = config;
    }
}

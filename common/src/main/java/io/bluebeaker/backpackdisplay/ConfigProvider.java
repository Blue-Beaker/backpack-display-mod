package io.bluebeaker.backpackdisplay;

import org.jetbrains.annotations.Nullable;

public class ConfigProvider {
    @Nullable
    private static BPDConfig INSTANCE = null;

    public static BPDConfig getInstance(){
        if(INSTANCE==null)
            throw new AssertionError();
        return INSTANCE;
    }

    public static void setInstance(BPDConfig config){
        INSTANCE = config;
    }
}

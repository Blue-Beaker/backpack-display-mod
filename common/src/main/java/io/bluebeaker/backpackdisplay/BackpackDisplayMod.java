package io.bluebeaker.backpackdisplay;

import io.bluebeaker.backpackdisplay.section.fluid.DisplaySectionFluid;
import io.bluebeaker.backpackdisplay.section.item.DisplaySectionItem;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.InteractionResult;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BackpackDisplayMod {
    public static final String MOD_ID = "backpackdisplay";

    public static void init() {

        AutoConfig.register(BPDConfig.class, Toml4jConfigSerializer::new);
        ConfigHolder<BPDConfig> configHolder = AutoConfig.getConfigHolder(BPDConfig.class);
        configHolder.registerSaveListener(BackpackDisplayMod::onSave);
        ConfigProvider.setConfig(configHolder.getConfig());

        SectionsManager.addSection(new DisplaySectionItem());
        SectionsManager.addSection(new DisplaySectionFluid());

        BPDConfigHelper.updateConfig();
        SectionsManager.updateToConfig();
        SectionsManager.sortSections();

        BPDTooltipCommon.INSTANCE.register();
    }

    private static InteractionResult onSave(ConfigHolder<BPDConfig> bpdConfigConfigHolder, BPDConfig bpdConfig) {
        onConfigReload();
        return InteractionResult.PASS;
    }

    public static void onConfigReload(){
        if(!ConfigProvider.isConfigLoaded())
            return;
        BPDConfigHelper.updateConfig();
        SectionsManager.updateToConfig();
        SectionsManager.sortSections();
    }

    public static void logError(String str,Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        logError(str+"\n"+sw);
    }
    public static void logError(String str){
        System.out.println("[ERROR][BackpackDisplay]"+str);
    }
    public static void logInfo(String str){
        System.out.println("[INFO][BackpackDisplay]"+str);
    }
}

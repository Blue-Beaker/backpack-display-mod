package io.bluebeaker.backpackdisplay;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import io.bluebeaker.backpackdisplay.section.fluid.DisplaySectionFluid;
import io.bluebeaker.backpackdisplay.section.item.DisplaySectionItem;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

import java.util.function.Supplier;

public class BackpackDisplayMod {
    public static final String MOD_ID = "backpackdisplay";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static void init() {

        AutoConfig.register(BPDConfig.class, Toml4jConfigSerializer::new);
        ConfigProvider.setConfig(AutoConfig.getConfigHolder(BPDConfig.class).getConfig());

        SectionsManager.addSection(new DisplaySectionItem());
        SectionsManager.addSection(new DisplaySectionFluid());

        BPDConfigHelper.updateConfig();
        SectionsManager.updateToConfig();
        SectionsManager.sortSections();

        BPDTooltipCommon.INSTANCE.register();
    }

    public static void onConfigReload(){
        if(!ConfigProvider.isConfigLoaded())
            return;
        BPDConfigHelper.updateConfig();
        SectionsManager.updateToConfig();
        SectionsManager.sortSections();
    }

    public static void logError(String str,Throwable e){
        logError(str+e.toString());
    }
    public static void logError(String str){
        System.out.println("[ERROR][BackpackDisplay]"+str);
    }
    public static void logInfo(String str){
        System.out.println("[INFO][BackpackDisplay]"+str);
    }
}

package io.bluebeaker.backpackdisplay;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import io.bluebeaker.backpackdisplay.section.fluid.DisplaySectionFluid;
import io.bluebeaker.backpackdisplay.section.item.DisplaySectionItem;

import java.util.function.Supplier;

public class BackpackDisplayMod {
    public static final String MOD_ID = "backpackdisplay";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static void init() {
        System.out.println(BPDExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());

        BPDConfigHelper.updateConfig();
        SectionsManager.addSection(new DisplaySectionItem());
        SectionsManager.addSection(new DisplaySectionFluid());
        SectionsManager.updateConfig();
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

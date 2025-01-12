package io.bluebeaker.backpackdisplay;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;

import java.util.function.Supplier;

public class BackpackDisplayMod {
    public static final String MOD_ID = "backpackdisplay";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    
    public static void init() {
        
        System.out.println(BPDExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}

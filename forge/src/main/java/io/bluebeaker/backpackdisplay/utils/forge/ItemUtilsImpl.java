package io.bluebeaker.backpackdisplay.utils.forge;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemUtilsImpl {
    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(BackpackDisplayMod.MOD_ID));

    public static Item getItemFromID(ResourceLocation id){
        return MANAGER.get().get(Registries.ITEM).get(id);
    }
}

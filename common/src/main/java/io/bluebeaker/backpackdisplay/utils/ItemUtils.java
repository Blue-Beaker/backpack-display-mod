package io.bluebeaker.backpackdisplay.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemUtils {
    public static Item getItemFromID(ResourceLocation id){
        return BuiltInRegistries.ITEM.get(id);
    }
}

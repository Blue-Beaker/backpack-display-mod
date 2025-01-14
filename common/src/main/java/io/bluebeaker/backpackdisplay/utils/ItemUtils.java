package io.bluebeaker.backpackdisplay.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemUtils {
    @ExpectPlatform
    public static Item getItemFromID(ResourceLocation id){
        return BuiltInRegistries.ITEM.get(id);
    }
    public static ItemStack createStackFromNBT(CompoundTag tag){
        Tag id = tag.get("id");
        if(id instanceof StringTag){
            ResourceLocation resourceID = new ResourceLocation(id.getAsString());
            return new ItemStack(getItemFromID(resourceID));
        }
        return ItemStack.EMPTY;
    }
}

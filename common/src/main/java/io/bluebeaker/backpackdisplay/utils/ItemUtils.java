package io.bluebeaker.backpackdisplay.utils;

import com.google.common.base.Suppliers;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrarManager;
import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ItemUtils {
    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(BackpackDisplayMod.MOD_ID));

    public static @Nullable Item getItemFromID(ResourceLocation id){
        return MANAGER.get().get(Registries.ITEM).get(id);
    }
//    @ExpectPlatform
//    public static Item getItemFromID(ResourceLocation id){
//        return BuiltInRegistries.ITEM.get(id);
//    }
    public static ItemStack createStackFromNBT(CompoundTag tag){
        Tag id = tag.get("id");
        if(id instanceof StringTag){
            ResourceLocation resourceID = new ResourceLocation(id.getAsString());
            Item item = getItemFromID(resourceID);
            if(item==null)
                return ItemStack.EMPTY;

            return new ItemStack(item);
        }
        return ItemStack.EMPTY;
    }
}

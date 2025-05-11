package io.bluebeaker.backpackdisplay.utils;

import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.Nullable;

public class NBTUtils {

    /**
     * @param pathStr: A string representing the path to the required value in NBT, splitted with dots.
     * @return      The path to the value in a list
     */
    public static String[] getKeysList(String pathStr){
        return pathStr.split("(?<!\\\\)\\.");
    }

    /**
     * @param root The NBT tag to get value from
     * @param path The path to the value in a list
     * @return     The value
     */
    public static @Nullable Tag getTagRecursive(Tag root, String[] path){
        Tag tag = root;
        for(String key:path){
            if(!key.isEmpty()) {
                if (tag instanceof CompoundTag)
                    tag = ((CompoundTag) tag).get(key);
                else if (tag instanceof ListTag listTag){
                    int i = Integer.parseInt(key);
                    if(i>=listTag.size()) return null;
                    tag = listTag.get(i);
                }
                else return null;
            }
        }
        return tag;
    }
    public static boolean isNumber(@Nullable Tag nbt){
        if(nbt==null) return false;
        byte i=nbt.getId();
        return i >= 1 && i <= 6;
    }
}

package io.bluebeaker.backpackdisplay.utils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nullable;

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
    public static @Nullable NBTBase getTagRecursive(NBTBase root, String[] path){
        NBTBase tag = root;
        for(String key:path){
            if(!key.isEmpty()) {
                if (tag instanceof NBTTagCompound)
                    tag = ((NBTTagCompound) tag).getTag(key);
                else if (tag instanceof NBTTagList)
                    tag = ((NBTTagList) tag).get(Integer.parseInt(key));
                else return null;
            }
        }
        return tag;
    }
    public static boolean isNumber(NBTBase nbt){
        byte i=nbt.getId();
        return i >= 1 && i <= 6;
    }
}

package io.bluebeaker.backpackdisplay;

import org.luaj.vm2.lib.PackageLib.searchpath;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTUtils {

    /**
     * @param keys: A string representing the path to the required value in NBT, splitted with dots.
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
    public static NBTBase getTagRecursive(NBTBase root, String[] path){
        NBTBase tag = root;
        for(String key:path){
            if(tag instanceof NBTTagCompound)
            tag=((NBTTagCompound)tag).getTag(key);
            else if(tag instanceof NBTTagList)
            tag=((NBTTagList)tag).get(Integer.valueOf(key));
            else return null;
        }
        return tag;
    }
    public static boolean isNumber(NBTBase nbt){
        byte i=nbt.getId();
        return i >= 1 && i <= 6;
    }
}

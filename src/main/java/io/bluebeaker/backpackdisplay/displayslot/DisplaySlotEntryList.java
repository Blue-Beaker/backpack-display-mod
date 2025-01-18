package io.bluebeaker.backpackdisplay.displayslot;

import io.bluebeaker.backpackdisplay.utils.ComparatorWithNumbers;
import io.bluebeaker.backpackdisplay.utils.NBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DisplaySlotEntryList extends DisplaySlotEntryBase {

    /**The path from nbt root to the list containing different items. */
    String[] pathToList;
    /**The path from the list to the item.     */
    String[] pathToItem = {};
    /**The path from the list to the count of the item.     */
    String[] pathToCount = {};
    public DisplaySlotEntryList(Set<Integer> metadataList,String nbtRule){
        super(metadataList,nbtRule);
        String[] splitted = nbtRule.split("(?<!\\\\);");
        pathToList = NBTUtils.getKeysList(splitted[0]);
        if(splitted.length>=2)
            pathToItem = NBTUtils.getKeysList(splitted[1]);
        if(splitted.length>=3)
            pathToCount = NBTUtils.getKeysList(splitted[2]);
    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        if(stack.getTagCompound()==null)
            return Collections.emptyList();
        List<ItemStack> output = new ArrayList<ItemStack>();
        NBTBase listRoot = NBTUtils.getTagRecursive(stack.getTagCompound(), pathToList);
        if(listRoot instanceof NBTTagList){
            NBTTagList list = ((NBTTagList)listRoot);
            for(NBTBase tag:list){
                ItemStack newStack = getSingleItem(tag);
                if(!newStack.isEmpty()){
                    output.add(newStack);
                }
            }
        }else if(listRoot instanceof NBTTagCompound){
            NBTTagCompound comp = ((NBTTagCompound)listRoot);
            Set<String> keys =comp.getKeySet();
            List<String> sortedKeys = keys.stream().sorted(new ComparatorWithNumbers()).collect(Collectors.toList());
            for(String key:sortedKeys){
                NBTBase tag = comp.getTag(key);
                ItemStack newStack = getSingleItem(tag);
                if(!newStack.isEmpty()){
                    output.add(newStack);
                }
            }
        }
        return output;
    }
    private ItemStack getSingleItem(NBTBase tag){
        NBTBase itemTag = NBTUtils.getTagRecursive(tag, pathToItem);
        if(itemTag instanceof NBTTagCompound){
            ItemStack newStack = new ItemStack((NBTTagCompound)itemTag);
            if(!newStack.isEmpty()){
                if(pathToCount.length>0){
                    NBTBase countTag = NBTUtils.getTagRecursive(tag, pathToCount);
                    if (countTag != null && NBTUtils.isNumber(countTag))
                        newStack.setCount(((NBTPrimitive) countTag).getInt());
                }
                return newStack;
            }
        }
        return ItemStack.EMPTY;
    }
}

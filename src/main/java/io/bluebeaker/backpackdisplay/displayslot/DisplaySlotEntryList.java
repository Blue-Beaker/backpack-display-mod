package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.bluebeaker.backpackdisplay.utils.ComparatorWithNumbers;
import io.bluebeaker.backpackdisplay.utils.NBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class DisplaySlotEntryList extends DisplaySlotEntryBase {

    /**The path from nbt root to the list containing different items. */
    String[] pathToList = {};
    /**The path from the list to the item.     */
    String[] pathToItem = {};
    /**The path from the list to the count of the item.     */
    String[] pathToCount = null;
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
        List<ItemStack> output = new ArrayList<ItemStack>();
        NBTBase listRoot = NBTUtils.getTagRecursive(stack.getTagCompound(), pathToList);
        if(listRoot instanceof NBTTagList){
            NBTTagList list = ((NBTTagList)listRoot);
            for(NBTBase tag:list){
                ItemStack newStack = getSingleItem(tag);
                if(newStack!=null && !newStack.isEmpty()){
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
                if(newStack!=null && !newStack.isEmpty()){
                    output.add(newStack);
                }
            }
        }
        return output;
    }
    private ItemStack getSingleItem(NBTBase tag){
        NBTBase itemTag = NBTUtils.getTagRecursive(tag, pathToItem);
        if(itemTag instanceof NBTTagCompound){
            ItemStack newStack = new ItemStack((NBTTagCompound)tag);
            if(!newStack.isEmpty()){
                if(pathToCount!=null){
                    NBTBase countTag = NBTUtils.getTagRecursive(tag, pathToCount);
                    if(NBTUtils.isNumber(countTag))
                    newStack.setCount(((NBTPrimitive)countTag).getInt());
                }
                return newStack;
            }
        }
        return null;
    }
}

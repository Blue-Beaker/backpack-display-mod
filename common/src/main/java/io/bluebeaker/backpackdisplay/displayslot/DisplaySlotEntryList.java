package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.bluebeaker.backpackdisplay.utils.ComparatorWithNumbers;
import io.bluebeaker.backpackdisplay.utils.NBTUtils;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class DisplaySlotEntryList extends DisplaySlotEntryBase {

    /**The path from nbt root to the list containing different items. */
    String[] pathToList = {};
    /**The path from the list to the item.     */
    String[] pathToItem = {};
    /**The path from the list to the count of the item.     */
    @Nullable String[] pathToCount = null;
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
        Tag listRoot = NBTUtils.getTagRecursive(stack.getTag(), pathToList);
        if(listRoot instanceof ListTag){
            ListTag list = ((ListTag)listRoot);
            for(Tag tag:list){
                ItemStack newStack = getSingleItem(tag);
                if(newStack!=null && !newStack.isEmpty()){
                    output.add(newStack);
                }
            }
        }else if(listRoot instanceof CompoundTag){
            CompoundTag comp = ((CompoundTag)listRoot);
            Set<String> keys =comp.getAllKeys();
            List<String> sortedKeys = keys.stream().sorted(new ComparatorWithNumbers()).collect(Collectors.toList());
            for(String key:sortedKeys){
                Tag tag = comp.get(key);
                ItemStack newStack = getSingleItem(tag);
                if(newStack!=null && !newStack.isEmpty()){
                    output.add(newStack);
                }
            }
        }
        return output;
    }
    private @Nullable ItemStack getSingleItem(Tag tag){
        Tag itemTag = NBTUtils.getTagRecursive(tag, pathToItem);
        if(itemTag instanceof CompoundTag){
            ItemStack newStack = new ItemStack((ItemLike) itemTag);
            if(!newStack.isEmpty()){
                if(pathToCount!=null){
                    Tag countTag = NBTUtils.getTagRecursive(tag, pathToCount);
                    if(countTag instanceof NumericTag)
                        newStack.setCount(((NumericTag)countTag).getAsInt());
                }
                return newStack;
            }
        }
        return null;
    }
}

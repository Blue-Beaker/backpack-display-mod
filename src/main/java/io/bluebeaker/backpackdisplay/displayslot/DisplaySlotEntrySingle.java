package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.bluebeaker.backpackdisplay.NBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;

public class DisplaySlotEntrySingle extends DisplaySlotEntryBase {
    /**The path from the list to the item.     */
    String[] pathToItem = {};
    /**The path from the list to the count of the item.     */
    String[] pathToCount = null;
    public DisplaySlotEntrySingle(Set<Integer> metadataList,String nbtRule){
        super(metadataList);
        String[] splitted = nbtRule.split("(?<!\\\\);");
        pathToItem = NBTUtils.getKeysList(splitted[0]);
        if(splitted.length>=2)
        pathToCount = NBTUtils.getKeysList(splitted[1]);
    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        List<ItemStack> output = new ArrayList<ItemStack>();
        NBTTagCompound itemNBT = stack.getTagCompound();
        NBTBase itemTag = NBTUtils.getTagRecursive(itemNBT, pathToItem);
        if(itemTag instanceof NBTTagCompound){
            ItemStack newStack = new ItemStack((NBTTagCompound)itemTag);
            if(!newStack.isEmpty()){
                if(pathToCount!=null){
                    NBTBase countTag = NBTUtils.getTagRecursive(itemNBT, pathToCount);
                    if(NBTUtils.isNumber(countTag))
                    newStack.setCount(((NBTPrimitive)countTag).getInt());
                }
                output.add(newStack);
            }
        }
        return output;
    }
}

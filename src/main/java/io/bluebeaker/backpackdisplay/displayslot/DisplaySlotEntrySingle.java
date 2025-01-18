package io.bluebeaker.backpackdisplay.displayslot;

import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.utils.NBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DisplaySlotEntrySingle extends DisplaySlotEntryBase {
    /**The path from the list to the item.     */
    String[] pathToItem;
    /**The path from the list to the count of the item.     */
    String[] pathToCount = {};
    List<ValueOperation> operations = Collections.emptyList();
    public DisplaySlotEntrySingle(Set<Integer> metadataList,String nbtRule){
        super(metadataList,nbtRule);
        String[] splitted = nbtRule.split("(?<!\\\\);",3);
        pathToItem = NBTUtils.getKeysList(splitted[0]);
        if(splitted.length>=2)
            pathToCount = NBTUtils.getKeysList(splitted[1]);
        if(splitted.length>=3){
            this.operations=new ArrayList<>();
            for(String path:splitted[2].split("(?<!\\\\);", 0)){
                ValueOperation newOperation = new ValueOperation(path);
                if(newOperation.operator==null){
                    BackpackDisplayMod.logError("Error parsing operation +'"+path+"'");
                }else{
                    this.operations.add(newOperation);
                }
            }
        }
    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        NBTTagCompound rootNBT = stack.getTagCompound();
        if(rootNBT==null)
            return Collections.emptyList();
        NBTBase itemTag = NBTUtils.getTagRecursive(rootNBT, pathToItem);
        if(!(itemTag instanceof NBTTagCompound)){
            return Collections.emptyList();
        }
        ItemStack newStack = new ItemStack((NBTTagCompound)itemTag);
        if(newStack.isEmpty()){
            return Collections.emptyList();
        }
        if(pathToCount.length>0){
            NBTBase countTag = NBTUtils.getTagRecursive(rootNBT, pathToCount);
            if (countTag != null && NBTUtils.isNumber(countTag)) {
                int count = ((NBTPrimitive) countTag).getInt();
                if (operations != null) {
                    for (ValueOperation operation : operations) {
                        count = operation.doOperation(count, rootNBT);
                    }
                }
                newStack.setCount(count);
            }
        }
        return Collections.singletonList(newStack);
    }
}

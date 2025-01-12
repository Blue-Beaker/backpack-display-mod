package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.utils.NBTUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ItemLike;

public class DisplaySlotEntrySingle extends DisplaySlotEntryBase {
    /**The path from the list to the item.     */
    String[] pathToItem = {};
    /**The path from the list to the count of the item.     */
    String[] pathToCount = null;
    List<ValueOperation> operations = null;
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
        List<ItemStack> output = new ArrayList<ItemStack>();
        CompoundTag rootNBT = stack.getTag();
        Tag itemTag = NBTUtils.getTagRecursive(rootNBT, pathToItem);
        if(itemTag instanceof CompoundTag){
            ItemStack newStack = new ItemStack((ItemLike) itemTag);
            if(!newStack.isEmpty()){
                if(pathToCount!=null){
                    Tag countTag = NBTUtils.getTagRecursive(rootNBT, pathToCount);
                    if(NBTUtils.isNumber(countTag)){
                        int count = ((NumericTag)countTag).getAsInt();
                            if(operations!=null){
                                for(ValueOperation operation:operations){
                                    count=operation.doOperation(count, rootNBT);
                                }
                            }
                        newStack.setCount(count);
                    }
                }
                output.add(newStack);
            }
        }
        return output;
    }
}

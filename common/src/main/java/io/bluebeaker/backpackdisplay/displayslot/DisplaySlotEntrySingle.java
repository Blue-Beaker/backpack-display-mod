package io.bluebeaker.backpackdisplay.displayslot;

import io.bluebeaker.backpackdisplay.BackpackDisplayMod;
import io.bluebeaker.backpackdisplay.utils.ItemUtils;
import io.bluebeaker.backpackdisplay.utils.NBTUtils;
import io.bluebeaker.backpackdisplay.utils.ValueOperator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplaySlotEntrySingle extends DisplaySlotEntryBase {
    /**The path from the list to the item.     */
    String[] pathToItem = {};
    /**The path from the list to the count of the item.     */
    String[] pathToCount = {};
    List<ValueOperation> operations = Collections.emptyList();
    public DisplaySlotEntrySingle(String nbtRule){
        super(nbtRule);
        String[] splitted = nbtRule.split("(?<!\\\\);",3);
        pathToItem = NBTUtils.getKeysList(splitted[0]);
        if(splitted.length>=2)
            pathToCount = NBTUtils.getKeysList(splitted[1]);
        if(splitted.length>=3){
            this.operations=new ArrayList<>();
            for(String path:splitted[2].split("(?<!\\\\);", 0)){
                ValueOperation newOperation = new ValueOperation(path);
                if(newOperation.operator instanceof ValueOperator.NUL){
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
            ItemStack newStack = ItemUtils.createStackFromNBT((CompoundTag) itemTag);
            if(!newStack.isEmpty()){
                if(pathToCount.length>0){
                    Tag countTag = NBTUtils.getTagRecursive(rootNBT, pathToCount);
                    if (countTag != null && NBTUtils.isNumber(countTag)) {
                        int count = ((NumericTag) countTag).getAsInt();
                        for (ValueOperation operation : operations) {
                            count = operation.doOperation(count, rootNBT);
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

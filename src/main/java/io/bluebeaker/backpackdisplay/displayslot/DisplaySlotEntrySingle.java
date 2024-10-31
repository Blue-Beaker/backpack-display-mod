package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;

public class DisplaySlotEntrySingle extends DisplaySlotEntryBase {
    public DisplaySlotEntrySingle(Set<Integer> metadataList,String nbtRule){
        super(metadataList);
    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        List<ItemStack> output = new ArrayList<ItemStack>();
        return output;
    }
}

package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;

public class DisplaySlotEntryList extends DisplaySlotEntryBase {
    public DisplaySlotEntryList(Set<Integer> metadataList,String nbtRule){
        super(metadataList);
    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        List<ItemStack> output = new ArrayList<ItemStack>();
        return output;
    }
}

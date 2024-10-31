package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class DisplaySlotEntrySingle implements IDisplaySlotEntry {
    public DisplaySlotEntrySingle(String nbtRule){

    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        List<ItemStack> output = new ArrayList<ItemStack>();
        return output;
    }
}

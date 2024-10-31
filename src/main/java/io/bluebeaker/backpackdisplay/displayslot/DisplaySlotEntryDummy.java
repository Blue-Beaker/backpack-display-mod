package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class DisplaySlotEntryDummy implements IDisplaySlotEntry {
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        List<ItemStack> output = new ArrayList<ItemStack>();
        output.add(new ItemStack(Blocks.STONE));
        return output;
    }
}

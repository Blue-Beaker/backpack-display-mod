package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DisplaySlotEntryBase implements IDisplaySlotEntry {
    Set<Integer> metadataList;
    public DisplaySlotEntryBase(Set<Integer> metadataList){
        this.metadataList=metadataList;
    }
    public boolean isItemMatches(ItemStack stack){
        return metadataList.isEmpty() || metadataList.contains(stack.getMetadata());
    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        List<ItemStack> output = new ArrayList<ItemStack>();
        output.add(new ItemStack(Blocks.STONE));
        output.add(new ItemStack(Items.DIAMOND_PICKAXE,1,500));
        output.add(new ItemStack(Blocks.STAINED_GLASS_PANE,1,3));
        return output;
    }
}

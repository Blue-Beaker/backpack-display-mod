package io.bluebeaker.backpackdisplay.displayslot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

public class DisplaySlotEntryBase implements IDisplaySlotEntry {
    Set<Integer> metadataList;
    String rule;
    public DisplaySlotEntryBase(Set<Integer> metadataList,String rule){
        this.metadataList=metadataList;
        this.rule=rule;
    }
    public boolean isItemMatches(ItemStack stack){
        return true;
    }
    public List<ItemStack> getItemsFromContainer(ItemStack stack){
        List<ItemStack> output = new ArrayList<ItemStack>();
        output.add(new ItemStack(Blocks.STONE));
        output.add(new ItemStack(Items.DIAMOND_PICKAXE,500));
        output.add(new ItemStack(Blocks.CYAN_STAINED_GLASS_PANE));
        return output;
    }
    @Override
    public String toString(){
        return "["+this.getClass().getName()+":"+this.rule+"]";
    }
}

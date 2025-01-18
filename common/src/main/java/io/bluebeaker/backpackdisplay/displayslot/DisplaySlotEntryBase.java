package io.bluebeaker.backpackdisplay.displayslot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class DisplaySlotEntryBase implements IDisplaySlotEntry {
    String rule;
    public DisplaySlotEntryBase(String rule){
        this.rule=rule;
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

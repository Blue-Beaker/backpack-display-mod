package io.bluebeaker.backpackdisplay.displayslot;

import java.util.Set;

import net.minecraft.world.item.ItemStack;

public class MetadataMatcher implements IItemMatcher {
    Set<Integer> metadataList;
    public MetadataMatcher(Set<Integer> metadataList){
        this.metadataList=metadataList;
    }
    public boolean isItemMatches(ItemStack stack){
        return metadataList.isEmpty() || metadataList.contains(stack.getMetadata());
    }
}

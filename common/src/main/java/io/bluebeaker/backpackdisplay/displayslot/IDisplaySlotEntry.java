package io.bluebeaker.backpackdisplay.displayslot;

import java.util.List;

import net.minecraft.world.item.ItemStack;

public interface IDisplaySlotEntry extends IItemMatcher {
    public boolean isItemMatches(ItemStack stack);
    public List<ItemStack> getItemsFromContainer(ItemStack stack);
}

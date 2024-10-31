package io.bluebeaker.backpackdisplay.displayslot;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IDisplaySlotEntry {
    public List<ItemStack> getItemsFromContainer(ItemStack stack);
}

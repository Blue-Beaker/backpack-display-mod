package io.bluebeaker.backpackdisplay.displayslot;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IDisplaySlotEntry {
    public List<ItemStack> getItemsFromContainer(ItemStack stack);
}

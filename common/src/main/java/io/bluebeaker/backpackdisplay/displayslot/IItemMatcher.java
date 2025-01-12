package io.bluebeaker.backpackdisplay.displayslot;

import net.minecraft.world.item.ItemStack;

public interface IItemMatcher {
    public boolean isItemMatches(ItemStack stack);
}

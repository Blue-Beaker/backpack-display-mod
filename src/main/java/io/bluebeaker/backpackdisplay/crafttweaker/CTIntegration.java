package io.bluebeaker.backpackdisplay.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class CTIntegration {
    public static List<ItemStack> getItemsForCT(ItemStack stack){
        List<ItemStack> items = new ArrayList<ItemStack>();
        DisplaySlotEntriesCT.getDisplayItems(CraftTweakerMC.getIItemStack(stack))
                .forEach((IItemStack stack1) -> {
                    items.add(CraftTweakerMC.getItemStack(stack1));
                });
        return items;
    }
}

package io.bluebeaker.backpackdisplay.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
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
    public static List<FluidStack> getFluidsForCT(ItemStack stack){
        List<FluidStack> items = new ArrayList<FluidStack>();
        BackpackDisplayFluidCT.getDisplayFluids(CraftTweakerMC.getIItemStack(stack))
                .forEach((ILiquidStack stack1) -> {
                    items.add(CraftTweakerMC.getLiquidStack(stack1));
                });
        return items;
    }
}

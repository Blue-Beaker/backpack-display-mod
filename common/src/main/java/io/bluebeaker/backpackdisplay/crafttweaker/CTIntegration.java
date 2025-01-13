package io.bluebeaker.backpackdisplay.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;

public class CTIntegration {
    public static List<ItemStack> getItemsForCT(ItemStack stack){
        List<ItemStack> items = new ArrayList<ItemStack>();
        BackpackDisplayItemsCT.getDisplayItems(IItemStack.of(stack))
                .forEach((IItemStack stack1) -> {
                    items.add(stack1.getImmutableInternal());
                });
        return items;
    }
    public static List<FluidStack> getFluidsForCT(ItemStack stack){
        List<FluidStack> items = new ArrayList<FluidStack>();
        BackpackDisplayFluidCT.getDisplayFluids(IItemStack.of(stack))
                .forEach((IFluidStack stack1) -> {
                    items.add((FluidStack)stack1.getImmutableInternal());
                });
        return items;
    }
}

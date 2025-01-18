package io.bluebeaker.backpackdisplay.crafttweaker;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import dev.architectury.fluid.FluidStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
                .forEach((IFluidStack iFluidStack) -> {
                    items.add(getFluidstackFromIFluidStack(iFluidStack));
                });
        return items;
    }

    @ExpectPlatform
    public static FluidStack getFluidstackFromIFluidStack(IFluidStack iFluidStack){
        throw new AssertionError();
    }
}

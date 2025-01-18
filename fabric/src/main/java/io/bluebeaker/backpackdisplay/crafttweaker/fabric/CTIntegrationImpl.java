package io.bluebeaker.backpackdisplay.crafttweaker.fabric;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.impl.fluid.SimpleFluidStack;
import dev.architectury.fluid.FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public class CTIntegrationImpl {

    public static FluidStack getFluidstackFromIFluidStack(IFluidStack iFluidStack){
        SimpleFluidStack internal = iFluidStack.<SimpleFluidStack>getInternal();
        Fluid fluid = internal.fluid();
        long amount = internal.amount();
        CompoundTag tag = internal.tag();

        return FluidStack.create(fluid,amount,tag);
    }
}
